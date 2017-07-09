package model;

import java.util.List;

import common_data.level.Level;
import model.database.HighScoreP;
import model.database.POJO;
import model.policy.Policy;
import searchable.Solution;
/**
 * All the business Logic implemented begind a FACADE.
 * @author Isan Rivkin
 *
 */
public interface FModel {
	/**
	 * load a level from xml,txt,obj,ser
	 * @param path
	 */
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
	public void adminMove(Solution sol);
	// ---  data manager --- //
	//returns list of highscores
	public List<HighScoreP> search(String classify, String lName,String pName, String orderType);
	// sign up user that finished a level
	public void signUpHighScore(String pName, String lName, Integer currentSteps, Long currentTime);
	// delete a row in any table
	public boolean deleteRow(POJO idPOJO,String ... delete);
	// save a random pojo - jojo
	public void save(POJO line);
	public List<HighScoreP> getCurrentHighScoreList();
	public void solveCurrentLevel();
	public void getHint();

	
}
