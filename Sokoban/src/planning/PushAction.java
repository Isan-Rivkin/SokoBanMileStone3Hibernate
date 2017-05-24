package planning;

import java.util.ArrayList;
import java.util.List;

import plannable.Action;
import plannable.Predicate;
public class PushAction extends Action
{
public PushAction() 
{
	name = "PushAction";	
	args = new String[]{"box", "source", "destination"};
	preconditions.add(new Predicate("On", "box", "source"));
	preconditions.add(new Predicate("Clear", "destination"));
	// player is actually close the fuckers(0)
	addList.add(new Predicate("On", "box", "destination"));
	
	deleteList.add(new Predicate("On", "box", "source"));
	deleteList.add(new Predicate("Clear", "destination"));
	/////
	
	
	
	//////
	// box
	List<Object> values = new ArrayList<Object>();
	values.add("A");
	values.add("o");
	values.add(" ");
	values.add("#");
	illegalAssignments.put("box", values);
	// source
	List<Object> values1 = new ArrayList<Object>();
	values1.add("#");
	values1.add("@");
	values1.add("A");
	illegalAssignments.put("source", values1);
	// destination
			List<Object> values2 = new ArrayList<Object>();
			values2.add("#");
			values2.add("@");
			values2.add("A");
			illegalAssignments.put("destination", values1);
}
}
