package model.policy;

import java.util.ArrayList;

import common_data.item.Item;
import common_data.item.Movable;
import common_data.item.Position2D;
import common_data.level.Level;

import sokoban_utils.SokoUtil;
/**
 * 
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class CalculateMove implements Imove{
	private Position2D source,destination;
	private Policy policy;
	private Item itemAtDest;
	private ArrayList<Item> path;
	private Level level;
	private SokoUtil util;
	
	public CalculateMove(Policy policy){
		this.util=new SokoUtil();
		this.source=null;
		this.destination=null;
		this.itemAtDest=null;
		this.policy=policy;
		this.level=null;
		this.path=new ArrayList<Item>();
	}
	/**
	 * 
	 * @param level
	 * @param direction
	 * @return If the move happened
	 */
	public boolean move(Level level,String direction){
		if(util.isValidDirection(direction)){
		this.level=level;
		boolean canBeMoved=false;
		source=level.getPlayerPosition(0); 
		path=this.getPathToDestination(direction);
		if(path != null)
			canBeMoved=policy.allPathCanBeMoved(path);
		if(canBeMoved){
			this.updateLevel();
		return true;	
		}
		}
		
	return false;
	}
	public boolean isPossibleMovement(Level level,String direction)
	{
//		if(util.isValidDirection(direction))
//		{
//			this.level=level;
//			SearchGoalState sgs = new SearchGoalState();
//			boolean canBeMoved=false;
//			source=level.getPlayerPosition(0); 
//			path=this.getPathToDestination(direction);
//			if(path != null)
//				canBeMoved=policy.allPathCanBeMoved(path);
//			if(canBeMoved)
//			{
//				return true;	
//			}
//		}
		return false;
	}
	/**
	 * getting an array of items that will be moved
	 * @param direction
	 * @return an array after an update of the positions
	 */
	// get the full path
	private ArrayList<Item> getPathToDestination(String direction)
	{
		Movable movables[][]=level.getMovables();
//		for(int i=0;i<movables.length;++i)
//		{
//			for(int j=0;j<movables[0].length;++j)
//			{
//				if(movables[i][j] == null)
//					System.out.print("+");
//				else
//					System.out.print(movables[i][j].getId_char());
//			}
//			System.out.println();
//		}
		Item map[][]=level.getMap();
		int lenthTillNull=0;
		int startX=source.getX();
		int startY=source.getY();
		int maxSteps=policy.howManyCanBeMoved();
		int y,x;
		switch(direction){
		//move right path
		case "right":
				y=startY+1;
			while(y < level.getWidth() && movables[startX][y] != null){
				path.add((Item)movables[startX][y]);
				y++;
			}
			if(y<level.getWidth())
			path.add(map[startX][y]);
			break;
		//move left path
		case "left":
			 	y=startY-1;
			 	while(y>=0 && movables[startX][y]!=null){
			 		path.add((Item)movables[startX][y]);
			 		y--;
			 	}
			 	if(y>=0)
			 	path.add(map[startX][y]);
			break;
		//move up path 
		case "up":
			x= startX-1;
			while(x>=0 && movables[x][startY]!= null){
				path.add((Item)movables[x][startY]);
				x--;
			}
			if(x>=0){
				path.add(map[x][startY]);}
			break;
	    //move down path
		case "down":
			x=startX+1;
			while(x<level.getHeight() && movables[x][startY]!=null)
			{
				path.add((Item)movables[x][startY]);
				x++;
			}
			if(x<level.getHeight())
				path.add(map[x][startY]);
			break;
		default:
			break;
		}
		
		return path;
	}
	/**
	 * updating the level
	 */
/**
 * The method uses 2 vectors.
 * oldPos vector is all the positions of the path
 * the path vector of all items.
 * iterate both and update new position and old position though level.updatePosition(oldPosition, newPosition);
 */
private void updateLevel()
{
	
	if(path.size()==1)
	{
		level.updatePosition(source, path.get(0).getPosition());
	}else{
			ArrayList<Position2D> oldPos=util.createPosFromPath(path);
			for(int i=path.size()-2;i>=0;i--){
				level.updatePosition(new Position2D(path.get(i).getPosX(), path.get(i).getPosY()),new Position2D(oldPos.get(i+1).getX(),oldPos.get(i+1).getY()));
			}
			level.updatePosition(source, new Position2D(oldPos.get(0).getX(),oldPos.get(0).getY()));
		}
}
public Level getLevel()
{
	path=new ArrayList<Item>();
	return level;
}
}
