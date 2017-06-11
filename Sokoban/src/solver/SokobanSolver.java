package solver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import common_data.level.Level;
import model.data.levelLoaders.FactoryLevelLoader;
import model.data.levelLoaders.ILevelLoader;
import model.data.solutionLoaders.FactorySolutionHandler;
import model.data.solutionLoaders.ISolutionHandler;
import model.data.solutionLoaders.TxtSolutionHandler;
import planning.plannable.PlanUtil;
import planning.plannable.Plannable;
import planning.plannable.PlannableCreator;
import planning.plannable.SokoHeuristics;
import planning.planner.Action;
import planning.planner.Planner;
import planning.planner.Strips;
import searchAlgoExtract.Solution;
import searching.search_util.SearchUtil;
import sokoban_utils.SokoUtil;

public class SokobanSolver extends Thread implements ISolver
{
	private String in;
	private String out;
	private ILevelLoader levelHandler;
	private FactoryLevelLoader factory_loader;
	private Level level;
	private SokoUtil sokoUtil;
	private FinalSolution finalSolution;
	private SokoHeuristics heuristics;
	private int attemptNum;
	private boolean solved;
	
	public SokobanSolver(FinalSolution finalSolution, SokoHeuristics heuristics, int attemp,Level level) 
	{
		this.level=level;
		this.attemptNum=attemp;
		this.solved=false;
		this.heuristics = heuristics;
		sokoUtil = new SokoUtil();
		this.finalSolution = finalSolution;
	}
	@Override
	public void run() 
	{
		this.solve();
	}

	public boolean solve()
	{
			PlannableCreator creator = new PlannableCreator(finalSolution,heuristics,attemptNum);
			Plannable adapter = creator.createPlannable(level.getCharGameBoard());
			Planner strips = new Strips();
			List<Action> plans = strips.plan(adapter);
			if(plans == null)
				System.err.println("plan is null");
			if(plans != null)
			{	
				this.solved=true;
				return true;
			}
			this.solved=false;
		return false;
	}
	public boolean isSolved()
	{
		return solved;
	}
	public FinalSolution getFinalSolution()
	{
		return finalSolution;
	}
	
}
