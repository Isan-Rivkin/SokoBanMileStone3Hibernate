package solver;

import java.util.List;

import common_data.level.Level;
import model.data.levelLoaders.FactoryLevelLoader;
import model.data.levelLoaders.ILevelLoader;
import plannableP.Plannable;
import plannerP.Action;
import plannerP.Planner;
import plannerP.Strips;
import planning.plannable.PlannableCreator;
import planning.plannable.SokoHeuristics;
import sokoban_utils.SokoUtil;
/**
 * A statless instance for inner use by MainSolve.java class.
 * each SokobanSolver represents a different possible solution.
 * some will fail, some will get a solution.
 * @author Isan Rivkin and Daniel Hake.
 *
 */
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
