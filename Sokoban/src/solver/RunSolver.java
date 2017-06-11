package solver;

import java.io.FileNotFoundException;

import planning.plannable.PlanUtil;
import planning.plannable.SokoHeuristics;
import searching.search_util.SearchUtil;

public class RunSolver 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		String levelPath="";
		String solutionPath="";
		if(args.length ==2)
		{
			levelPath=args[0];
			solutionPath=args[1];
		}
		else
		{
			levelPath="./levels/level1.txt";
			solutionPath="./LevelSolutions/level1.txt";
		}
		SearchUtil.printCharLevel(SearchUtil.loadLevel(levelPath).getCharGameBoard());
		SokoHeuristics heuristics = new SokoHeuristics();
		MainSolver solver = new MainSolver(heuristics);
		solver.defineLevelPath(levelPath,solutionPath );
		solver.loadLevel();
		solver.asyncSolve();
		solver.saveSolution();
	}

}
