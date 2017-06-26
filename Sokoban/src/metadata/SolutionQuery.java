package metadata;

import java.io.Serializable;

public class SolutionQuery implements Serializable
{

	private String levelMap;
	private String levelSolution;
	private Integer minSteps;
	private int maxResults;
	private String orderBy;
	private boolean desc;
	
	public SolutionQuery() 
	{
		levelMap=null;
		levelSolution=null;
		minSteps=null;
		orderBy="levelMinSteps asc";
		maxResults=10;
	}
	
	public SolutionQuery(String levelMap, String levelSolution, Integer minSteps, int maxResults, String orderBy,
			boolean desc) {
		super();
		this.levelMap = levelMap;
		this.levelSolution = levelSolution;
		this.minSteps = minSteps;
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

	public String getLevelSolution() {
		return levelSolution;
	}

	public void setMinSteps(Integer minSteps) {
		this.minSteps = minSteps;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getLevelMap() 
	{
		return levelMap;
	}

	
	public Integer getMinSteps() 
	{
		return minSteps;
	}

	
	public String getSolution()
	{
		return levelSolution;
	}

	
	public String getOrderBy() 
	{
		return orderBy;
	}

	
	public void setLevelMap(String levelMap)
	{
		this.levelMap=levelMap;
	}

	
	public void setLevelSolution(String Solution) 
	{
		this.levelSolution=Solution;
	}

	
	public void setMaxResults(int max)
	{
		this.maxResults=max;
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

}
