package solver;

import java.io.InputStream;
import java.io.OutputStream;

import common_data.level.Level;
import searchAlgoExtract.Solution;

public interface ISolver
{
	// from where to read level && where to save the level
	public void defineLevelPath(String in,String out);
	public Solution solveLevel(Level l);
}


