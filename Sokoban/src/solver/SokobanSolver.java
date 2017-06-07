package solver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import common_data.level.Level;
import model.data.levelLoaders.FactoryLevelLoader;
import model.data.levelLoaders.ILevelLoader;
import planning.plannable.PlanUtil;
import planning.plannable.Plannable;
import planning.plannable.PlannableCreator;
import planning.planner.Action;
import planning.planner.Planner;
import planning.planner.Strips;
import searchAlgoExtract.Solution;
import searching.search_util.SearchUtil;
import sokoban_utils.SokoUtil;

public class SokobanSolver implements ISolver
{
	private String in;
	private String out;
	private ILevelLoader levelHandler;
	private FactoryLevelLoader factory_loader;
	private Level level;
	private SokoUtil sokoUtil;
	private FinalSolution finalSolution;
	public SokobanSolver() 
	{
		sokoUtil = new SokoUtil();
		finalSolution = new FinalSolution();
	}
	
	@Override
	public Solution solveLevel(Level l) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void defineLevelPath(String in, String out) 
	{
		this.in=in;
		this.out=out;
		factory_loader =new FactoryLevelLoader();
		if(!(sokoUtil.isValidFileType(sokoUtil.extractFileType(in)) && sokoUtil.isFileExist(in)))
		{
			System.out.println("INVALID FILE TYPE OR FILE DOESNT EXIST");
		}
		levelHandler = factory_loader.getLevelLoader(in);
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
	public boolean solve()
	{
		for(int i=0;i<level.getTargets().size()+1;++i)
		{
			PlannableCreator creator = new PlannableCreator(finalSolution);
			Plannable adapter = creator.createPlannable(level.getCharGameBoard());
			Planner strips = new Strips();
			List<Action> plans = strips.plan(adapter);
			for(Solution sol : finalSolution.solutions_compilation)
			{
				System.out.println(sol);
			}
			if(plans != null)
				break;
		}
		return false;
	}

}
