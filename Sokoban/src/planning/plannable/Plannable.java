package planning.plannable;

import java.util.Set;

import planning.planner.Action;
import planning.planner.Clause;
import planning.planner.Predicate;

public interface Plannable 
{
	//public Set<Action> getSatisfyingActions(Predicate top);
	public Action getSatisfyingAction(Predicate top);
	public Clause getKnowledgeBase();
	public Clause getGoal();
	
}
