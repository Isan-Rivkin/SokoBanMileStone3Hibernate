package model.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Levels", catalog="SokobanDB", schema="DBO")
public class LevelP 
{	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int levelID;
	@Column(name="levelName")
	private String levelName;
	@Column(name="serializedLevel")
	private String serializedLevel;
	@Column(name="levelMinSteps")
	private int levelMinSteps;
	
	public LevelP(){}
	public LevelP(String levelName)
	{
		this.levelName=levelName;
		serializedLevel="";
		levelMinSteps=0;
	}
	@Override
	public String toString() {
		return "LevelP [levelID=" + levelID + ", levelName=" + levelName + ", serializedLevel=" + serializedLevel
				+ ", levelMinSteps=" + levelMinSteps + "]";
	}
	public int getLevelID() {
		return levelID;
	}
	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getSerializedLevel() {
		return serializedLevel;
	}
	public void setSerializedLevel(String serializedLevel) {
		this.serializedLevel = serializedLevel;
	}
	public int getLevelMinSteps() {
		return levelMinSteps;
	}
	public void setLevelMinSteps(int levelMinSteps) {
		this.levelMinSteps = levelMinSteps;
	}
}
