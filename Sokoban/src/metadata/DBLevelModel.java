package metadata;

import java.io.Serializable;

public class DBLevelModel implements Serializable
{
	private String levelName;
	private String serializedLevel;
	private Integer levelMinSteps;
	private String levelPath;
	
	public DBLevelModel(){}
	public DBLevelModel(String levelName, String levelPath,String serializedLevel,Integer levelMinSteps)
	{
		this.levelName=levelName;
		this.serializedLevel=serializedLevel;
		this.levelMinSteps=levelMinSteps;
		this.levelPath=levelPath;
	}
	public String getLevelPath()
	{
		return levelPath;
	}
	public void setLevelPath(String levelPath) 
	{
		this.levelPath = levelPath;
	}
	
	public String getLevelName() 
	{
		return levelName;
	}
	public void setLevelName(String levelName)
	{
		this.levelName = levelName;
	}
	public String getSerializedLevel() 
	{
		return serializedLevel;
	}
	public void setSerializedLevel(String serializedLevel)
	{
		this.serializedLevel = serializedLevel;
	}
	public Integer getLevelMinSteps() 
	{
		return levelMinSteps;
	}
	public void setLevelMinSteps(Integer levelMinSteps)
	{
		this.levelMinSteps = levelMinSteps;
	}

}
