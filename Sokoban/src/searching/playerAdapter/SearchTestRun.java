package searching.playerAdapter;
import java.io.FileNotFoundException;

import common_data.item.Position2D;
import common_data.level.Level;
import searchable.Searchable;
import searchable.Solution;
import searcher.BFS;
import searching.search_util.SearchUtil;
public class SearchTestRun 
{
	static String path = "./levels/level1.txt";
	public static void main(String[] args) throws FileNotFoundException 
	{
		
		Position2D toHere= new Position2D(10,10);
		Level level = SearchUtil.loadLevel(path);
		SearchUtil.printLevel(level);
		Searchable<SokobanState> adapter = new SokobanSearchAdapter(level.getCharGameBoard(), null,toHere);
		BFS<SokobanState> bfs = new BFS<>();
		System.out.println(SearchUtil.extractPlayerPosition(level));
		Solution sol=bfs.search(adapter);
		if(sol!=null)
			System.out.println(sol);
		else 
			System.out.println("NULL");
	}
}
