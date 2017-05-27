package searching.search_util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import common_data.item.Box;
import common_data.item.BoxOnTarget;
import common_data.item.Floor;
import common_data.item.Player;
import common_data.item.PlayerOnTarget;
import common_data.item.Position2D;
import common_data.item.Target;
import common_data.level.Level;
import model.data.levelLoaders.FactoryLevelLoader;
import model.data.levelLoaders.ILevelLoader;
import sokoban_utils.SokoUtil;

public class SearchUtil 
{
	final static String right = "right";
	final static String left = "left";
	final static String up = "up";
	final static String down = "down";
	final static String jump = "ur MaMA";
	public static boolean allowErrorMessages = true;
	
	public static char[][] initiatePlayerGoal(Level l,Position2D target)
	{
		
		return initPlayerGoal(l.getCharGameBoard(), target);
	}
	public static char[][] initPlayerGoal(char [][] original,Position2D target)
	{
		if(original == null || original.length == 0 || target == null || target.getX() >=original.length || target.getY() >= original[0].length)
		{
			SokoUtil.printError(new SearchUtil(), "map/target pos empty or null",allowErrorMessages);
			return null;
		}
		char [][]target_map = new char[original.length][original[0].length];
		int height = original.length;
		int width = original[0].length;
		boolean player_found = false;
		
		// 
		
		char onTarget = original[target.getX()][target.getY()];
		
		if(!isPossiblePlayerMove(original, target))
		{
			return null;
		}
		target_map = duplicateMap(original);
		Position2D playerPos = extractCharPlayerPosition(target_map);
		if(playerPos == null)
		{
			return null;
		}
		player_found= true;
		char playerSymbol = target_map[playerPos.getX()][playerPos.getY()];
		if(playerSymbol == new Player().getId_char())
		{
			target_map[playerPos.getX()][playerPos.getY()] = new Floor().getId_char();
		}
		else if(playerSymbol == new PlayerOnTarget().getId_char())
		{
			target_map[playerPos.getX()][playerPos.getY()] = new Target().getId_char();
		}
		char targetSymbol = target_map[target.getX()][target.getY()];
		if(targetSymbol == new Target().getId_char())
		{
			target_map[target.getX()][target.getY()] = new PlayerOnTarget().getId_char();
		}
		else if(targetSymbol == new Floor().getId_char())
		{
			target_map[target.getX()][target.getY()] = new Player().getId_char();
		}
		if(player_found)
			return target_map;
		return null;
	}
	public static boolean isPossiblePlayerMove(char [][]map, Position2D target)
	{
		if(map == null || map.length == 0 || target == null || target.getY()>= map[0].length || target.getX() >=map.length)
			return false;
		Position2D playerPos = extractCharPlayerPosition(map);
		if (playerPos == null)
			return false;
		char onTarget = map[target.getX()][target.getY()];
		if (onTarget != new Floor().getId_char() && onTarget!= new Target().getId_char())
			return false;
		
		return true;
	}
	public static Level loadLevel(String path) throws FileNotFoundException
	{
		FactoryLevelLoader fac_loader = new FactoryLevelLoader();
		ILevelLoader loader=fac_loader.getLevelLoader(path);
		InputStream in = new FileInputStream(path);
		Level level=loader.load(in);
		return level;
	}
	public static void printLevel(Level k)
	{
		if(k==null)
			return;
		printCharLevel(k.getCharGameBoard());
	}
	public static void printCharLevel(char[][] map)
	{
		for(int i=0;i<map.length;++i)
		{
			for(int j=0;j<map[0].length;++j)
			{
				System.out.print(map[i][j]);
			}
		
			System.out.println();
		}
	}
	public static Position2D extractPlayerPosition(Level l)
	{
		return extractCharPlayerPosition(l.getCharGameBoard());
	}
	public static Position2D extractCharPlayerPosition(char[][] charGameBoard) 
	{
		if(charGameBoard == null)
			return null;
		Player p = new Player();
		for(int i=0;i<charGameBoard.length;++i)
		{
			for(int j=0; j<charGameBoard[0].length;++j)
			{
				if(charGameBoard[i][j] == p.getId_char() || charGameBoard[i][j] == new PlayerOnTarget().getId_char() )
				{
					return new Position2D(i,j);
				}
			}
		}
		return null;
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
	public static Position2D extractPosFromStr(Position2D playerPos , String dir)
	{	Position2D newPos = new Position2D();
	int x = playerPos.getX();
	int y = playerPos.getY();
		switch(dir)
		{
		case up:
			newPos.setCoordinate(x-1, y);
			break;
		case down:
			newPos.setCoordinate(x+1, y);
			break;
		case right:
			newPos.setCoordinate(x, y+1);
			break;
		case left:
			newPos.setCoordinate(x, y-1);
			break;
			default:
				return null;

		}
		return newPos;
	}
	// BOX ADAPTER STUFF ROM HERE
	
	public static char[][] generateInitialBoxState(char[][] initial_map) 
	{
		Position2D playerPos = extractCharPlayerPosition(initial_map);
		char [][] duplicated = duplicateMap(initial_map);
		if(duplicated[playerPos.getX()][playerPos.getY()] == new PlayerOnTarget().getId_char())
		{
			duplicated[playerPos.getX()][playerPos.getY()] = new Target().getId_char();

		}
		else if(duplicated[playerPos.getX()][playerPos.getY()] == new Player().getId_char())
		{
			duplicated[playerPos.getX()][playerPos.getY()] = new Floor().getId_char();
		}
		return duplicated;
	}
	public static char[][] generateBoxGoalState(char[][] initial_map, Position2D sourcePos, Position2D destPos) 
	{
		
		return null;
	}
	
	public boolean isPossibleBoxMove(char[][] map , Position2D boxPos , String dir)
	{
		boolean possible = false;
		//Position2D preBox
		switch(dir)
		{
		case right:
		{
			
			break;
		}
		default:
			break;
			
		}
		return possible;
		
	}
}
