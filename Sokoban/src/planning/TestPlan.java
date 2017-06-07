package planning;

import java.io.FileNotFoundException;
import java.util.List;

import common_data.item.Position2D;
import common_data.level.Level;
import planning.plannable.PlanUtil;
import planning.plannable.PlanUtil.SearchContainer;
import planning.plannable.Plannable;
import planning.plannable.PlannableCreator;
import planning.planner.Action;
import planning.planner.Planner;
import planning.planner.Strips;
import searching.search_util.SearchUtil;

public class TestPlan
{
	static String path = "./levels/level1.txt";

	public static void main(String[] args) throws FileNotFoundException 
	{
//		PlanUtil util = new PlanUtil();
//		Level l =SearchUtil.loadLevel(path);
//		SearchUtil.printLevel(l);
//		PlannableCreator creator = new PlannableCreator();
//		Plannable adapter = creator.createPlannable(l.getCharGameBoard());
//		Planner strips = new Strips();
//		List<Action> plans = strips.plan(adapter);
//		if(plans != null)
//		{
//			for(Action a : plans)
//			{
//				System.out.println(a.getValue());
//			}
//		}
//		else
//		{
//			System.out.println("NULL RETURNED ");
//		}
	}

}
