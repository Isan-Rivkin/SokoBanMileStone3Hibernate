package planning;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import blocksworld.BlocksWorldAdapter;
import common_data.level.Level;
import model.data.levelLoaders.FactoryLevelLoader;
import model.data.levelLoaders.ILevelLoader;
import plannable.Plannable;
import planners.Plan;
import planners.Planner;
import planners.Strips;
import planning.delete.LevelHandler;
import planning.delete.StripsShit;

public class PlanTest
{
	static String path = "./myLevels/planner1.txt";
	public static void main(String[] args) throws FileNotFoundException 
	{
		
		LevelHandler handler = new LevelHandler();
		Level l =handler.handle(path);
		handler.printLevel(l);
		
		Planner planner = new StripsShit();
		Plannable plannable = new SokobanPAdapter(l);
		Plan plan = planner.computePlan(plannable, null);
		System.out.println(plan);
	}

}
