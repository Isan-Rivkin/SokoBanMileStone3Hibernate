package planning.plannable;

import planning.planner.Action;
import planning.planner.Clause;
import planning.planner.Predicate;

public class MoveAction extends Action 
{
	String who, start,dest;
	public MoveAction(String who, String start, String dest)
	{
		super(who, start, dest);
		this.who = who;
		this.start=start;
		this.dest=dest;
		generatePreConditions();
		generatePostEffects();
	}

	private void generatePostEffects()
	{
		this.effects = new Clause(null);
		Predicate boxAt = new Predicate("BoxAt",who,dest);
		this.effects.add(boxAt);
	}

	private void generatePreConditions()
	{
		preConditions = new Clause(null);
		Predicate boxAt = new Predicate("BoxAt", who, start);
		Predicate targetAt = new Predicate("TargetAt", "?", dest);
		preConditions.add(boxAt);
		preConditions.add(targetAt);
	}
	

}
