package searching.playerAdapter;

import java.util.HashMap;

import common_data.item.Position2D;
import common_data.level.Level;
import model.policy.CalculateMove;
import searchable.Action;
import searchable.Searchable;
import searchable.State;
import searching.search_util.SearchUtil;
import sokoban_utils.SokoUtil;
/**
 * Player search path  with BFS adapter. 
 * @author Isan Rivkin and Daniel Hake.
 *
 */
public class SokobanSearchAdapter implements Searchable<SokobanState>
{	
	final String up="up";
	final String down = "down";
	final String left = "left";
	final String right = "right";
	private Level level;
	private CalculateMove policy;
	private boolean hasTarget;	
	private boolean allowMessage;
	char [][] map;
	
	//delete
	Position2D takeMe,dest;
	public SokobanSearchAdapter(char[][] m, CalculateMove policy,Position2D takeMeHere) 
	{
		allowMessage = true;
		this.dest=takeMeHere;
		this.map=m;
		this.policy = policy;
		hasTarget = true;
	}
	@Override
	public State<SokobanState> getInitialState()
	{
		if(!hasTarget)
		{
			SokoUtil.printError(this, "target not set", allowMessage);
			return null;
		}
		
		SokobanState s = new SokobanState(map);
		State<SokobanState> initialState = new State<SokobanState>(s,0);
		return initialState;
	}

	@Override
	public State<SokobanState> getGoalState()
	{
		char [][] dest_map= SearchUtil.initPlayerGoal(map, dest);
		SokobanState s = new SokobanState(dest_map);
		State<SokobanState> goalState = new State<SokobanState>(s,0);
		return goalState;
	}
	public void setTargetCoordinate(Position2D takeMeHere)
	{
		this.dest = takeMeHere;
		hasTarget = true;
	}
	
	@Override
	public HashMap<Action, State<SokobanState>> getAllPossibleStates(State<SokobanState> state)
	{
		HashMap<Action, State<SokobanState>> possyAction = new HashMap<>();
		Position2D playerPosition = SearchUtil.extractCharPlayerPosition(state.getState().getMap());
		if(playerPosition == null)
		{
			return null;
		}
		if (SearchUtil.isPossiblePlayerMove(state.getState().getMap(),SearchUtil.extractPosFromStr(playerPosition, up)))
		{
			char [][] newMap = SearchUtil.initPlayerGoal(state.getState().getMap(), SearchUtil.extractPosFromStr(playerPosition, up));
			SokobanState s = new SokobanState(newMap);
			State<SokobanState> full_state = new State<SokobanState>(s,state.getCost()+1);
			full_state.setCameFrom(state);
			Action activity = new Action("move up");
			full_state.setAction(activity);
			possyAction.put(activity, full_state);
		}
		if (SearchUtil.isPossiblePlayerMove(state.getState().getMap(),SearchUtil.extractPosFromStr(playerPosition, down)))
		{
			char [][] newMap = SearchUtil.initPlayerGoal(state.getState().getMap(), SearchUtil.extractPosFromStr(playerPosition, down));
			SokobanState s = new SokobanState(newMap);
			State<SokobanState> full_state = new State<SokobanState>(s,state.getCost()+1);
			full_state.setCameFrom(state);
			Action activity = new Action("move down");
			full_state.setAction(activity);
			possyAction.put(activity, full_state);
		}
		if (SearchUtil.isPossiblePlayerMove(state.getState().getMap(),SearchUtil.extractPosFromStr(playerPosition, left)))
		{
			char [][] newMap = SearchUtil.initPlayerGoal(state.getState().getMap(), SearchUtil.extractPosFromStr(playerPosition, left));
			SokobanState s = new SokobanState(newMap);
			State<SokobanState> full_state = new State<SokobanState>(s,state.getCost()+1);
			full_state.setCameFrom(state);
			Action activity = new Action("move left");
			full_state.setAction(activity);
			possyAction.put(activity, full_state);
		}
		if (SearchUtil.isPossiblePlayerMove(state.getState().getMap(),SearchUtil.extractPosFromStr(playerPosition, right)))
		{
			char [][] newMap = SearchUtil.initPlayerGoal(state.getState().getMap(), SearchUtil.extractPosFromStr(playerPosition, right));
			SokobanState s = new SokobanState(newMap);
			State<SokobanState> full_state = new State<SokobanState>(s,state.getCost()+1);
			full_state.setCameFrom(state);
			Action activity = new Action("move right");
			full_state.setAction(activity);
			possyAction.put(activity, full_state);
		}


		return possyAction;
	}
	
	
	
}
