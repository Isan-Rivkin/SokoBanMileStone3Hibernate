package model.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Players", catalog="SokobanDB", schema="DBO")
public class PlayerP
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int playerID;
	@Column(name="playerName")
	private String playerName;
	
	public PlayerP(){}
	public int getPlayerID() 
	{
		return playerID;
	}
	@Override
	public String toString() 
	{
		return "PlayerP [playerID=" + playerID + ", playerName=" + playerName + "]";
	}
	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}
	public String getPlayerName()
	{
		return playerName;
	}
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}
	public PlayerP(String playerName)
	{
		this.playerName=playerName;
	}
	
}


