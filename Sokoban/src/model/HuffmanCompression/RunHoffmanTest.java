package model.HuffmanCompression;

import java.util.LinkedList;

import searchable.Action;
import searching.search_util.SearchUtil;

public class RunHoffmanTest
{

	public static void main(String[] args)
{
		ICoder c1 = new Coder();
		ICoder c2 = new Coder();
		ICoder c3 = new Coder();
		LinkedList<Action> sol = new LinkedList<Action>();
		int i = 0;
		while(i<50)
		{
			Action activi = new Action("move up");
			sol.add(activi);
			i++;
		}
		System.out.println(c1.solutionEncoder(sol));
		for (Action aaa : c1.solutionDecoder(c1.solutionEncoder(sol)))
		{
			System.out.println(aaa.getAction());
		}

		char[][] map1 = {{'#','#','#','#'},
						{'#','a',' ','#'},
						{'#','@',' ','#'},
						{'#','o',' ','#'},
						{'#','#','#','#'}};
		char[][] map2 = {{'#','#','#','#'},
						{'#','o',' ','#'},
						{'#','@',' ','#'},
						{'#','a',' ','#'},
						{'#','#','#','#'}};
		char[][] map3 = {{'#','#','#','#'},
						{'#',' ','a','#'},
						{'#',' ','@','#'},
						{'#',' ','o','#'},
						{'#','#','#','#'}};
//		System.out.println(c1.levelEncoder(map1));
//		SearchUtil.printCharLevel(c1.levelDecoder(c1.levelEncoder(map1)));
		// check encode 
//		System.out.println("encoding equels : ");
//		System.out.println("code1 vs. coder2");
//		System.out.println("1 == 2 : "+(c1.levelEncoder(map1)==c2.levelEncoder(map2)));
//		System.out.println("1 == 3 : "+(c1.levelEncoder(map1)==c2.levelEncoder(map3)));
//		System.out.println("2 == 3 : "+(c1.levelEncoder(map2)==c2.levelEncoder(map3)));
//		System.out.println("code1 vs. coder3");
//		System.out.println("1 == 2 : "+(c1.levelEncoder(map1)==c3.levelEncoder(map2)));
//		System.out.println("1 == 3 : "+(c1.levelEncoder(map1)==c3.levelEncoder(map3)));
//		System.out.println("2 == 3 : "+(c1.levelEncoder(map2)==c3.levelEncoder(map3)));
//		System.out.println("code3 vs. coder2");
//		System.out.println("1 == 2 : "+(c3.levelEncoder(map1)==c2.levelEncoder(map2)));
//		System.out.println("1 == 3 : "+(c3.levelEncoder(map1)==c2.levelEncoder(map3)));
//		System.out.println("2 == 3 : "+(c3.levelEncoder(map2)==c2.levelEncoder(map3)));
//		// check decode
//		System.out.println("decoding equels:");
//		char[][] d1 = c2.levelDecoder(c1.levelEncoder(map1));
//		char[][] d2 = c3.levelDecoder(c1.levelEncoder(map2));
//		char[][] d3 = c1.levelDecoder(c1.levelEncoder(map3));
//		System.out.println("1 == 2 : "+(d1==d2));
//		System.out.println("1 == 3 : "+(d1==d3));
//		System.out.println("2 == 3 : "+(d3==d2));
//		
//		
//		Action a1 = new Action("move up");
//		Action a2 = new Action("move up");
//		Action a3 = new Action("move up");
//		Action a4 = new Action("move up");
//		Action a5 = new Action("move up");
//		Action a6 = new Action("move up");
//		Action a7 = new Action("move up");
//		Action a8 = new Action("move up");
//		Action a9 = new Action("move up");
//		sol.add(a);
//		sol.add(a1);
//		sol.add(a2);
//		sol.add(a3);
//		sol.add(a4);
//		sol.add(a5);
//		sol.add(a6);
//		sol.add(a7);
//		sol.add(a8);
//		String encoded = c1.solutionEncoder(sol);
//		System.out.println("sol encode : " +encoded);
//		List<Action> decoded = c1.solutionDecoder(encoded);
//		for(Action aa : decoded)
//		System.out.println("sol decoded : " + aa.getAction());
//		SolutionCoder sc = c1.getSolCoder();
//		List<String> dirs = sc.getDirList(decoded);
//		for (String s : dirs)
//		{
//			System.out.println("Move " +s);
//		}
//		
	}

}
