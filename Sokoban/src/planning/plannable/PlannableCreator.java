package planning.plannable;

import java.util.HashMap;

import common_data.item.Box;
import common_data.item.BoxOnTarget;
import common_data.item.Floor;
import common_data.item.Player;
import common_data.item.PlayerOnTarget;
import common_data.item.Position2D;
import common_data.item.Target;
import common_data.item.Wall;
import plannerP.Clause;
import plannerP.Predicate;
import solver.FinalSolution;

public class PlannableCreator 
{
	
	private PlannableContainerCreator container;
	private HashMap<String,String> boxes_ids;
	private FinalSolution finalSolution;
	private SokoHeuristics heuristics;
	private int attempt;
	
	public class PlannableContainerCreator
	{
		public Clause kb,goal;
		public char[][] level;
		public HashMap<String,String> boxes_ids;
		public FinalSolution finalSoltuin;

		public PlannableContainerCreator(){}
		public PlannableContainerCreator(Clause kb, Clause goal, char [][] level, HashMap<String,String> boxes_ids,FinalSolution finalSolution) 
		{
			
			this.finalSoltuin = finalSolution;
			this.kb=kb;
			this.goal=goal;
			this.level=level;
			this.boxes_ids=boxes_ids;
		}
	}
	public PlannableCreator(FinalSolution finalSoltuion,SokoHeuristics heuristics,int attempt) 
	{
		this.attempt=attempt;
		this.heuristics=heuristics;
		this.finalSolution=finalSoltuion;
		boxes_ids = new HashMap<>();
	}
	public Clause getGoal(Clause kb)
	{
		return heuristics.gettGoal(kb, this.attempt);
//		Clause goal = new Clause(null);
//		//for(Predicate p : kb.getPredicates())
//		for(Predicate p : A.getPredicates(kb,i))
//		{
//			if(p.getType().startsWith("Tar"))
//			{
//				// insert into euroisaifnseouigbseuo list
//				//add all to goal
//				goal.add(new Predicate("BoxAt", "?", p.getValue()));
//			}
//		}
//		return goal;
	}
	public  Clause getKB(char[][] level)
	{
		Clause kb = new Clause(null);
		int boxCount = 0;
		int targetCount= 0;
		final char floor = new Floor().getId_char();
		final char wall = new Wall().getId_char();
		final char player = new Player().getId_char();
		final char box = new Box().getId_char();
		final char target= new Target().getId_char();
		final char playerOnTarget = new PlayerOnTarget().getId_char();
		final char boxOnTarget = new BoxOnTarget().getId_char();
		 
		for(int i=0;i<level.length;++i)
		{
			
			for(int j=0; j<level[0].length;++j)
			{
				
				char current = level[i][j];
				if(current == floor)
				{
					
				}
				else if(current == box)
				{
					boxCount++;
					kb.add(new Predicate("BoxAt", "b"+boxCount, "("+i+","+j+")"));
					boxes_ids.put(new Position2D(i,j).toString(),"b"+boxCount);
					
				}
				else if(current == wall)
				{
					
				}
				else if(current == player)
				{
					kb.add(new Predicate("PlayerAt", "", "("+i+","+j+")"));
				}
				else if(current == target)
				{
					targetCount++;
					kb.add(new Predicate("TargetAt", "t"+targetCount, "("+i+","+j+")"));
				}
				else if(current == playerOnTarget)
				{
					
				}
				else if(current == boxOnTarget)
				{
					
				}
			}
			
		}
		return kb;	
	}
	public  SokobanPlannable createPlannable(char[][] level)
	{
		Clause kb = getKB(level);
		Clause goal=getGoal(kb);
		container = new PlannableContainerCreator(kb, goal, level, boxes_ids,finalSolution);
		SokobanPlannable p = new SokobanPlannable(container);
		//	SokobanPlannable p = new SokobanPlannable(kb, goal,level);
		return p;
	}
	
}
