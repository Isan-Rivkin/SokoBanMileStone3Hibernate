package planning.plannable;

import planning.planner.Predicate;

public class SokoSPredicate extends Predicate
{
	public SokoSPredicate(String type, String id, String value) 
	{
		super(type,id,value);
	}
	@Override
	public boolean contradicts(Predicate p)
	{
		return super.contradicts(p) || (!this.id.equals(p.id()) && this.value.equals(p.getValue()));
	}
}	
