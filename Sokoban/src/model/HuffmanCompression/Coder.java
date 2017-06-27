package model.HuffmanCompression;
import java.util.List;

import searchable.Action;

public class Coder implements ICoder {
	private HuffmanCoder h;
	private SolutionCoder s;
	
	public Coder() 
	{
		s = new SolutionCoder();
		h = new Huffman();
	}
	
	/**
	 * generates string map from char map
	 * @param map
	 * @return
	 */
	public String getStringMap(char[][] map)
	{
		StringBuilder sb = new StringBuilder();
		int i,j;
		for(i=0;i<map.length;++i)
		{
			for(j=0;j<map[0].length;++j)
			{
				sb.append(map[i][j]);
			}
			sb.append("~");
		}
//		System.out.println("sb is : " + sb.toString()+" = " +sb.toString().indexOf("~") + "===" + getHeightByTilda(sb.toString()));
		return sb.toString();
	}

	/**
	 * extracting the width of the level from the string
	 * @param map
	 * @return
	 */
	public int getWidthByTilda(String map)
	{
		int i = map.indexOf("~");
		if (i==0)
		{
			System.out.println("Coder sys: No Lines Here");
			return 0;
		}
		else return i;
	}
	
	/**
	 * extracting the height of the map from string
	 * @param map
	 * @return
	 */
	public int getHeightByTilda(String map)
	{
		char c = '~';
		int i = 0;
		int count = 0;
		while(i<map.length())
		{
			if(c==map.charAt(i))
				count++;
			i++;
		}
		return count;
	}
	
	/**
	 * get
	 * @param map
	 * @return
	 */
	public char[][] getCharMapFromStr(String map)
	{
		int width = getWidthByTilda(map);
		int height = getHeightByTilda(map);
		char[][] newMap = new char[height][width];
		int runOnString=0;
		int cols=0;
		int rows=0;
		while(runOnString<map.length())
		{
		if(cols>width)
			break;
		if(map.charAt(runOnString)=='~')
			{
				runOnString++;
				rows++;
				cols=0;
				if(rows>height || runOnString == map.length())
					break;
			}
		
		char c = map.charAt(runOnString);
		newMap[rows][cols] = c;
		cols++;
		runOnString++;
		}
		return newMap;
	}
	
	/**
	 * encoding level using huffman code
	 */
	@Override
	public String levelEncoder(char[][] map) 
	{
		String strMap = getStringMap(map);
		String code = h.encode(strMap);
		return code;
	}

	/**
	 * decoding the level back from huffman code
	 */
	@Override
	public char[][] levelDecoder(String huff) 
	{
		String decodedMap = h.decode(huff);
		char[][] map = new char[getWidthByTilda(decodedMap)][getHeightByTilda(decodedMap)];
		map = getCharMapFromStr(decodedMap);
		return map;
	}

	/**
	 * zipping solution to string
	 */
	@Override
	public String solutionEncoder(List<Action> sol) 
	{
		String strSol = s.encodeSol(sol);
		return strSol;
	}

	/**
	 * unzippping string back to solution
	 */
	@Override
	public List<Action> solutionDecoder(String sol)
	{
		List<Action> solList = s.decodeSol(sol);
		return solList;
	}

	/**
	 * getter to solution coder
	 */
	@Override
	public SolutionCoder getSolCoder() {
		return s;
	}

	/**
	 * getter to huffman coder
	 */
	@Override
	public HuffmanCoder getLevelCoder() {
		return h;
	}
	
}
