package model;

import java.util.List;

import model.database.HighScoreP;
import model.database.POJO;
import model.database.PlayerP;

public interface IDataManager
{
	public List<HighScoreP> search(String classify, String name, String orderType);
	public void signUpHighScore(String pName, String lName, Integer currentSteps, Long currentTime);
	public PlayerP signUpUser(String pName);
	public boolean deleteQuery(POJO idPOJO,String ... delete);
	public void save(POJO line);
	public List<HighScoreP> getCurrentHighScoreList();
	
	
}
