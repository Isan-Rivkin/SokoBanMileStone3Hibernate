package common_data.level;

import java.io.FileNotFoundException;

import metadata.LevelModel;
import searching.search_util.SearchUtil;
import sokoban_utils.SokoUtil;

public class LevelTestRun 
{
	public static void main(String[] args) 
	{
		try
		{
			Level old =SearchUtil.loadLevel("./levels/level1.txt");
			SearchUtil.printLevel(old);
			LevelModel model = old.getModel();
			Level newLevel = SokoUtil.generateLevelFromModel(model);
			SearchUtil.printLevel(newLevel);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

}
