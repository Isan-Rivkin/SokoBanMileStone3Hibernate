package model.data.levelLoaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

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
import model.policy.PolicyLevelStructure;
import model.policy.SokobanPolicy;
import sokoban_utils.SokoUtil;
/**
 * Specific txt level loader
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class TxtLevelLoader implements ILevelLoader {
	SokoUtil util;
	public TxtLevelLoader() {
	 util=new SokoUtil();
	}
	/**
	 * loads the level
	 * @param in
	 * @return
	 */
	@Override
	public Level load(InputStream in) {
		
		FactoryItemLoader factory_items=new FactoryItemLoader();
		BufferedReader reader= new BufferedReader(new InputStreamReader(in));
		ArrayList<Box> boxes=new ArrayList<Box>();
		ArrayList<Target> targets=new ArrayList<Target>();
		ArrayList<Player> players=new ArrayList<Player>();
		ArrayList<Wall> walls=new ArrayList<Wall>();
		String line="";
		char ch;
		int max_row=0, max_col=0,j;
		PolicyLevelStructure policyOfStructure=new PolicyLevelStructure(new SokobanPolicy());
		ArrayList<String> level_from_txt=new ArrayList<String>();
		try { 
			while((line=reader.readLine())!= null){
				level_from_txt.add(line);
				if(line.length()>=max_col){
					max_col=line.length();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		max_row=level_from_txt.size();
		Item map[][]=new Item[max_row][max_col];
		Movable movables[][]=new Movable[max_row][max_col];
		
		for(int i=0;i<max_row;++i){
			for(j=0;j<level_from_txt.get(i).length();++j){
				ch=level_from_txt.get(i).charAt(j);
				IitemGenerator generator=factory_items.getItemGenerator(ch);
				Item item=generator.generate();
				item.setPosition(new Position2D(i,j));
				// identify object
				if (item ==null) continue;
				//player on target
				if(item instanceof PlayerOnTarget){
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
				if(item instanceof BoxOnTarget){
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
				if(item instanceof Target){
					map[i][j]=(Target)item;
					movables[i][j]=null;
					targets.add((Target)item);
				}
				//wall
				if(item instanceof Wall){
				  map[i][j]=(Wall)item;
				  movables[i][j]=null;
				  walls.add((Wall)item);
				}
				//movable
				if(item instanceof Movable){
					Floor floor=new Floor();
					floor.setPosition(new Position2D(i,j));
					map[i][j]=floor;
					
					if(item instanceof Player){
						movables[i][j]=(Player)item;
						players.add((Player)item);
					}
					if(item instanceof Box){
						movables[i][j]=(Box)item;
						boxes.add((Box)item);
					}
				}
				//floor
				if(item instanceof Floor){
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
		
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Level the_level=new Level(map,movables,max_col,max_row ,boxes, walls, targets, players);

		if(policyOfStructure.isLevelLegal(the_level))
			return the_level;
		util.notifyUser("LevelLoader", "Level Structure is illegal, not loaded.");
		return null;
	}
	/**
	 * Saves the level
	 * @param out
	 * @param level
	 */
	@Override
	public void save(OutputStream out, Level level) {
		PrintWriter writer=new PrintWriter(out);
		char ch;
		Movable movables[][]=level.getMovables();
		Item map[][]=level.getMap();

		int row=level.getHeight();
		int col=level.getWidth();
		for(int i=0;i<row;++i){
			for(int j=0;j<col;j++){
				
				if(movables[i][j]==null){
				     ch=map[i][j].getId_char();
				}else{
					ch=movables[i][j].getId_char();
					if (ch== new Box().id_char && map[i][j] instanceof Target)
						ch=new BoxOnTarget().getId_char();
					if(ch==new Player().id_char && map[i][j] instanceof Target)
						ch=new PlayerOnTarget().getId_char();
				}
				writer.print(ch);
			}
			writer.println();
		}
         //writer.close();
		   writer.flush();
	}	 
	}
	
	

