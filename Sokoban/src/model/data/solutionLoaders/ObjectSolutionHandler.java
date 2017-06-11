package model.data.solutionLoaders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import common_data.level.Level;
import searchAlgoExtract.Solution;
import sokoban_utils.SerializationUtil;

public class ObjectSolutionHandler implements ISolutionHandler {

	@Override
	public Solution load(InputStream in) {
		Solution s = new Solution();
		
		//Level l=null;
		try {
			s = (Solution)SerializationUtil.deserialize(in);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	@Override
	public void save(OutputStream out, Solution solution) {
	      try {
				SerializationUtil.serialize(solution,out);
			} catch (IOException e) {
				e.printStackTrace();
			}		
	}

}
