package view.highScoreLogic;

import javafx.beans.property.SimpleStringProperty;

public class HighScoreTableRow 
{
	private SimpleStringProperty levelName,playerName,playerSteps,playerTime;
	
	public HighScoreTableRow()
	{
		
	}
	public HighScoreTableRow(String levelName, String playerName, Integer playerSteps, Long playerTime) 
	{
		this.levelName = new SimpleStringProperty(levelName);
		this.playerName= new SimpleStringProperty(playerName);
		this.playerSteps= new SimpleStringProperty(""+playerSteps);
		this.playerTime = new SimpleStringProperty(""+playerTime);
	}
	public SimpleStringProperty getLevelName()
	{
		return levelName;
	}
	public void setLevelName(SimpleStringProperty levelName)
	{
		this.levelName = levelName;
	}
	public String getPlayerName() 
	{
		return playerName.get();
	}
	public void setPlayerName(SimpleStringProperty playerName)
	{
		this.playerName = playerName;
	}
	public String getPlayerSteps()
	{
		return playerSteps.get();
	}
	public void setPlayerSteps(SimpleStringProperty playerSteps) 
	{
		this.playerSteps = playerSteps;
	}
	public String getPlayerTime() 
	{
		return playerTime.get();
	}
	public void setPlayerTime(SimpleStringProperty playerTime)
	{
		this.playerTime = playerTime;
	}
}
