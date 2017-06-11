package model.data.levelLoaders;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import common_data.level.Level;
import sokoban_utils.XMLUtil;
/**
 * Specific xml level loader
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class XMLLevelLoader implements ILevelLoader {
	/**
	 * loads the level
	 * @param in
	 * @return level
	 */
	@Override
	public Level load(InputStream in) {
		Level l = null;
		try {
			 l=(Level)XMLUtil.decodeXML(in);
			
		} catch (FileNotFoundException e) {
            System.out.println(e.toString());
			e.printStackTrace();
		}
	
		return l;
	}
	/**
	 * Saves the level
	 * @param out
	 * @param level
	 */
	@Override
	public void save(OutputStream out, Level level)
	{
		try
		{
			XMLUtil.encodeXML(level, out);
		}
		catch (FileNotFoundException e) 
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}

}
