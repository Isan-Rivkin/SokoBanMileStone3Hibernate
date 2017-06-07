package solver;

import java.io.FileNotFoundException;
import java.util.List;

import common_data.level.Level;
import planning.plannable.SokoHeuristics;
import planning.planner.Clause;
import searching.search_util.SearchUtil;

public class RunSolver 
{
	public static void main(String[] args) 
	{
//		SokobanSolver solver = new SokobanSolver();
//		solver.defineLevelPath("./myLevels/search1.txt", "./LevelSolutions/solution.txt");
//		solver.loadLevel();
//		solver.solve();
		SokoHeuristics h = new SokoHeuristics();
		try 
		{
			Level l = SearchUtil.loadLevel("./myLevels/search2.txt");
			SearchUtil.printCharLevel(l.getCharGameBoard());
			List<Clause> goals =  h.generateGoalList(l.getCharGameBoard());
			for(Clause c : goals)
			{
				System.out.println(c);
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
