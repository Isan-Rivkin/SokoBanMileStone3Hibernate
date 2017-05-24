package searching;

import java.util.HashMap;

import bfs.Action;
import bfs.Searchable;
import bfs.State;
import common_data.item.Player;
import common_data.item.Position2D;
import common_data.level.Level;
import model.policy.CalculateMove;
import model.policy.SokobanPolicy;
import planning.delete.LevelHandler;
import searching.delete.SearchGoalState;

public class SokobanSearchAdapter implements Searchable<SokobanState>
{	
	final String up="up";
	final String down = "down";
	final String left = "left";
	final String right = "right";
	private Level level;
	private CalculateMove policy;
	//delete
	Position2D takeMe,dest;
	public SokobanSearchAdapter(Level l, CalculateMove policy) 
	{
		level=l;
		this.policy = policy;
	}
	@Override
	public State<SokobanState> getInitialState()
	{
		SokobanState s = new SokobanState(level);
		State<SokobanState> initialState = new State<SokobanState>(s,0);
		return initialState;
	}

	@Override
	public State<SokobanState> getGoalState()
	{
		SearchGoalState goalCreator = new SearchGoalState();
		Level goalLevel = goalCreator.initObjectGoalState(level,takeMe,dest);
		SokobanState s = new SokobanState(goalLevel);
		State<SokobanState> goalState = new State<SokobanState>(s,0);
		return goalState;
		
	}
	public void setCoordinate(Position2D whoToTake,Position2D takeMeHere)
	{
		this.takeMe = whoToTake;
		this.dest = takeMeHere;
	}
	
	private State<SokobanState> generateNextState(State<SokobanState> s, String dir)
	{
		SearchGoalState sgs = new SearchGoalState();
		Position2D prePos = getPlayerPos(s.getState().game_map);
		if (prePos == null)
		{
			System.out.println("PRE POST IS NULL");
			//return null;
		}
			
		Position2D nextPose = getNextPos(dir,prePos);
		if(nextPose == null)
		{
			System.out.println("NEXT POSE IS NULL");
			//return null;
		}
	//	System.out.println("------------------------------");
		Level l = sgs.initObjectGoalState(s.getState().getLevel(), prePos, nextPose);
		if(l ==null)
			return null;
		//LevelHandler.printLevel(l);
		//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		SokobanState sokoS = new SokobanState(l);
		State<SokobanState> stateSokoS = new State<SokobanState>(sokoS,s.getCost()+1);
		return stateSokoS;
	}
	@Override
	public HashMap<Action, State<SokobanState>> getAllPossibleStates(State<SokobanState> state)
	{
		HashMap<Action, State<SokobanState>> possyAction = new HashMap<>();
		
		if(policy.isPossibleMovement(state.getState().getLevel(),up))
		{
		LevelHandler.printLevel(state.getState().getLevel());
			State<SokobanState> s = generateNextState(state, up);
			if (s!=null)
			{
			//	System.out.println("up send");
				possyAction.put(new Action("move up"), s);
			}
		
		}
		policy.getLevel();
		if(policy.isPossibleMovement(state.getState().getLevel(),down))
		{
			//LevelHandler.printLevel(state.getState().getLevel());
		//	System.out.println("------------------------------------------");
			//LevelHandler.printLevel(state.getState().getLevel());
			State<SokobanState> s = generateNextState(state, down);
			//LevelHandler.printLevel(state.getState().getLevel());
			//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			if (s!=null)
			{
				//System.out.println("down send");
				possyAction.put(new Action("move down"), s);
			}
		}
		policy.getLevel();
		if(policy.isPossibleMovement(state.getState().getLevel(),right))
		{
		//	LevelHandler.printLevel(state.getState().getLevel());
			State<SokobanState> s = generateNextState(state, right);
			if (s!=null)
			{
		//		System.out.println("riight send");
				possyAction.put(new Action("move right"), s);
			}
			
		}
		policy.getLevel();
		
		if(policy.isPossibleMovement(state.getState().getLevel(),left))
		{
		//	LevelHandler.printLevel(state.getState().getLevel());
			State<SokobanState> s = generateNextState(state, left);
			if (s!=null)
			{
				//System.out.println("left send");
				possyAction.put(new Action("move left"), s);
			}
		}
		policy.getLevel();
			return possyAction;
	}
	
	public Position2D getNextPos(String dir, Position2D playerPos)
	{
		Position2D next = null;
		int tempx = playerPos.getX();
		int tempy = playerPos.getY();
		
		if(dir.equals(up))
		{
			next = new Position2D(tempx-1,tempy);
		}
		
		if(dir.equals(down))
		{
			next = new Position2D(tempx+1,tempy);
		}
		
		if(dir.equals(right))
		{
			next = new Position2D(tempx,tempy+1);
		}
		
		if(dir.equals(left))
		{
			next = new Position2D(tempx,tempy-1);
		}
		if(next!=null)
		{
			return next;		
		}
		
		else return null;
	}
	
	public Position2D getPlayerPos(char[][] board)
	{
	
		char[][] game = board;
		Position2D pos = null;
		for (int i=0;i<game.length;i++)
		{
			for (int j=0;j<game[0].length;j++)
			{
				if(game[i][j]==(new Player()).getId_char())
				{
					pos = new Position2D(i,i);
				}
			}
		}
		if(pos!=null)
			return pos;
		else return null;
	}
}
