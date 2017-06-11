package planning.plannable;

import java.util.HashMap;

import common_data.item.Position2D;
import plannableP.Plannable;
import plannerP.Action;
import plannerP.Clause;
import plannerP.Predicate;
import planning.plannable.PlanUtil.SearchContainer;
import planning.plannable.PlannableCreator.PlannableContainerCreator;
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
		Position2D playerPos = SearchUtil.extractCharPlayerPosition(level);
		SearchContainer container = util.attachBoxToTargetSolution(level, boxDestination, null);
		if(container == null)
		{
			return null;
		}
		finalSolution.solutions_compilation.add(container.solution);
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
