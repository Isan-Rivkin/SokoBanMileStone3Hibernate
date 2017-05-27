package searching.search_util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

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
import searchAlgoExtract.Action;
import searchAlgoExtract.BFS;
import searchAlgoExtract.Searchable;
import searchAlgoExtract.Solution;
import searching.playerAdapter.SokobanSearchAdapter;
import searching.playerAdapter.SokobanState;
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
	public static char[][] generateBoxNextStep(char[][] initial_map, Position2D sourcePos, String dirPos)
	{
		char[][] map =SearchUtil.duplicateMap(initial_map);
		Position2D oldBoxPos = sourcePos;
		Position2D oldPlayerPos = extractCharPlayerPosition(initial_map);

		//Position2D newPlayerPos = extractPlayerDestPosForBox(initial_map, sourcePos, dirPos);
		Position2D newPlayerPos = oldBoxPos;
		Position2D newBoxPos = extractPosFromStr(sourcePos, dirPos);
	//	System.out.println("prevbox : " + oldBoxPos + " newbox : " + newBoxPos + " " + dirPos);
		
		/**
		 * box swaping
		 */
		// if old box pos was box
		if(map[oldBoxPos.getX()][oldBoxPos.getY()] == new Box().getId_char())
		{
			map[oldBoxPos.getX()][oldBoxPos.getY()] = new Floor().getId_char();
		}
		// if old box pos was box on target
		else if(map[oldBoxPos.getX()][oldBoxPos.getY()] == new BoxOnTarget().getId_char())
		{
			map[oldBoxPos.getX()][oldBoxPos.getY()] = new Target().getId_char();
		}
		
		// if new box pos was target
		if(map[newBoxPos.getX()][newBoxPos.getY()] == new Target().getId_char())
		{
			map[newBoxPos.getX()][newBoxPos.getY()] = new BoxOnTarget().getId_char();
		}
		
		// if new box pos was floor
		else if(map[newBoxPos.getX()][newBoxPos.getY()] == new Floor().getId_char())
		{
			map[newBoxPos.getX()][newBoxPos.getY()] = new Box().getId_char();
		}
		/**
		 * player swaping
		 */
	//	System.out.println("old player pos = " + oldPlayerPos + " new pos = " +newPlayerPos);
		//old - player on floor 
		if(map[oldPlayerPos.getX()][oldPlayerPos.getY()] == new Player().getId_char())
		{
			map[oldPlayerPos.getX()][oldPlayerPos.getY()] = new Floor().getId_char();
		}
		//old player on target
		else if(map[oldPlayerPos.getX()][oldPlayerPos.getY()] == new PlayerOnTarget().getId_char())
		{
			map[oldPlayerPos.getX()][oldPlayerPos.getY()] = new Target().getId_char();
		}
		// new floor
		if(map[newPlayerPos.getX()][newPlayerPos.getY()] == new Floor().getId_char())
		{
			map[newPlayerPos.getX()][newPlayerPos.getY()] = new Player().getId_char();
		}
		// new target
		else if(map[newPlayerPos.getX()][newPlayerPos.getY()] == new Target().getId_char())
		{
			map[newPlayerPos.getX()][newPlayerPos.getY()] = new PlayerOnTarget().getId_char();
		}
//		printCharLevel(initial_map);
//		System.out.println("-----------------------");
//		printCharLevel(map);
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		return map;
		
	}
	// BOX ADAPTER STUFF ROM HERE
	public static char[][] generateBoxGoalState(char[][] initial_map, Position2D sourcePos, Position2D destPos) 
	{
		char [][] copy_map = duplicateMap(initial_map);
		int hake = initial_map.length;
		int width = initial_map[0].length;
		if(sourcePos.getX()>= hake || sourcePos.getY() >= width || destPos.getX() >= hake || destPos.getY() >= width)
		{
			return null;
		}
		if(copy_map[sourcePos.getX()][sourcePos.getY()] != new Box().getId_char() && copy_map[sourcePos.getX()][sourcePos.getY()] != new BoxOnTarget().getId_char())
		{
			return null;
		}
		char underBox = copy_map[sourcePos.getX()][sourcePos.getY()];
		if(underBox == new BoxOnTarget().getId_char())
		{
			copy_map[sourcePos.getX()][sourcePos.getY()] = new Target().getId_char();
		}
		else if(underBox == new Box().getId_char())
		{
			copy_map[sourcePos.getX()][sourcePos.getY()] = new Floor().getId_char();
		}
		char onTarget = copy_map[destPos.getX()][destPos.getY()]; 
		if(onTarget != new Floor().getId_char() && onTarget != new Target().getId_char())
		{
			return null;
		}
		if(onTarget == new Floor().getId_char())
		{
			copy_map[destPos.getX()][destPos.getY()] = new Box().getId_char();
		}
		else if(onTarget == new Target().getId_char())
		{
			copy_map[destPos.getX()][destPos.getY()] = new BoxOnTarget().getId_char();
		}
		return copy_map;
	}
	
	public static Solution findBoxMove(char[][] map , Position2D boxPos , String dir)
	{
		boolean possible = false;
		Solution sol = null;
		if(!isPossibleBoxMove(map, boxPos, dir))
		{
			return null;
		}
		Position2D playerTargetPos = extractPlayerDestPosForBox(map, boxPos, dir); 
		if(playerTargetPos == null)
		{
			return null;
		}
		sol = searchPlayerPath(map, playerTargetPos);
		return sol;
	}
	public static boolean isPossibleBoxMove(char[][] map , Position2D boxPos , String dir)
	{
		if(isStepablePos(map, extractPosFromStr(boxPos, dir)))
		{
			return true;
		}
		return false;
	}
	public static Solution searchPlayerPath(char [][]map,Position2D targetPos)
	{
		
		Searchable<SokobanState> adapter = new SokobanSearchAdapter(map, null,targetPos);
		BFS<SokobanState> bfs = new BFS<>();
		if(targetPos.equals(extractCharPlayerPosition(map)))
		{
			LinkedList<Action> a = new LinkedList<>();
			a.add(new Action(""));
			return new Solution(a);
		}
		Solution sol=bfs.search(adapter);
		return sol;

	}
	public static Position2D extractPlayerDestPosForBox(char[][] map ,Position2D boxPos, String boxDir)
	{
		
		String opositeBoxDir=getOpositedir(boxDir);
		Position2D playerTargetPos=null;
		switch(opositeBoxDir)
		{
			case up:
			{
				playerTargetPos = new Position2D(boxPos.getX()-1, boxPos.getY());
				break;
			}
			case right:
			{
				playerTargetPos = new Position2D(boxPos.getX(), boxPos.getY()+1);
				break;
			}
			case left:
			{
				playerTargetPos = new Position2D(boxPos.getX(), boxPos.getY()-1);
				break;
			}
			
			case down:
			{
				playerTargetPos = new Position2D(boxPos.getX()+1, boxPos.getY());
				break;
			}
		}
		if(playerTargetPos.equals(extractCharPlayerPosition(map)))
		{
			return playerTargetPos;
		}
		if(isStepablePos(map, playerTargetPos))
		{
			
			return playerTargetPos;
		}
		
		return null;
	}
	private static String getOpositedir(String dir)
	{
		
		switch(dir)
		{
		case up:
			return down;
		case down:
			return up;
		case left:
			return right;
		case right:
			return left;
		default:
			return null;
		}
	}
	private static boolean isStepablePos(char [][]map, Position2D pos)
	{
		if(map == null || pos == null)
			return false;
		int x = map.length;
		int y = map[0].length;
		if(pos.getX() < 0 || pos.getX() >= x || pos.getY() <0 || pos.getY() >=y)
			return false;
		char atDestSymbol = map[pos.getX()][pos.getY()];
		
//		if(atDestSymbol == new Box().getId_char())
//		{
//			Position2D nextNextPos = extractPosFromStr(pos, dir);
//			char symAtNextNext = map[nextNextPos.getX()][nextNextPos.getY()];
//			if(symAtNextNext == new Floor().getId_char() || symAtNextNext == new Target().getId_char())
//			{
//				return true;
//			}
//		}
//		else if(atDestSymbol == new BoxOnTarget().getId_char())
//		{
//			Position2D nextNextPos = extractPosFromStr(pos, dir);
//			char symAtNextNext = map[nextNextPos.getX()][nextNextPos.getY()];
//			if(symAtNextNext == new Floor().getId_char() || symAtNextNext == new Target().getId_char())
//			{
//				return true;
//			}
//		}
		if(atDestSymbol == new Floor().getId_char() || atDestSymbol == new Target().getId_char())
			return true;
		return false;
	}
	public static Action getActionFromSolution(Solution s, String pushDir)
	{
		StringBuilder sol= new StringBuilder();
		for (Action aa : s.getTheSolution())
		{
		if(aa.getAction().equals(""))
		{
			
		}
		else
		{
			sol.append(aa.getAction());
			sol.append("\n");
		}
		}
		sol.append("move "+pushDir);
		if (sol.length() == 0)
		{
		return null;
		
		}
		Action a = new Action(sol.toString());
		
		
		return a;
	}
}






