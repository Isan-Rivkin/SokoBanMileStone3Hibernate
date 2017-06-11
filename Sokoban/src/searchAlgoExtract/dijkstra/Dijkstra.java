package searchAlgoExtract.dijkstra;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;

import com.sun.xml.internal.ws.Closeable;

import searchAlgoExtract.Action;
import searchAlgoExtract.CommonSearcher;
import searchAlgoExtract.Searchable;
import searchAlgoExtract.Solution;
import searchAlgoExtract.State;
import searching.boxAdapter.BoxState;
import searching.search_util.SearchUtil;

public class Dijkstra<T> extends CommonSearcher<T>
{


	private PriorityQueue<State<T>> openList;
	private HashSet<State<T>> closeSet;
	private HashMap<Action, State<T>> successors;
	
	public Dijkstra() 
	{
		initDijkstra();
	}
	
	public void initDijkstra()
	{
		this.evaluatedNodes=0;
		this.openList=new PriorityQueue<>();
		this.closeSet=new HashSet<>();
	}
	
	@Override
	public Solution search(Searchable<T> s)
	{
		//
		State<T>init=s.getInitialState();
		init.setCameFrom(null);
		openList.add(init);
		
		while(!this.openList.isEmpty())
		{
			
			//take out from the open list
			State<T>currState=this.openList.poll();
			
			this.evaluatedNodes++;
			
			this.closeSet.add(currState);
			
			this.successors=s.getAllPossibleStates(currState);
			for(Action a: this.successors.keySet())
			{
				State<T> n=this.successors.get(a);
				
				if(!this.closeSet.contains(n))
				{
					
					if(!this.openList.contains(n))
					{
						n.setCameFrom(currState);
						n.setAction(a);
						this.openList.add(n);
					}
					
					else{
						//find the state with the same state(DM)
						State<T> sameState=null;
						for(State<T> st:this.openList)
						{
							if(st.equals(n))
							{
								sameState=st;
								break;
							}
						}
						
						if(sameState!=null)
						{
							//check if the cost lower
							if(n.getCost()<sameState.getCost()){
								this.openList.remove(sameState);
								n.setCameFrom(currState);
								n.setAction(a);
								this.openList.add(n);
							}
						}
					}
					
				}
				
				
			}
				
		}

			for(State<T> node : closeSet)
			{
				if(node.equals(s.getGoalState()))
					return backTrace(node);
			}
		
		return null;
	}


}
