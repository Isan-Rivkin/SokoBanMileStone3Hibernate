package planning.planner;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import planning.plannable.Plannable;





public class Strips implements Planner 
{
	private Plannable plannable;


	@Override
	public List<Action> plan(Plannable plannable)
	{
		LinkedList<Action> plan = new LinkedList<>();
		this.plannable = plannable;
		Stack<Predicate> stack = new Stack<>();
		stack.push(plannable.getGoal());
		while(!stack.isEmpty())
		{
			Predicate top = stack.peek();
			if(!(top instanceof Action))
			{
				if(!plannable.getKnowledgeBase().satisfies(top))
				{
					//unsatisfied
					if(top instanceof Clause) //multipart
					{
						Clause c = (Clause)top;
						// move now
						//stack.pop();
						for(Predicate p: c.predicates)
						{
							stack.push(p);
						}
					}
					else //single and unsatisfied
					{
						stack.pop();
						Action action = plannable.getSatisfyingAction(top);
						stack.push(action);
						stack.push(action.preConditions);
					}
				}
				else
				{
					Predicate pp = stack.pop();
				}
			}
			else // is action
			{
				stack.pop();
				Action a = (Action)top;
				plannable.getKnowledgeBase().update(a.effects);
				plan.add(a);
			}
		}
		return plan;
	}


}
