package model.data.solutionLoaders;

import java.io.InputStream;
import java.io.OutputStream;

import searchable.Solution;

public interface ISolutionHandler {
	public Solution load(InputStream in);

	public void save(OutputStream out, Solution solution);
}
