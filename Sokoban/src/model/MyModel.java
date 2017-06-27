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
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;

import common_data.level.Level;
import metadata.FMGenerator;
import metadata.LevelModel;
import metadata.METADATA;
import metadata.Protocol;
import model.data.levelLoaders.FactoryLevelLoader;
import model.data.levelLoaders.ILevelLoader;
import model.database.HighScoreP;
import model.database.IDBMapper;
import model.database.POJO;
import model.database.SokoDBMapper;
import model.policy.CalculateMove;
import model.policy.Imove;
import model.policy.Policy;
import model.services.ServiceRequester;
import model.services.ServiceRequester.ServiceResponse;
import planning.plannable.SokoHeuristics;
import searchable.Action;
import searchable.Solution;
import searching.search_util.SearchUtil;
import sokoban_utils.SokoUtil;
import solver.MainSolver;

public class MyModel extends Observable implements FModel,Observer
{
	private String lastHint;
	private final static long delayAdminMove = 500;
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
	//hint stuff
	private String lastPlayerMove="";
	private LinkedList<String> lastSolutionList;
	private boolean playerHintRuinedSolution=true;
	// solver stuff
	private MainSolver solver;
	
	public MyModel(Policy policy) 
	{
		this.lastHint="";
		this.lastSolutionList = null;
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
	public void adminMove(Solution sol)
	{
		  Timer t = new Timer();
		    int i =0;
		    LinkedList<String> moves = (LinkedList)SearchUtil.parseSolution(sol);
		        t.scheduleAtFixedRate(new TimerTask() 
		        {
		            @Override
		            public void run() 
		            {
		                if(moves.isEmpty())
		                {
		                    this.cancel();
		                    return;
		                }
		                String b =moves.removeLast();
		                LinkedList<String> params = new LinkedList<>();
		                params.add("move");
		                params.add("0");
		                params.add(b.substring(5));
		                setChanged();
		                notifyObservers(params);
		            }
		        },0,delayAdminMove);

		  
	}
	@Override
	public void loadLevel(String path) 
	{
		LinkedList<String> params=new LinkedList<>();
		if(util.isValidFileType(util.extractFileType(path)) && util.isFileExist(path)){
			this.currentLevelPath=path;
			loader=fac_loader.getLevelLoader(path);
			try
			{
				InputStream in = new FileInputStream(path);
				currentLevel=loader.load(in);
				if(currentLevel == null)
					util.notifyUser("Model", "Could not load level");
			    levels.add(currentLevel);
			    currentLevel.setAlreadyWon(false);
			    winningSteps = Integer.MAX_VALUE;
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
	            char[][] beforeMoveMap = SearchUtil.duplicateMap(currentLevel.getCharGameBoard());
	            currentLevel = movePush.getLevel();
	            // check if move happend or not.
	            if(lastSolutionList != null || !SearchUtil.isLevelsEqual(beforeMoveMap, currentLevel.getCharGameBoard()))
	            {
	                lastPlayerMove = "move " + direction;
	                System.out.println("chekc = "+lastPlayerMove+ " ? " + lastHint);
	                if(!lastHint.equals(lastPlayerMove))
	                {
	                
	                    playerHintRuinedSolution = true;
	                    lastSolutionList = new LinkedList<>();
	                    lastHint="";
	                }
	                else
	                {
	                    playerHintRuinedSolution = false;
	                    if(!lastSolutionList.isEmpty())
	                    lastHint = lastSolutionList.removeLast();
	                }
	            }
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
    	updateObserver("displayHint","PLEASE WAIT WHILE CALCULATING SOLUTION - SIZE MATTERS ;)");
        ServiceRequester sr = new ServiceRequester();
        Gson g = sr.getGson();
        METADATA m = new METADATA();
        m.setId(Protocol.SOLVE_LEVEL);
        LevelModel lModel = FMGenerator.generateModelFromLevel(currentLevel);
        String mdString = g.toJson(m);
        String objString = g.toJson(lModel);
        ServiceResponse sres = sr.requestService(objString, mdString, Protocol.serverIP,Protocol.serverPort);
        
        String objFromServer =sres.getJsonObject(); 
        Solution solution = g.fromJson(objFromServer, Solution.class);          
    if(solution == null)
        {
            System.out.println("Model: solution is null.");
            return;
        }
//		updateObserver("displayHint","PLEASE WAIT WHILE CALCULATING SOLUTION - SIZE MATTERS ;)");
//		String levelPath=getCurrentLevelPath();
//		String solutionPath="./LevelSolutions/level1.txt";
//		SokoHeuristics heuristics = new SokoHeuristics();
//		MainSolver solver = new MainSolver(heuristics);
//		solver.defineLevelPath(levelPath,solutionPath );
//		if(currentLevel==null)
//			solver.loadLevel();
//		else 
//			solver.setLevel(currentLevel);
//		solver.asyncSolve();
//		Solution solution = solver.saveSolution();
//		if(solution == null)
//		{
//			System.out.println("Model: solution is null.");
//			return;
//		}
		int size = solution.getTheSolution().size();
		String [] parsed_solution = new String[size+2];
		int i=2;
		parsed_solution[0]="executesolution";
		parsed_solution[1]=getCurrentLevelPath();
		
		for(Action a: solution.getTheSolution())
		{
			parsed_solution[i]=a.getAction();
			i++;
		}
		updateObserver(parsed_solution);
	}


	public void getHint() 
    {
        if(playerHintRuinedSolution || lastSolutionList==null)
        {
        	updateObserver("displayHint","PLEASE WAIT WHILE CALCULATING SOLUTION - SIZE MATTERS ;)");
            ServiceRequester sr = new ServiceRequester();
            Gson g = sr.getGson();
            METADATA m = new METADATA();
            m.setId(Protocol.SOLVE_LEVEL);
            LevelModel lModel = FMGenerator.generateModelFromLevel(currentLevel);
            String mdString = g.toJson(m);
            String objString = g.toJson(lModel);
            ServiceResponse sres = sr.requestService(objString, mdString, Protocol.serverIP,Protocol.serverPort);
            
            String objFromServer =sres.getJsonObject(); 
            Solution solution = g.fromJson(objFromServer, Solution.class);          
        if(solution == null)
            {
                System.out.println("Model: solution is null.");
                return;
            }
        LinkedList<String> s = (LinkedList<String>)SearchUtil.parseSolution(solution);
        lastSolutionList = s;
        playerHintRuinedSolution=false;
        lastHint = lastSolutionList.removeLast();
        }
        
        updateObserver("displayHint",lastHint);
    }


}
