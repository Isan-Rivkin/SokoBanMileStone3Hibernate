package model.HuffmanCompression;
import java.util.List;

import searchable.Action;
/**
 * Compression for level maps and solutions.
 * Levels are compressed with Huffman compression algorithms.
 * Solutions are compressed by an algorithm we developed.
 * @author Isan Rivkin and Daniel Hake.
 *
 */
public interface ICoder 
{
	/**
	 * Compress a level.
	 * @param map
	 * @return encoded Huffman level.
	 */
	public String levelEncoder(char[][] map);
	/**
	 * 
	 * @param sol -encode a solution.
	 * @return compressed string of the solution.
	 */
	public String solutionEncoder(List<Action> sol);

	public char[][] levelDecoder(String huff);
	public List<Action> solutionDecoder(String sol);
	public SolutionCoder getSolCoder();
	public HuffmanCoder getLevelCoder();
}
