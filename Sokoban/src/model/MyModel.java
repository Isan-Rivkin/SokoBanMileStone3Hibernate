package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import common_data.level.Level;
import model.data.levelLoaders.FactoryLevelLoader;
import model.data.levelLoaders.ILevelLoader;
import model.database.HighScoreP;
import model.database.IDBMapper;
import model.database.POJO;
import model.database.SokoDBMapper;
import model.policy.CalculateMove;
import model.policy.Imove;
import model.policy.Policy;
import planning.plannable.SokoHeuristics;
import searchable.Action;
import searchable.Solution;
import sokoban_utils.SokoUtil;
import solver.MainSolver;

public class MyModel extends Observable implements FModel,Observer
{
		
	private ArrayList<Level> levels; 
	private Level currentLevel;//,originalLevel;
	private ILevelLoader loader;
	private ILevelLoader saver;
	private Imove mover;
	private Policy policy;
	private FactoryLevelLoader fac_loader;
	private SokoUtil util;
	private Imove movePush;
	private String currentLevelPath;
	private int winningSteps;
	private IDataManager hs_manager;
	private IDBMapper mapper; 
	// solver stuff
	private MainSolver solver;
	
	public MyModel(Policy policy) 
	{
	//indicator for showing winning msg once.
	this.winningSteps=(int)Integer.MAX_VALUE;
	this.currentLevelPath="";
	this.util=new SokoUtil();
	this.levels=new ArrayList<Level>();
	this.currentLevel=null;
	//this.originalLevel = null;
	this.loader=null;
	this.saver=null;
	this.mover=null;
	this.policy=policy;
    this.movePush=new CalculateMove(this.policy);
	this.fac_loader=new FactoryLevelLoader();
	this.mapper = new SokoDBMapper();
	this.hs_manager=new HSDataManager(mapper);
	//solver stuff
	
	}

	@Override
	public void loadLevel(String path) {
		System.out.println("model: path: "+path);
		LinkedList<String> params=new LinkedList<>();
		if(util.isValidFileType(util.extractFileType(path)) && util.isFileExist(path)){
			this.currentLevelPath=path;
			loader=fac_loader.getLevelLoader(path);
			try {
				InputStream in = new FileInputStream(path);
				currentLevel=loader.load(in);
				if(currentLevel == null)
					util.notifyUser("Model", "Could not load level");
			    levels.add(currentLevel);
			     util.notifyUser("Model", "Loaded Level");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			params.add("display");
			setChanged();
		    notifyObservers(params);
		}else{
			params.add("alert");
			params.add("Cannot load file.");
			setChanged();
		    notifyObservers(params);
		}
		
	}

	@Override
	public void saveCurrentLevel(String path) {
		LinkedList<String> params=new LinkedList<String>();
		if(path!= null && util.isValidFileType(util.extractFileType(path))){
			try {
				saver=fac_loader.getLevelLoader(path);
				FileOutputStream out;
				out = new FileOutputStream(path);
				saver.save(out, currentLevel);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			params.add("display");
			setChanged();
			notifyObservers(params);
			updateObserver("insertleveltodb",SokoUtil.extractLevelNameFromPath(path),path);
		}else{
			params.add("alert");
			params.add("Please insert .ser/.obj in the end of the file name :)");
			setChanged();
			notifyObservers(params);
		}
		
	}

	@Override
	public int getPlayerSteps(int playerNum) {
		return currentLevel.getPlayers().get(playerNum).getCurrent_steps();
	}

	@Override
	public int getPlayerScore(int PlayerNum) {
		return currentLevel.getPlayers().get(PlayerNum).getScore();
	}

	@Override
	public boolean isVictory()
	{
		if(currentLevel!=null)
			return currentLevel.isPlayerWon();
		return false;
		
	}

	@Override
	public void move(int playerNum, String direction) 
	{
		if(currentLevel != null){
			// add player num
			movePush.move(currentLevel, direction);
			currentLevel = movePush.getLevel();
			if(currentLevel.alreadyWon() && currentLevel.numOfBoxesOnTargets()<currentLevel.getNumOfTargets()){
				winningSteps=(int)Integer.MAX_VALUE;
				currentLevel.setAlreadyWon(false);
			}
		}else{
			util.notifyUser("Model", "No level to move");
		}
		LinkedList<String> params=new LinkedList<>();
		params.add("display");
		setChanged();
		notifyObservers(params);
		if(currentLevel.isPlayerWon() && currentLevel.getCurrent_steps()<winningSteps)
		{
			winningSteps=currentLevel.getCurrent_steps();
			LinkedList<String> won=new LinkedList<>();
			won.add("winalert");
			won.add("Congratulations! You are Winner steps:"+this.getPlayerSteps(0));
			setChanged();
			notifyObservers(won);
		}
	}

	@Override
	public Level getCurrentLevel()
	{
		return this.currentLevel;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		
	}

	@Override
	public char[][] getCurrentCharLevel() 
	{
		if(currentLevel != null)
			return currentLevel.getCharGameBoard();
		return null;
	}

	@Override
	public Policy getPolicy()
	{
		return this.policy;
	}

	@Override
	public String getCurrentLevelPath() 
	{
		if(currentLevelPath!= null && currentLevelPath.length()>1)
		{
			return this.currentLevelPath;
		}else{
			LinkedList<String> params = new LinkedList<String>();
			params.add("msgheader");
			params.add("Please Load a Level Before Restart");
			setChanged();
			notifyObservers(params);
		}	
		return null;
	}

	@Override
	public List<HighScoreP> search(String classify, String lName,String pName, String orderType)
	{
		hs_manager.search(classify, lName, pName, orderType);
		updateObserver("displayhs");
		return hs_manager.getCurrentHighScoreList();
	}

	@Override
	public void signUpHighScore(String pName, String lName, Integer currentSteps, Long currentTime)
	{
		hs_manager.signUpHighScore(pName, lName, currentSteps, currentTime);
		updateObserver("search","l","","","");
		
	}

	@Override
	public boolean deleteRow(POJO idPOJO, String... delete) 
	{
		boolean succeed=  hs_manager.deleteQuery(idPOJO, delete);
	//	updateObserver("display"); ++++ msgheader alert
		return succeed;
	}

	@Override
	public void save(POJO line) 
	{
		hs_manager.save(line);
		updateObserver("msgheader","Saved");
	}
	
	public void updateObserver(String ...strings)
	{
		List<String> params = new LinkedList<String>();
		for(String s : strings)
		{
			params.add(s);
		}
		setChanged();
		notifyObservers(params);
	}

	@Override
	public List<HighScoreP> getCurrentHighScoreList() 
	{
		return hs_manager.getCurrentHighScoreList();
	}

	@Override
	public void solveCurrentLevel() 
	{
		String levelPath=getCurrentLevelPath();
		String solutionPath="./LevelSolutions/level1.txt";
		SokoHeuristics heuristics = new SokoHeuristics();
		MainSolver solver = new MainSolver(heuristics);
		solver.defineLevelPath(levelPath,solutionPath );
		solver.loadLevel();
		solver.asyncSolve();
		Solution solution = solver.saveSolution();
		if(solution == null)
		{
			System.out.println("Model: solution is null.");
			return;
		}
		int size = solution.getTheSolution().size();
		String [] parsed_solution = new String[size+2];
		int i=2;
		parsed_solution[0]="executesolution";
		parsed_solution[1]=levelPath;
		
		for(Action a: solution.getTheSolution())
		{
			parsed_solution[i]=a.getAction();
			i++;
		}
		updateObserver(parsed_solution);
	}

}
