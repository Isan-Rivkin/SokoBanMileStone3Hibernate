package model.data.solutionLoaders;

import java.io.InputStream;
import java.io.OutputStream;

import common_data.level.Level;
import searchAlgoExtract.Solution;

public interface ISolutionHandler {
	public Solution load(InputStream in);

	public void save(OutputStream out, Solution solution);
}
