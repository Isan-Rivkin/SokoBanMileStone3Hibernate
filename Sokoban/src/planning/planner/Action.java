package planning.planner;

public class Action extends Predicate
{
	public Clause effects,preConditions;
	
	public Action(String type, String id, String value) 
	{
		super(type, id, value);
	}

}
