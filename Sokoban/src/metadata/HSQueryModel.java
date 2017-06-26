package metadata;

import java.io.Serializable;

public class HSQueryModel implements Serializable
{
	private String levelName;
	private String playerName;
	private int maxResults;
	private String orderBy;
	private boolean desc;
	
	public HSQueryModel()
	{
		levelName=null;
		playerName=null;
		orderBy="playerTime asc";
	}
	
	public HSQueryModel(String levelName, String playerName, int maxResults, String orderBy, boolean desc) 
	{
		super();
		this.levelName = levelName;
		this.playerName = playerName;
		this.maxResults = maxResults;
		this.orderBy = orderBy;
		this.desc = desc;
	}

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getLevelName() 
	{
		return levelName;
	}
	public String getPlayerName()
	{
		return playerName;
	}
	public String getOrderBy() 
	{
		return orderBy;
	}
	public void setLevelName(String levelName)
	{
		this.levelName=levelName;
	}
	public void setPlayerName(String playerName) 
	{
		this.playerName=playerName;
	}
	public void setMaxResults(int max) 
	{
		this.maxResults=max;
	}
	public void initOrderBySteps() 
	{
		orderBy="playerSteps asc";
	}
	public void initOrderByTime() 
	{
		orderBy="playerTime asc";
	}

	public int getMaxResults() 
	{
		return maxResults;
	}
	public boolean isDESC()
	{
		return desc;
	}
	public void setDESC(boolean desc)
	{
		this.desc=desc;
	}
	public void initLexiPlayerName() 
	{
		orderBy="playerName asc";
	}
	public void initLexiLevelName() 
	{
		orderBy="levelName asc";
	}

}
