package solver;

import planning.plannable.SokoHeuristics;

public class RunSolver 
{
	public static void main(String[] args) 
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
		SokoHeuristics heuristics = new SokoHeuristics();
		MainSolver solver = new MainSolver(heuristics);
		solver.defineLevelPath(levelPath,solutionPath );
		solver.loadLevel();
		solver.asyncSolve();
		solver.saveSolution();
			
	}

}
