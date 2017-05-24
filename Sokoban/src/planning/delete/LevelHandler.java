package planning.delete;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import common_data.item.Player;
import common_data.item.Position2D;
import common_data.level.Level;
import model.data.levelLoaders.FactoryLevelLoader;
import model.data.levelLoaders.ILevelLoader;

public class LevelHandler
{

	Level level;
	public Level handle(String path) throws FileNotFoundException
	{
		FactoryLevelLoader fac_loader = new FactoryLevelLoader();
		ILevelLoader loader=fac_loader.getLevelLoader(path);
		InputStream in = new FileInputStream(path);
		this.level=loader.load(in);
		return level;
	}
	public static void printLevel(Level k)
	{
		if(k==null)
			return;
		printCharLevel(k.getCharGameBoard());
	}
	public static void printCharLevel(char[][] map)
	{
		for(int i=0;i<map.length;++i)
		{
			for(int j=0;j<map[0].length;++j)
			{
				System.out.print(map[i][j]);
			}
		
			System.out.println();
		}
	}
	public Position2D extractPlayerPosition(Level l)
	{
		return extractCharPlayerPosition(l.getCharGameBoard());
	}
	public Position2D extractCharPlayerPosition(char[][] charGameBoard) 
	{
		Player p = new Player();
		for(int i=0;i<charGameBoard.length;++i)
		{
			for(int j=0; j<charGameBoard[0].length;++j)
			{
				if(charGameBoard[i][j] == p.getId_char())
				{
					return new Position2D(i,j);
				}
			}
		}
		return null;
	}
}
