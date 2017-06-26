package metadata;

import java.util.ArrayList;
import java.util.List;

import coders.Coder;
import common_data.item.Box;
import common_data.item.BoxOnTarget;
import common_data.item.Floor;
import common_data.item.Item;
import common_data.item.Movable;
import common_data.item.Player;
import common_data.item.PlayerOnTarget;
import common_data.item.Position2D;
import common_data.item.Target;
import common_data.item.Wall;
import common_data.level.Level;
import model.data.itemGeneratos.FactoryItemLoader;
import model.data.itemGeneratos.IitemGenerator;
import model.database.HighScoreP;
import model.database.HighScoreQuery;
import model.database.LevelP;
import model.database.PlayerP;
import model.policy.PolicyLevelStructure;
import model.policy.SokobanPolicy;

public class FMGenerator
{
	/**
	 * query models
	 */
	public static HSQueryModel generateHSQueryModel(HighScoreQuery hs)
	{
		return new HSQueryModel(hs.getLevelName(), hs.getPlayerName(), hs.getMaxResults(), hs.getOrderBy(), hs.isDESC());
	}
	/**
	 * data compressor
	 */
	public static Coder getDataCompressor()
	{
		return new Coder();
	}
	/**
	 * entity models
	 */
	public static HighScoreModel generateHighScoreModel(HighScoreP hs)
	{
		HighScoreModel model = new HighScoreModel(hs.getPlayerName(), hs.getLevelName(), hs.getPlayerSteps(), hs.getPlayerTime());
		return model;
	}
	public static HighScoreP getHighScorePojo(HighScoreModel model)
	{
		HighScoreP hs = new HighScoreP(model.getPlayerName(), model.getLevelName(), model.getPlayerSteps(), model.getPlayerTime());
		return hs;
	}
	public static List<HighScoreP> getHSPojoList(List<HighScoreModel> models)
	{
		List<HighScoreP> pojos = new ArrayList<>();
		for(HighScoreModel m : models)
		{
			pojos.add(getHighScorePojo(m));
		}
		return pojos;
	}
	public static PlayerModel generatePlayerModel(PlayerP p)
	{
		PlayerModel model = new PlayerModel(p.getPlayerName());
		return model;
	}
	public static PlayerP getPlayerPojo(PlayerModel model)
	{
		PlayerP p = new PlayerP(model.getPlayerName());
		return p;
	}
	public static DBLevelModel generateDBLevelModel(LevelP l)
	{
		DBLevelModel model = new DBLevelModel(l.getLevelName(),l.getLevelPath(), l.getSerializedLevel(),l.getLevelMinSteps());
		return model;
	}
	public static LevelP getLevelPojo(DBLevelModel model)
	{
		LevelP level = new LevelP(model.getLevelName(), model.getLevelPath());
		level.setLevelMinSteps(model.getLevelMinSteps());
		level.setSerializedLevel(model.getSerializedLevel());
		return level;
	}
	public static LevelModel generateModelFromLevel(Level l)
	{
		return l.getModel();
	}
	   // generate a level from a level model.
	   public static Level generateLevelFromModel(LevelModel levelModel)
	   {
			char [][]modelMap = levelModel.getMap();
			FactoryItemLoader factory_items=new FactoryItemLoader();
			ArrayList<Box> boxes=new ArrayList<Box>();
			ArrayList<Target> targets=new ArrayList<Target>();
			ArrayList<Player> players=new ArrayList<Player>();
			ArrayList<Wall> walls=new ArrayList<Wall>();
			String line="";
			char ch;
			int max_row=0, max_col=0,j;
			PolicyLevelStructure policyOfStructure=new PolicyLevelStructure(new SokobanPolicy());
			max_col=modelMap[0].length;
			max_row=modelMap.length;
			Item map[][]=new Item[max_row][max_col];
			Movable movables[][]=new Movable[max_row][max_col];
			
			for(int i=0;i<max_row;++i){
				for(j=0;j<modelMap[i].length;++j)
				{
					ch=modelMap[i][j];
					IitemGenerator generator=factory_items.getItemGenerator(ch);
					Item item=generator.generate();
					item.setPosition(new Position2D(i,j));
					// identify object
					if (item ==null) continue;
					//player on target
					if(item instanceof PlayerOnTarget)
					{
						generator=factory_items.getItemGenerator(new Target().getId_char());
						Item target=generator.generate();
						target.setPosition(new Position2D(i,j));
						generator=factory_items.getItemGenerator(new Player().getId_char());
						Item player=generator.generate();
						player.setPosition(new Position2D(i,j));
						map[i][j]=(Target)target;
						movables[i][j]=(Player)player;
						targets.add((Target)target);
						players.add((Player)player);
					}
					//Box on target
					if(item instanceof BoxOnTarget)
					{
						generator=factory_items.getItemGenerator(new Target().getId_char());
						Item target=generator.generate();
						target.setPosition(new Position2D(i,j));
						generator=factory_items.getItemGenerator(new Box().getId_char());
						Item box=generator.generate();
						box.setPosition(new Position2D(i,j));
						map[i][j]=(Target)target;
						movables[i][j]=(Box)box;
						targets.add((Target)target);
						boxes.add((Box)box);
					}
					
					
					//taraget
					if(item instanceof Target)
					{
						map[i][j]=(Target)item;
						movables[i][j]=null;
						targets.add((Target)item);
					}
					//wall
					if(item instanceof Wall)
					{
					  map[i][j]=(Wall)item;
					  movables[i][j]=null;
					  walls.add((Wall)item);
					}
					//movable
					if(item instanceof Movable)
					{
						Floor floor=new Floor();
						floor.setPosition(new Position2D(i,j));
						map[i][j]=floor;
						
						if(item instanceof Player)
						{
							movables[i][j]=(Player)item;
							players.add((Player)item);
						}
						if(item instanceof Box)
						{
							movables[i][j]=(Box)item;
							boxes.add((Box)item);
						}
					}
					//floor
					if(item instanceof Floor)
					{
						movables[i][j]=null;
						map[i][j]=(Floor)item;
					}
				}
			for (int k=j;k<max_col;k++)
			{
				Floor floor = new Floor();
				floor.setPosition(new Position2D(i, j));
				map[i][k] = floor;
				movables[i][k] = null;
			}
		}
			Level the_level=new Level(map,movables,max_col,max_row ,boxes, walls, targets, players);
			return the_level;
	   }
}
