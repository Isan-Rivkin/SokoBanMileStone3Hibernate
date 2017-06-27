package model.HuffmanCompression;
import java.util.List;

import searchable.Action;

public interface ICoder 
{
	public String levelEncoder(char[][] map);
	public String solutionEncoder(List<Action> sol);
	public char[][] levelDecoder(String huff);
	public List<Action> solutionDecoder(String sol);
	public SolutionCoder getSolCoder();
	public HuffmanCoder getLevelCoder();
}
