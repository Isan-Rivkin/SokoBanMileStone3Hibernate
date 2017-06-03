//package solver;
//
//import common_data.item.Position2D;
//import common_data.level.Level;
//import searchAlgoExtract.BFS;
//import searchAlgoExtract.Searchable;
//import searchAlgoExtract.Searcher;
//import searchAlgoExtract.Solution;
//import searching.boxAdapter.BoxSearchAdapter;
//import searching.boxAdapter.BoxState;
//import searching.search_util.SearchUtil;
//
//
//public class BaseSolver implements ISolver
//{
//
//	@Override
//	public Solution solveLevel(Level l) 
//	{
//		Searchable<BoxState> adapter = new BoxSearchAdapter(level.getCharGameBoard(), new Position2D(3,3), new Position2D(1,17));
//		Searcher<BoxState> searcher = new BFS<BoxState>();
//		Solution sol = searcher.search(adapter);
//		return sol;
//	}
//	
//	public class BoxPathOne
//	{
//		public Position2D boxSource,destination;
//	}
//}
