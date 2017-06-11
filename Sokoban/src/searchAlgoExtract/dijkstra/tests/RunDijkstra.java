package searchAlgoExtract.dijkstra.tests;

import java.io.FileNotFoundException;

import common_data.item.Position2D;
import common_data.level.Level;
import searchAlgoExtract.Searchable;
import searchAlgoExtract.Searcher;
import searchAlgoExtract.Solution;
import searchAlgoExtract.dijkstra.Dijkstra;
import searchAlgoExtract.dijkstra.DijkstraBad;
import searching.boxAdapter.BoxSearchAdapter;
import searching.boxAdapter.BoxState;
import searching.search_util.SearchUtil;

public class RunDijkstra {

	public static void main(String[] args) throws FileNotFoundException 
	{
		Level level= SearchUtil.loadLevel("./levels/level1.txt");
		SearchUtil.printCharLevel(level.getCharGameBoard());
		Searchable<BoxState> adapter = new BoxSearchAdapter(level.getCharGameBoard(), new Position2D(2,6), new Position2D(1,17));
		Searcher<BoxState> searcher = new Dijkstra<BoxState>();
		Solution sol = searcher.search(adapter);
		System.out.println(sol);
	}

}
