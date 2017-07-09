package searching.boxAdapter;

import java.util.HashMap;

import common_data.item.Position2D;
import searchable.Action;
import searchable.Searchable;
import searchable.Solution;
import searchable.State;
import searching.search_util.SearchUtil;

/**
 * Full path including boxes bfs adapter.
 * @author Isan Rivkin and Daniel Hake.
 *
 */
public class BoxSearchAdapter implements Searchable<BoxState>
{
	final String up="up";
	final String down = "down";
	final String left = "left";
	final String right = "right";
	private Position2D sourcePos,destPos;
	int firstTime = 0;
	private char[][] initial_map;
	
	public BoxSearchAdapter(char [][] initial_map , Position2D sourcePos, Position2D destPos)
	{	this.initial_map = initial_map;
		this.sourcePos=sourcePos;
		this.destPos = destPos;
		
	}
	@Override
	public State<BoxState> getInitialState() 
	{
		char [][] generated_map = SearchUtil.duplicateMap(initial_map);
		BoxState s = new BoxState(generated_map,sourcePos,destPos);
		
		State<BoxState> initialState = new State<BoxState>(s,0);
		return initialState;
	}
	
	@Override
	public State<BoxState> getGoalState()
	{
		char [][] duplicated = SearchUtil.generateBoxGoalState(initial_map, sourcePos,destPos);
		BoxState s = new BoxState(duplicated, destPos,destPos);
		State<BoxState> goalState = new State<BoxState>(s,0);
		return goalState;
	}

	@Override
	public HashMap<Action, State<BoxState>> getAllPossibleStates(State<BoxState> state) 
	{
		
		//System.out.println("STARTED ");
		HashMap<Action, State<BoxState>> possyAction = new HashMap<>();
		Solution sol = null;
		if((sol=SearchUtil.findBoxMove(state.getState().getMap(), state.getState().getBoxPos(), right))!=null)
		{
			//System.out.println("right ");
			Action a = SearchUtil.getActionFromSolution(sol,right);
			State<BoxState> newState = generateNextBoxState(state, sol,right);
			possyAction.put(a,newState);
		}
		sol = new Solution(null);
		if((sol=SearchUtil.findBoxMove(state.getState().getMap(), state.getState().getBoxPos(), left))!=null)
		{//System.out.println("left ");
			Action a = SearchUtil.getActionFromSolution(sol,left);
			State<BoxState> newState = generateNextBoxState(state, sol,left);
			possyAction.put(a,newState);		
		}
		
		sol = new Solution(null);
		if((sol=SearchUtil.findBoxMove(state.getState().getMap(), state.getState().getBoxPos(), up))!=null)
		{//System.out.println("up ");
			Action a = SearchUtil.getActionFromSolution(sol,up);
			State<BoxState> newState = generateNextBoxState(state, sol,up);
			possyAction.put(a,newState);		
		}
		
		sol = new Solution(null);
		if((sol=SearchUtil.findBoxMove(state.getState().getMap(), state.getState().getBoxPos(), down))!=null)
		{//System.out.println("down ");
			Action a = SearchUtil.getActionFromSolution(sol,down);
			State<BoxState> newState = generateNextBoxState(state, sol,down);
			possyAction.put(a,newState);		
		}
		
		
		return possyAction;
	}

	public State<BoxState> generateNextBoxState(State<BoxState> bs,Solution s,String dir)
	{
		
		char [][] map = SearchUtil.duplicateMap(bs.getState().game_map);
		
		double newCost = (float)s.getTheSolution().size()+bs.getCost()+1;
		Position2D prevBoxPos =  bs.getState().getBoxPos();
		Action a = SearchUtil.getActionFromSolution(s,dir);
		Position2D nextPos = SearchUtil.extractPosFromStr(prevBoxPos, dir);
		char[][] newmap =SearchUtil.generateBoxNextStep(map,prevBoxPos,dir);
		
		BoxState boxState = new BoxState(newmap, nextPos, this.destPos);
		State<BoxState> finalState = new State<BoxState>(boxState, newCost);
		finalState.setCameFrom(bs);
		finalState.setAction(a);
		return finalState;
	}
}
