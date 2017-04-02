package model.data.levelLoaders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import common_data.level.Level;
import sokoban_utils.SerializationUtil;
/**
 * Specific object level loader
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class ObjLevelLoader implements ILevelLoader {

	@Override
	public Level load(InputStream in) {
		Level l=null;
		try {
			l = (Level)SerializationUtil.deserialize(in);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return l;
	}

	@Override
	public void save(OutputStream out , Level level) {
	      try {
			SerializationUtil.serialize(level,out);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
