package metadata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

public class HighScoreModel implements Serializable
{
	private Integer highScoreID;
	private String playerName;
	private String levelName;
	private Integer playerSteps;
	private Long playerTime;
	
	public HighScoreModel(){}
	public HighScoreModel(String playerName, String levelName ,Integer playerSteps,Long playerTime) 
	{
		this.levelName=levelName;
		this.playerName=playerName;
		this.playerSteps=playerSteps;
		this.playerTime=playerTime;
	}
	public Integer getHighScoreID() 
	{
		return highScoreID;
	}
	public void setHighScoreID(Integer highScoreID) 
	{
		this.highScoreID = highScoreID;
	}
	public String getPlayerName() 
	{
		return playerName;
	}
	public void setPlayerName(String playerName) 
	{
		this.playerName = playerName;
	}
	public String getLevelName() 
	{
		return levelName;
	}
	public void setLevelName(String levelName) 
	{
		this.levelName = levelName;
	}
	public Integer getPlayerSteps() 
	{
		return playerSteps;
	}
	public void setPlayerSteps(Integer playerSteps) 
	{
		this.playerSteps = playerSteps;
	}
	public Long getPlayerTime() 
	{
		return playerTime;
	}
	public void setPlayerTime(Long playerTime) 
	{
		this.playerTime = playerTime;
	}
}
