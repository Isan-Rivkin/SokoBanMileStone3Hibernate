package searching.search_util;

import java.util.LinkedList;
import java.util.List;

import searchable.Action;
import searchable.Solution;

public class TestsMainUtil {

	public static void main(String[] args) 
	{
		LinkedList<Action> actions = new LinkedList<>();
		actions.add(new Action("move up move down move right"));
		actions.add(new Action("move left move up"));
		actions.add(new Action("move down"));
		Solution sol = new Solution(actions);
		SearchUtil util = new SearchUtil();
		List<String> result = util.parseSolution(sol);
		System.out.println("executing");
		for(String s :result)
		{
			System.out.println("-----------");
			System.out.println(s);
		}

	}

}
