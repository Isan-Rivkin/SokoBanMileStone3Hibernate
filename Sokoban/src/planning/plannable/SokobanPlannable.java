package planning.plannable;

import java.util.HashMap;
import java.util.LinkedList;

import common_data.item.Position2D;
import planning.plannable.PlanUtil.SearchContainer;
import planning.plannable.PlannableCreator.PlannableContainerCreator;
import planning.planner.Action;
import planning.planner.Clause;
import planning.planner.Predicate;
import searchAlgoExtract.Solution;
import searching.search_util.SearchUtil;
import solver.FinalSolution;

public class SokobanPlannable implements Plannable
{
	
	Clause kb,goal;
	char [][] level;
	//LinkedList<Solution> solutions_compilation;
	HashMap<String, String> boxes_id;
	FinalSolution finalSolution;
	
	public SokobanPlannable(PlannableContainerCreator container) 
	{
		this.finalSolution = container.finalSoltuin;
		this.boxes_id = container.boxes_ids;
		this.kb = container.kb;
		this.goal = container.goal;
		this.level = container.level;
	}
	
	@Override
	public Action getSatisfyingAction(Predicate top) 
	{
		
		PlanUtil util = new PlanUtil();
		Position2D boxDestination = PlanUtil.posFromStr(top.getValue());
		SearchContainer container = util.attachBoxToTargetSolution(level, boxDestination, null);
		finalSolution.solutions_compilation.add(container.solution);
		 // we are only accepting the predicate Predicate("BoxAt","?","1,4") 

		Action action = new MoveAction(boxes_id.get(container.boxPos.toString()), container.boxPos.toString(), container.targetPos.toString());
		level = PlanUtil.generateNextState(container);
		return action;
	}

	@Override
	public Clause getKnowledgeBase()
	{
		return kb;
	}

	@Override
	public Clause getGoal() 
	{
		return goal;
	}

}
