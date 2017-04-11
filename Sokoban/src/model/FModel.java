package model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import common_data.level.Level;
import model.database.HighScoreP;
import model.database.POJO;
import model.database.PlayerP;
import model.policy.Policy;

public interface FModel {
	public void loadLevel(String path); 
	public void saveCurrentLevel(String path);
	public int getPlayerSteps(int playerNum);
	public int getPlayerScore(int PlayerNum);
	public Level getCurrentLevel();
	public boolean isVictory();
	public void move(int PlayerNum, String direction);
	public char[][] getCurrentCharLevel();
	public Policy getPolicy();
	public String getCurrentLevelPath();
	// ---  data manager --- //
	//returns list of highscores
	public List<HighScoreP> search(String classify, String name, String orderType);
	// sign up user that finished a level
	public void signUpHighScore(String pName, String lName, Integer currentSteps, Long currentTime);
	// delete a row in any table
	public boolean deleteRow(POJO idPOJO,String ... delete);
	// save a random pojo - jojo
	public void save(POJO line);
	public List<HighScoreP> getCurrentHighScoreList();
	
}
