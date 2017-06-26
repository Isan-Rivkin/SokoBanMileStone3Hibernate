package sokoban_utils;

import java.io.File;
import java.io.PrintStream;
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
import javafx.scene.input.KeyCode;
import metadata.LevelModel;
import model.data.itemGeneratos.FactoryItemLoader;
import model.data.itemGeneratos.IitemGenerator;
import model.policy.PolicyLevelStructure;
import model.policy.SokobanPolicy;
public class SokoUtil {
	
	public SokoUtil(){
		
	}

	public String extractCmd(String cmd){
		String command="";
		if(cmd.indexOf(' ') == -1){
			return cmd;
		}
		for (int i=0;i<cmd.length();++i){
			if(cmd.charAt(i)==' '){
				command=cmd.substring(0,i);
				return command;
			}
				
		}
		return command;
		
	}
	public boolean isValidCommand(String cmd){
		String c=extractCmd(cmd);
		ArrayList<String> valid_cmd=new ArrayList<String>();
		valid_cmd.add("load");
		valid_cmd.add("exit");
		valid_cmd.add("move");
		valid_cmd.add("save");
		valid_cmd.add("display");
		valid_cmd.add("reset");
		valid_cmd.add("msg");
		valid_cmd.add("d");
		valid_cmd.add("s");
		valid_cmd.add("a");
		valid_cmd.add("w");
		valid_cmd.add(KeyCode.W.toString());
		valid_cmd.add(KeyCode.S.toString());
		valid_cmd.add(KeyCode.A.toString());
		valid_cmd.add(KeyCode.D.toString());
		valid_cmd.add(KeyCode.UP.toString());
		valid_cmd.add(KeyCode.LEFT.toString());
		valid_cmd.add(KeyCode.RIGHT.toString());
		valid_cmd.add(KeyCode.DOWN.toString());
		if(valid_cmd.contains(c))
			return true;
		return false;
	}
	public boolean isValidMoveCommand(String cmd){
		String c=extractCmd(cmd);
		ArrayList<String> valid_cmd=new ArrayList<String>();
		valid_cmd.add("move");
		valid_cmd.add("d");
		valid_cmd.add("s");
		valid_cmd.add("a");
		valid_cmd.add("w");
		valid_cmd.add(KeyCode.W.toString());
		valid_cmd.add(KeyCode.S.toString());
		valid_cmd.add(KeyCode.A.toString());
		valid_cmd.add(KeyCode.D.toString());
		valid_cmd.add(KeyCode.UP.toString());
		valid_cmd.add(KeyCode.LEFT.toString());
		valid_cmd.add(KeyCode.RIGHT.toString());
		valid_cmd.add(KeyCode.DOWN.toString());
		if(valid_cmd.contains(c))
			return true;
		return false;
	}
   public String extractFileType(String path){
		String type="";
        int i = path.lastIndexOf('.');
        if (i > 0) {
        type = path.substring(i+1);
       }               
	   return type;
   }
   public String extractInfo(String s){
		String info="";
        int i = s.lastIndexOf(' ');
        if (i > 0) {
        info = s.substring(i+1);
       }               
	   return info;
   }
   public void print2DItemMap(Item[][] map){
	   for(Item[] i_line: map){
		   for(Item i:i_line){
			   System.out.print(i.getId_char());
		   }
		   System.out.println();
		   
	   }
	   
   }
   public void print2DMovablesMap(Movable[][] map, int row, int col){
	   for(int i=0;i<row;++i){
		   for(int j=0;j<col;j++){
			   if(map[i][j] == null)
				   System.out.print('#');
			   else
				   System.out.print(map[i][j].getId_char());
		   }
		   System.out.println();
	   }
   }
   public boolean isValidDirection(String directions){
	   ArrayList<String> valid_directions=new ArrayList<String>();
	   valid_directions.add("up");
	   valid_directions.add("right");
	   valid_directions.add("down");
	   valid_directions.add("left");
	   valid_directions.add("w");
	   valid_directions.add("d");
	   valid_directions.add("a");
	   valid_directions.add("s");
	   if(valid_directions.contains(directions))
		   return true;
	   return false;
   }
   public boolean isValidFileType(String type){
	   ArrayList<String> valid_types=new ArrayList<String>();
	   valid_types.add("txt");
	   valid_types.add("xml");
	   valid_types.add("ser");
	   valid_types.add("obj");
	   if(valid_types.contains(type))
		   return true;
	   return false;
   }
   public boolean isFileExist(String filePathString){
	   File f = new File(filePathString);
	   if(f.exists() && !f.isDirectory()) { 
	       return true;
	   }
	return false;
   }
   public ArrayList<Position2D> createPosFromPath(ArrayList<Item> path){
	   ArrayList<Position2D> oldPos=new ArrayList<Position2D>();
		for(Item i:path){
			oldPos.add(new Position2D(i.getPosX(),i.getPosY()));
		}
		return oldPos;
   }
   public void notifyUser(String from, String msg){
	   PrintStream out=System.out;
	   out.println("["+from+"]: "+msg);
   }
   // input= * or $ 
   //return A or @ accordingly
	public char getMovableChar(char movableOnTarget){
		if(movableOnTarget == new PlayerOnTarget().getId_char())
			return new Player().getId_char();
		else
			return new Box().getId_char();
	}
	//if the given char is a static object
	public boolean isStatic(char ch){
		if(ch == new Wall().getId_char() || ch ==new Floor().getId_char() || ch==new Target().getId_char())
			return true;
		return false;
	}
	//extract level name from a given full path 
	   public static String extractLevelNameFromPath(String path)
	   {
		    if(path == null)
		    {
		    	path="";
		    	return path;
		    }
		    int j = path.lastIndexOf('\\');    
		    String type="";
	        int i = path.lastIndexOf('.');
	        if(i == -1)
	        	return type;
	        if (i >= 0)
	        {
	        type = path.substring(j+1,i);
	        }     
		   return type; 
	   }
	   // error debug help
	   public static void printError(Object caller,String error, boolean allowMessages)
	   {
		   if(!allowMessages)
			   return;
		   String openning = "U: ";
		   System.out.println(openning + caller.toString());
		   System.out.println(openning + error);
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
