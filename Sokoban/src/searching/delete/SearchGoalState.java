package searching.delete;

import common_data.item.Floor;
import common_data.item.Item;
import common_data.item.Movable;
import common_data.item.Player;
import common_data.item.PlayerOnTarget;
import common_data.item.Position2D;
import common_data.item.Target;
import common_data.level.Level;
import planning.delete.LevelHandler;

public class SearchGoalState
{

	private Level level;
	private Position2D destination;
	
	public SearchGoalState() 
	{}
	public  Level initObjectGoalState(Level l , Position2D whoToTake ,Position2D takeMeHere)
	{
		if(whoToTake.equals(takeMeHere))
		{
			return null;
		}
		Level copy = l.getCopy();
		Movable moveMe =copy.getMovables()[l.getPlayerPosition(0).getX()][l.getPlayerPosition(0).getY()];
		if(l.getWidth() <= takeMeHere.getY() || l.getHeight() <=takeMeHere.getX())
			return null;
		Item static_item = l.getStaticItemAtPos(takeMeHere.getX(), takeMeHere.getY());
		if(static_item instanceof Target)
		{
			moveMe = new PlayerOnTarget();
		}
		copy.getMovables()[takeMeHere.getX()][takeMeHere.getY()]=moveMe;
		copy.getMovables()[whoToTake.getX()][whoToTake.getY()]=null;
		return copy;
	}
	public static char[][] initCharGoalState(char [][] map , Position2D takeMeHere)
	{
//		// extract player position + what is he on
//		Position2D playerPos = null;
//		char underPlayer= ' ';
//		char [][] newMap = duplicateMap(map);
//		for(int i=0; i<newMap.length;++i)
//		{
//			for(int j=0;j<newMap[0].length;++j)
//			{
//				if( (newMap[i][j] == new PlayerOnTarget().getId_char()) || (newMap[i][j] == new Player().getId_char()) )
//				{
//					playerPos = new Position2D(i, j);
//					if(newMap[i][j] == new Player().getId_char())
//						underPlayer = new Floor().getId_char();
//					else if(newMap[i][j] == new PlayerOnTarget().getId_char())
//						underPlayer = new PlayerOnTarget().getId_char();
//					break;
//				}
//			}
//		}
//		
//		if(playerPos == null || takeMeHere.getX() >= newMap.length|| takeMeHere.getY()>= newMap[0].length)
//			return null;
//		newMap[playerPos.getX()][playerPos.getY()] = underPlayer;
//		char newPositionSymbol ;//??? continue
//		
		return null;
	}
	private Position2D getEmptyMovablesSpot(Movable[][] movables,Item[][] map) 
	{
		for(int i=0;i<movables.length;++i)
		{
			for(int j=0;j<movables[0].length;++j)
			{
				if(movables[i][j] == null && map[i][j] instanceof Floor)
				{
					Position2D pos = new Position2D();
					pos.setX(i);
					pos.setY(j);
					return pos;
				}
			}
		}
		return null;
		
	}
	public void printSatate(Level l)
	{
		for(int i=0;i<l.getCharGameBoard().length;++i)
		{
			for(int j=0;j<l.getCharGameBoard()[0].length;++j)
			{
				System.out.print(l.getCharGameBoard()[i][j]);
			}
			System.out.println();
		}
	}
	public static char[][] duplicateMap(char [][] map)
	{
		char [][] duplicate = new char[map.length][map[0].length];
		for(int i=0;i<map.length;++i)
		{
			for(int j=0;j<map[0].length;++j)
			{
				duplicate[i][j] = map[i][j];
			}
		}
		return duplicate;
	}
	
}
