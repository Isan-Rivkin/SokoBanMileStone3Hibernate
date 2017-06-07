package planning.planner;

import java.util.HashSet;
import java.util.Set;

public class Clause extends Predicate
{
	HashSet<Predicate> predicates;
	
	private void updateDescription()
	{
		value ="{";
		for(Predicate p : predicates)
		{
			value+=p.toString()+" & ";
		}
		value+="}";
	}
	public Clause(Predicate ...predicates) 
	{
		super("And","","");
		if(predicates != null)
		{
			this.predicates = new HashSet<>();
			for(Predicate p : predicates)
			{
				this.predicates.add(p);
			}
			updateDescription();		
		}
	}
	public void update(Clause effect)
	{
		effect.predicates.forEach((Predicate p)->predicates.removeIf((Predicate pr )->p.contradicts(pr)));
		predicates.addAll(effect.predicates);
		updateDescription();;
	}
	public void add(Predicate p)
	{
		if(predicates == null)
			predicates = new HashSet<>();
		this.predicates.add(p);
		updateDescription();
	}
	@Override
	public boolean satisfies(Predicate p) 
	{
		if(p instanceof Clause)
		{
			Clause clause = (Clause)p;
			for(Predicate pr : clause.predicates)
			{
				if(!satisfies(pr))
					return false;
			}
			return true;
		}
		else
		{
			for(Predicate pr : predicates)
			{
				if(pr.satisfies(p))
				{
					return true;
				}		
			}
			return false;	
		}
		
	}
//	public boolean satisfies(Clause clause)
//	{
//		for(Predicate p : clause.predicates)
//		{
//			if(!satisfies(p))
//				return false;
//		}
//		return true;
//	}
	public Set<Predicate> getPredicates()
	{
		return this.predicates;
	}
}