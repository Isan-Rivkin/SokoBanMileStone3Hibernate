package planning.plannable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import common_data.item.Position2D;
import common_data.item.Target;
import plannerP.Clause;
import plannerP.Predicate;
import searching.search_util.SearchUtil;

public class SokoHeuristics 
{
	List<Clause> goals;
	PlanUtil planUtil;
	
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
			return (util.manhattenDistance(o2, distance))-(util.manhattenDistance(o1, distance));
		}
	}
	
	public SokoHeuristics() 
	{
		planUtil = new PlanUtil();
		goals = new LinkedList<>();
	}
	public List<Clause> generateGoalsList(char [][] level, int itterations)
	{
		ArrayList<Position2D> targets = new ArrayList<>();
		ArrayList<Position2D> afterEffectTargets = new ArrayList<>();
		char targetSymbol = new Target().getId_char();
		Position2D playerPos = SearchUtil.extractCharPlayerPosition(level);
		BoxComparator bc = new BoxComparator(playerPos);
		PriorityQueue<Position2D> targets_mangatten_queue = new PriorityQueue<>(bc);
		//extract all targets positions list	
		for(int i=0;i<level.length;++i)
			{
				for(int j=0;j<level[0].length;++j)
				{
					if(level[i][j] == targetSymbol)
					{
						targets.add(new Position2D(i,j));
					}
				}
			}
		
		for(Position2D pos : targets)
		{
			targets_mangatten_queue.add(pos);
		}
		while(!targets_mangatten_queue.isEmpty())
		{
			afterEffectTargets.add(targets_mangatten_queue.poll());
		}
		int siduration = 0;
		int offSet=0;
		for (;siduration<1;siduration++)
		{
			ArrayList<Position2D> list = new ArrayList<>();
			offSet = siduration;
			int count=0;
			while(count<afterEffectTargets.size())
			{
				
				//create 1 list order
				list.add(afterEffectTargets.get(offSet));
				if(offSet == afterEffectTargets.size()-1)
				{
					offSet=-1;
				}
				offSet++;
				count++;
			}
			goals.add(generateGoal(list));	
		}
		for(int i=0;i<itterations;++i)
		{
			Position2D[] ordered = generateRandomSiduration(afterEffectTargets);
			goals.add(generateGoal(ordered));
		}
		return goals;
	}
	private Clause generateGoal(Position2D[] ordered) 
	{
		ArrayList<Position2D> orderdList = new ArrayList<>();
		for(int i=0;i<ordered.length;++i)
		{
			orderdList.add(ordered[i]);
		}
		return generateGoal(orderdList);
	}
	private Position2D[] generateRandomSiduration(ArrayList<Position2D> afterEffectTargets) 
	{
		Position2D [] orderd = new Position2D[afterEffectTargets.size()];
		int range = orderd.length;
		Random randomGenerator = new Random();
		for(Position2D pos : afterEffectTargets)
		{
			boolean goodAsama = false;
			while(!goodAsama)
			{
				int indx = randomGenerator.nextInt(range);
				if(orderd[indx]== null)
				{
					orderd[indx] = pos;
					goodAsama= true;
				}	
			}
		}
		return orderd;
	}
	private Clause generateGoal(ArrayList<Position2D> targetsPos)
	{
		Clause c = new Clause(null);
		//for(Position2D pos : targetsPos)
		for(int i=targetsPos.size()-1;i>=0;i--)
		{
			c.add(new Predicate("BoxAt", "?", targetsPos.get(i).toString()));
		}
		return c;
	}
	public Clause gettGoal(Clause kb, int attempt)
	{
		return goals.get(attempt);
	}
}
