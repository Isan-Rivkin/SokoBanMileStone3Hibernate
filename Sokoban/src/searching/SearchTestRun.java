package searching;

import java.io.FileNotFoundException;
import java.util.HashMap;

import bfs.Action;
import bfs.BFS;
import bfs.Searchable;
import bfs.Solution;
import bfs.State;
import common_data.item.Position2D;
import common_data.level.Level;
import model.policy.CalculateMove;
import model.policy.Policy;
import model.policy.SokobanPolicy;
import planning.delete.LevelHandler;
import searching.delete.SearchGoalState;

public class SearchTestRun {
	static String path = "./myLevels/search1.txt";
	public static void main(String[] args) throws FileNotFoundException 
	{
		LevelHandler handler = new LevelHandler();
		Level l =handler.handle(path);
		Position2D takeMe = new Position2D(1,2);
		Position2D toHere= new Position2D(1,3);
					
		Policy policy = new SokobanPolicy();
		SokobanSearchAdapter adapter=new SokobanSearchAdapter(l,new CalculateMove(policy));
		adapter.setCoordinate(takeMe, toHere);
		handler.printLevel(l);
		BFS<SokobanState> bfs = new BFS<>();
		Solution sol=bfs.search(adapter);
		if(sol!=null)
			System.out.println(sol);
		else 
			System.out.println("!NULL");
	}

}
