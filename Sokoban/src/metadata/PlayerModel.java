package metadata;

import java.io.Serializable;

import javax.persistence.Id;

public class PlayerModel implements Serializable
{
	
	private String playerName;
	
	public PlayerModel(){}
	public PlayerModel(String playerName)
	{
		this.playerName=playerName;
	}
	public String getPlayerName()
	{
		return playerName;
	}
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}
}
