package planning.plannable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

import common_data.item.Box;
import common_data.item.Floor;
import common_data.item.Player;
import common_data.item.PlayerOnTarget;
import common_data.item.Position2D;
import common_data.item.Target;
import planning.plannable.PlanUtil.SearchContainer;
import searchAlgoExtract.BFS;
import searchAlgoExtract.Searchable;
import searchAlgoExtract.Searcher;
import searchAlgoExtract.Solution;
import searching.boxAdapter.BoxSearchAdapter;
import searching.boxAdapter.BoxState;
import searching.search_util.SearchUtil;


public class PlanUtil
{
	final static String up="move up";
	final static String left = "move left";
	final static String down = "move down";
	final static String right = "move right";
	SearchContainer searchContainer;
	
	private class BoxComparator implements Comparator<Position2D>
	{
		
		
		Position2D distance;
		
		public BoxComparator(Position2D manhaten_distance)
		{
			this.distance=manhaten_distance;
		}
		@Override
		public int compare(Position2D o1, Position2D o2) 
		{
			PlanUtil util = new PlanUtil();
			return (util.manhattenDistance(o1, distance)-util.manhattenDistance(o2, distance));
		}
	}
	public class SearchContainer
	{
		public SearchContainer(Solution solution, Position2D boxPos,Position2D targetPos,char[][] level)
		{
			this.solution=solution;
			this.boxPos=boxPos;
			this.targetPos=targetPos;
			this.level=level;
		}
		public char[][] level;
		public Position2D targetPos;
		public Solution solution;
		public Position2D boxPos;
	}
	public static Position2D posFromStr(String pos)
	{
		pos = pos.substring(1);
		pos = pos.substring(0, pos.length()-1);
		Scanner in = new Scanner(pos);
		in.useDelimiter(",");
		int x = Integer.parseInt(in.next());
		int y = Integer.parseInt(in.next());
		Position2D p = new Position2D(x, y);
		return p;
	}
	
	
	public SearchContainer attachBoxToTargetSolution(char[][] level, Position2D targetPos, ArrayList<Position2D> excludePoses)
	{
	
		ArrayList<Position2D> boxes = new ArrayList<>();
		BoxComparator bc = new BoxComparator(targetPos);
		PriorityQueue<Position2D> queue = new PriorityQueue<>(bc);
		SearchUtil.printCharLevel(level);
		for(int i=0;i<level.length;++i)
		{
			for(int j=0;j<level[0].length;++j)
			{
				if(level[i][j] == new Box().getId_char())
				{
					if(excludePoses != null)
					{
						if(!excludePoses.contains(new Position2D(i,j)))
						{
							boxes.add(new Position2D(i,j));
						}
					}
					else
					{	
						     boxes.add(new Position2D(i,j));
					}
						
				}
			}
 		}
			for (Position2D p : boxes)
			{
				queue.add(p);
			}
			while(!queue.isEmpty())
			{
				Position2D source = queue.poll();
				Searchable<BoxState> adapter = new BoxSearchAdapter(level, source, targetPos);
				Searcher<BoxState> searcher = new BFS<BoxState>();
				Solution sol = searcher.search(adapter);
				if(sol == null)
				{
					continue;
				}
				this.searchContainer = new SearchContainer(sol, source,targetPos,adapter.getGoalState().getState().getMap());
				return this.searchContainer;
			}
		return null;
	}
	public int manhattenDistance(Position2D from, Position2D to)
	{	
		int ronen = Math.abs(from.getX()-to.getX()) + Math.abs(from.getY()-to.getY());
		return ronen;
	}

	public static char[][] generateNextState(SearchContainer container)
	{
		
		Position2D targetPos = container.targetPos;
		char [][] newLevel = SearchUtil.duplicateMap(container.level);
		LinkedList<searchAlgoExtract.Action> actions = container.solution.getTheSolution();
		int lastindex = actions.getLast().getAction().lastIndexOf("move");
		String lastMove = actions.getLast().getAction().substring(lastindex, actions.getLast().getAction().length());
		Position2D playerPos = SearchUtil.extractCharPlayerPosition(newLevel);
		char playerChar =newLevel[playerPos.getX()][playerPos.getY()]; 
		// delete old player position
		if(playerChar == new Player().id_char)
		{
			newLevel[playerPos.getX()][playerPos.getY()]= new Floor().getId_char();
		}
		else if(playerChar == new PlayerOnTarget().getId_char())
		{
			newLevel[playerPos.getX()][playerPos.getY()]= new Target().getId_char();
		}
		//initiate players final pose (with alot of logic...)
		switch(lastMove)
		{
		case up:
		{
			if(newLevel[targetPos.getX()+1][targetPos.getY()]== new Floor().getId_char())
			{
				newLevel[targetPos.getX()+1][targetPos.getY()]= new Player().getId_char();	
			}
			else
			{
				newLevel[targetPos.getX()+1][targetPos.getY()]= new PlayerOnTarget().getId_char();	
			}
			break;
		}
			 
		case down:
		{
			if(newLevel[targetPos.getX()-1][targetPos.getY()]== new Floor().getId_char())
			{
				newLevel[targetPos.getX()-1][targetPos.getY()]= new Player().getId_char();	
			}
			else
			{
				newLevel[targetPos.getX()-1][targetPos.getY()]= new PlayerOnTarget().getId_char();	
			}
			break;
		}
		case left:
		{
			if(newLevel[targetPos.getX()][targetPos.getY()-1]== new Floor().getId_char())
			{
				newLevel[targetPos.getX()][targetPos.getY()-1]= new Player().getId_char();	
			}
			else
			{
				newLevel[targetPos.getX()][targetPos.getY()-1]= new PlayerOnTarget().getId_char();	
			}
			break;
		}
		case right:
		{
			if(newLevel[targetPos.getX()][targetPos.getY()-1]== new Floor().getId_char())
			{
				newLevel[targetPos.getX()][targetPos.getY()-1]= new Player().getId_char();	
			}
			else
			{
				newLevel[targetPos.getX()][targetPos.getY()-1]= new PlayerOnTarget().getId_char();	
			}
			break;
		}
			default:
			{
				break;
			}

		}
		
		return newLevel;
	}
}
