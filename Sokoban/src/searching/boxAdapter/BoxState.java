package searching.boxAdapter;

import common_data.item.Position2D;
import searching.search_util.SearchUtil;

public class BoxState
{
	char[][] game_map;
	Position2D boxPos,targetPos;
	
	public BoxState(char[][] urMama, Position2D boxPos, Position2D targetPos) 
	{	
		this.boxPos = boxPos;
		this.targetPos = targetPos;
		this.game_map = urMama;
	}
	
	@Override
	public String toString()
	{

		String str = "";
		for (int i = 0; i < game_map.length; i++) 
		{
			for (int j = 0; j < game_map[0].length; j++)
			{
				str += game_map[i][j];
			}
			str += "\n";
		}
		return str;	
	}
	
	@Override
	public int hashCode() 
	{		
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) 
	{	
		BoxState state = (BoxState)obj;
		if(boxPos.equals(state.boxPos) && boxPos.equals(state.targetPos))
		{	
			return true;
		}
		for (int i = 0; i < game_map.length; i++) 
		{
			for (int j = 0; j < game_map[0].length; j++) 
			{
				if (game_map[i][j] != state.game_map[i][j])
				{
					return false;
				}
			}			
		}
		
		return true;
	}
	public char[][] getMap()
	{
		return game_map;
	}
	public Position2D getBoxPos()
	{
		return boxPos;
	}
	public Position2D getDestPos()
	{
		return targetPos;
	}
	
}
