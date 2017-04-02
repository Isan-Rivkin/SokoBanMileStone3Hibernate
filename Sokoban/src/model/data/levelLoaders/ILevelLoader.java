package model.data.levelLoaders;

import java.io.InputStream;
import java.io.OutputStream;

import common_data.level.Level;
/**
 * Aswers for 1-4:<p>
 * 1) The Map,inside "Level", only contains the data and any action we want to do will be with another interface who will be charge of it. Like in the case of Level-Loader that loads the level from where the user is asking and getting the required data to the Map inside Level to keep it. <p>
 * 2) The Open/Closed principle is about keeping the code closed for changes but still open for extensions. Here you can find Level-Loader as a good example how you must use that interface to load new level but if you need a new way, that you can't find in the project, to load a level, you create a new level-loader-specific that implements Level-Loader so the code remains the same but you extended it for your needs.<p>
 * 3) Liskuv principle is shown in the implementation of InputSteam, we use a general InputStream when reading a level with the Level-Loader, so when-ever we need a specific stream you can use without any problem.<p>
 * 4) We are using InputStream because it's open for implementation of any stream we need, instead of the String that will stabilize the code for one kind of stream.
 *   
 * @author Isan Rivkin & Daniel Hake
 *
 */
public interface ILevelLoader {
	public Level load(InputStream in);

	public void save(OutputStream out, Level level);
}
