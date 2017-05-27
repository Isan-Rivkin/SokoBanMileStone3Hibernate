package searching.boxAdapter;

import java.util.HashMap;

import common_data.item.Position2D;
import searchAlgoExtract.Action;
import searchAlgoExtract.Searchable;
import searchAlgoExtract.State;
import searching.SokobanState;
import searching.search_util.SearchUtil;
import sokoban.SokoAdapter;

public class BoxSearchAdapter implements Searchable<SokobanState>
{
	private Position2D sourcePos,destPos;
	private char[][] initial_map;
	
	public BoxSearchAdapter(char [][] initial_map , Position2D sourcePos, Position2D destPos)
	{	this.initial_map = initial_map;
		this.sourcePos=sourcePos;
		this.destPos = destPos;
	}
	@Override
	public State<SokobanState> getInitialState() 
	{
		char [][] generated_map = SearchUtil.generateInitialBoxState(initial_map);
		SokobanState s = new SokobanState(generated_map);
		State<SokobanState> initialState = new State<SokobanState>(s,0);
		return initialState;
	}

	@Override
	public State<SokobanState> getGoalState()
	{
		char [][] duplicated = SearchUtil.generateBoxGoalState(initial_map, sourcePos,destPos);
		SokobanState s = new SokobanState(duplicated);
		State<SokobanState> goalState = new State<SokobanState>(s,0);
		return goalState;
	}

	@Override
	public HashMap<Action, State<SokobanState>> getAllPossibleStates(State<SokobanState> state) 
	{
		HashMap<Action, State<SokobanState>> possyAction = new HashMap<>();
		//Position2D boxPosition = SearchUtil.extractCharPlayerPosition(state.getState().getMap());
		
		return null;
	}

}
