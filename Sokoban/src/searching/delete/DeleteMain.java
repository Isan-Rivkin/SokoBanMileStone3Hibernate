package searching.delete;

import java.io.FileNotFoundException;

import common_data.item.Position2D;
import common_data.level.Level;
import planning.delete.LevelHandler;

public class DeleteMain 
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		SearchGoalState sgs = new SearchGoalState();
		LevelHandler handler = new LevelHandler();
		Level l = handler.handle("./myLevels/search1.txt");
		Level k = l.getCopy();
		Position2D playerPos = handler.extractPlayerPosition(l);
		System.out.println(playerPos);
		Level goalLevel = sgs.initObjectGoalState(l, playerPos, new Position2D(1,5));
		if(goalLevel == null)
			System.out.println("goal level = null");
		handler.printLevel(l);
		handler.printLevel(goalLevel);
	}

}
