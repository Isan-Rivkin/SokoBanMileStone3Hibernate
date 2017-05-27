package searching.playerAdapter;

public class SokobanState
{
	char[][] game_map;
	//boxpos
	public SokobanState(char[][] urMama) 
	{
		
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
		SokobanState state = (SokobanState)obj;
		
		
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
}
