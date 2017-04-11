package sokoban_utils;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

import common_data.item.Box;
import common_data.item.Floor;
import common_data.item.Item;
import common_data.item.Movable;
import common_data.item.Player;
import common_data.item.PlayerOnTarget;
import common_data.item.Position2D;
import common_data.item.Target;
import common_data.item.Wall;
import javafx.scene.input.KeyCode;
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

}
