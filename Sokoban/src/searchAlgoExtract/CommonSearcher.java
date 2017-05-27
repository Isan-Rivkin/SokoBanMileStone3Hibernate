package searchAlgoExtract;

import java.util.LinkedList;
import searching.search_util.SearchUtil;

public abstract class CommonSearcher<T> implements Searcher<T>
{
	
	protected int evaluatedNodes;
	
	@Override
	public int  getNumberOfNodesEvaluated()
	{
		return this.evaluatedNodes;
	}
	
	protected Solution backTrace(State<T> goalState)
	{
		
		LinkedList<Action> actions=new LinkedList<>();
		
		State<T> currState=goalState;
		while(currState.getCameFrom()!=null)
		{
			actions.addFirst(currState.getAction());
			if(currState.getPreActions()!=null)
			{
				actions.addAll(0, currState.getPreActions());
			}
			currState=currState.getCameFrom();
		}
		
		return new Solution(actions);
	}

	
}
