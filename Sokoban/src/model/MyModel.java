package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.omg.CORBA.INTERNAL;

import common_data.level.Level;
import model.data.levelLoaders.FactoryLevelLoader;
import model.data.levelLoaders.ILevelLoader;
import model.policy.CalculateMove;
import model.policy.Imove;
import model.policy.Policy;
import sokoban_utils.SokoUtil;

public class MyModel extends Observable implements FModel,Observer {
		
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

	
	public MyModel(Policy policy) {
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
	public boolean isVictory() {
		if(currentLevel!=null)
			return currentLevel.isPlayerWon();
		return false;
		
	}

	@Override
	public void move(int playerNum, String direction) {
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
		if(currentLevel.isPlayerWon() && currentLevel.getCurrent_steps()<winningSteps){
			winningSteps=currentLevel.getCurrent_steps();
			LinkedList<String> won=new LinkedList<>();
			won.add("alert");
			won.add("Congratulations! You are Winner steps:"+this.getPlayerSteps(0));
			setChanged();
			notifyObservers(won);
		}
	}

	@Override
	public Level getCurrentLevel() {
		return this.currentLevel;
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}

	@Override
	public char[][] getCurrentCharLevel() {
		if(currentLevel != null)
			return currentLevel.getCharGameBoard();
		return null;
	}

	@Override
	public Policy getPolicy() {
		return this.policy;
	}

	@Override
	public String getCurrentLevelPath() {
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

}
