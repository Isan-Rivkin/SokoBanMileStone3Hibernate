package searchAlgoExtract.dijkstra;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import searchAlgoExtract.Action;
import searchAlgoExtract.CommonSearcher;
import searchAlgoExtract.Searchable;
import searchAlgoExtract.Solution;
import searchAlgoExtract.State;

public class DijkstraBad<T> extends CommonSearcher<T>
{
	@Override
	public Solution search(Searchable<T> s) 
	{
		PriorityQueue<State<T>> unvisited = new PriorityQueue<>();
		State<T> source = s.getInitialState();
		HashSet<State<T>> visited = new HashSet<>();
		unvisited.add(source);
		while(!unvisited.isEmpty())
		{
			State<T> u = unvisited.poll();
			visited.add(u);
			HashMap<Action,State<T>> adj = s.getAllPossibleStates(u);
			for(State<T> node : adj.values())
			{
				if(!visited.contains(node))
				{
					unvisited.add(node);
					relax(u,node);
				}
				else
				{
					adj.remove(node);
				}
				
			}
		}
		State<T> goal = s.getGoalState();
		LinkedList<Action> actions = new LinkedList<>();
		visited.forEach((n)->
		{
			if(n.equals(goal))
			{
				actions.add(goal.getAction());
				actions.addAll(goal.getPreActions());
			}
		});
		Solution solution = new Solution(actions);
		return solution;
	}
	private void relax(State<T> u, State<T> v)
	{
		if(v.getCost() > u.getCost() + 1)
		{
			v.setCost(u.getCost()+1);
			v.setCameFrom(u);
		}
	}

}
