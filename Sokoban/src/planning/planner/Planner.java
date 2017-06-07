package planning.planner;

import java.util.List;

import planning.plannable.Plannable;


public interface Planner 
{
	List<Action> plan(Plannable plannable);

	
}
