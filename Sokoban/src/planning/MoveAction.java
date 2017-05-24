package planning;

import java.util.ArrayList;
import java.util.List;

import common_data.item.Floor;
import common_data.item.Item;
import common_data.item.Player;
import plannable.Action;
import plannable.Predicate;

public class MoveAction extends Action {

	Player p;
	public MoveAction() {
		name = "MoveAction";	
		args = new String[]{"player", "source", "destination"};
		preconditions.add(new Predicate("On", "player", "source"));
		preconditions.add(new Predicate("Clear", "destination"));
		
		addList.add(new Predicate("On", "player", "destination"));
		addList.add(new Predicate("Clear", "source"));
		
		deleteList.add(new Predicate("On", "player", "source"));
		deleteList.add(new Predicate("Clear", "destination"));
		
		List<Object> values = new ArrayList<Object>();
		values.add("@");
		values.add("o");
		values.add(" ");
		values.add("#");
		illegalAssignments.put("player", values);
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
