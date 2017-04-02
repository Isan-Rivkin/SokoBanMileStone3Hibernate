package model.database;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="HighScores", catalog="SokobanDB", schema="DBO")
public class HighScoreP 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int highScoreID;
	@JoinColumn(name="playerID")
	private int playerID;
    @JoinColumn(name="levelID")
	private int levelID;
	@Column(name="playerSteps")
	private int playerSteps;
	@Column(name="playerTime")
	private long playerTime;
	
	public HighScoreP(){}
	public HighScoreP(int levelID, int playerID,int playerSteps,long playerTime) 
	{
		this.levelID=levelID;
		this.playerID=playerID;
		this.playerSteps=playerSteps;
		this.playerTime=playerTime;
	}

	public int getHsID() 
	{
		return highScoreID;
	}

	public void setHsID(int hsID) 
	{
		this.highScoreID = hsID;
	}

	public int getPlayerID() 
	{
		return playerID;
	}

	public void setPlayerID(int playerID) 
	{
		this.playerID = playerID;
	}

	public int getLevelID() 
	{
		return levelID;
	}

	public void setLevelID(int levelID) 
	{
		this.levelID = levelID;
	}

	public int getMinSteps() 
	{
		return playerSteps;
	}

	public void setMinSteps(int minSteps) 
	{
		this.playerSteps = minSteps;
	}

	public long getTime() 
	{
		return playerTime;
	}

	public void setTime(long time) 
	{
		this.playerTime = time;
	}
	@Override
	public String toString() {
		return "HighScoreP [highScoreID=" + highScoreID + ", playerID=" + playerID + ", levelID=" + levelID
				+ ", playerSteps=" + playerSteps + ", playerTime=" + playerTime + "]";
	}

	
	
	
}
