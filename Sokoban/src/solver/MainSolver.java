package solver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import common_data.level.Level;
import model.data.levelLoaders.FactoryLevelLoader;
import model.data.levelLoaders.ILevelLoader;
import model.data.solutionLoaders.FactorySolutionHandler;
import model.data.solutionLoaders.ISolutionHandler;
import planning.plannable.SokoHeuristics;
import searchAlgoExtract.Action;
import searchAlgoExtract.Solution;
import searching.search_util.SearchUtil;
import sokoban_utils.SokoUtil;

public class MainSolver
{
	List<SokobanSolver> solvers;
	Level level;
	SokoHeuristics heuristics;
	FactoryLevelLoader factory_loader;
	String in,out;
	SokoUtil sokoUtil;
	ILevelLoader levelHandler;
	SokobanSolver actualSolver;
	
	public MainSolver(SokoHeuristics heuristics)
	{
		sokoUtil = new SokoUtil();
		factory_loader = new FactoryLevelLoader();
		this.heuristics=heuristics;
		solvers = new LinkedList<SokobanSolver>();
	}
	public void defineLevelPath(String in,String out)
	{
		this.in=in;
		this.out=out;
		if(!(sokoUtil.isValidFileType(sokoUtil.extractFileType(in)) && sokoUtil.isFileExist(in)))
		{
			System.out.println("INVALID FILE TYPE OR FILE DOESNT EXIST");
		}
		levelHandler = factory_loader.getLevelLoader(in);
	}
	public SokobanSolver asyncSolve()
	{
		this.heuristics.generateGoalsList(level.getCharGameBoard());	
		for(int attempt=0;attempt<level.getTargets().size();++attempt)
		{
			solvers.add(new SokobanSolver(new FinalSolution(),heuristics,attempt,level));
			solvers.get(attempt).start();
		}
		for(SokobanSolver solver: solvers)
		{
			try 
			{
				solver.join();
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			};
		}
		int size = 0;
		for(SokobanSolver solver : solvers)
		{
			
//			for(Solution s : solver.getFinalSolution().getFinalSolution())
//			{
//				for(Action a: s.getTheSolution())
//				{
//					System.out.print("->" + a.getAction());
//				}
//			}
//			System.out.println();
			if(solver.isSolved())
			{
				LinkedList<Action> aa = generateFullSolution(solver.getFinalSolution().solutions_compilation);
				if(size == 0 ||  aa.size()< size)
				{
					this.actualSolver=solver;
					size = aa.size();
				}
			}
		}
		return actualSolver;
	}
	
	public void saveSolution()
	{
		FactorySolutionHandler factory = new FactorySolutionHandler();
		ISolutionHandler handler = factory.getLevelLoader(out);
		OutputStream o;
		try 
		{
			if(this.actualSolver == null)
			{
				return;
			}
				
			o = new FileOutputStream(new File(out));

			LinkedList<searchAlgoExtract.Action> actions = generateFullSolution(this.actualSolver.getFinalSolution().solutions_compilation);
			Solution finalFinalSolution = new Solution(actions);
			handler.save(o, finalFinalSolution);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		LinkedList<searchAlgoExtract.Action> actions = generateFullSolution(this.actualSolver.getFinalSolution().solutions_compilation);
		for(Action a: actions)
			System.out.println(a.getAction());
	}
	private LinkedList<searchAlgoExtract.Action> generateFullSolution(LinkedList<Solution> solutions_compilation)
	{
		LinkedList<searchAlgoExtract.Action> actions = new LinkedList<>();

		for(Solution s :solutions_compilation)
		{
			for(searchAlgoExtract.Action a : s.getTheSolution())
			{
				actions.add(a);
			}
		}
		return actions;
	}
	public void loadLevel()
	{
		try 
		{
			InputStream inS = new FileInputStream(in);
			this.level=levelHandler.load(inS);
			if(level == null)
			{
				throw new FileNotFoundException();
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
