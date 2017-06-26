package metadata;

import java.io.Serializable;

public class SolutionModel implements Serializable
{
	private String levelMap;
	private String levelSolution;
	private Integer levelSteps;
	
	public SolutionModel() {}
	public SolutionModel(String levelMap, String levelSolution, Integer levelSteps)
	{
		this.levelMap=levelMap;
		this.levelSolution=levelSolution;
		this.levelSteps=levelSteps;
	}
	public String getLevelMap() 
	{
		return levelMap;
	}

	public void setLevelMap(String levelMap) {
		this.levelMap = levelMap;
	}

	public String getLevelSolution() {
		return levelSolution;
	}

	public void setLevelSolution(String levelSolution) {
		this.levelSolution = levelSolution;
	}

	public Integer getLevelSteps() {
		return levelSteps;
	}

	public void setLevelSteps(Integer levelSteps) {
		this.levelSteps = levelSteps;
	}
	
}
