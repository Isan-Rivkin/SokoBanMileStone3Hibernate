package model;

import java.io.InputStream;
import java.io.OutputStream;


import common_data.level.Level;
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
}
