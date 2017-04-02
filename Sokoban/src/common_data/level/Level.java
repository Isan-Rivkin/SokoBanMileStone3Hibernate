package common_data.level;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import common_data.item.Box;
import common_data.item.BoxOnTarget;
import common_data.item.Item;
import common_data.item.Movable;
import common_data.item.Player;
import common_data.item.PlayerOnTarget;
import common_data.item.Position2D;
import common_data.item.Target;
import common_data.item.Wall;
/**
 * @author Isan Rivkin and Daniel Hake
 * @param map 2d matrix of all the non Movable objecs in the game (floor, wall) 
 * @param movables 2d matrix of all the movable objects in the game (box, player)
 * @param players holds all the instances  of players in the game
 * @param boxes  holds all the instances  of boxes
 * @param targets holds all the instances  of targets
 * @param walls holds all the instances  of walls 
 * @param min_steps number of minimum steps required to finish the level
 * @param current_Steps the current amount of steps the user takes
 * @param height the number of rows in the map 
 * @param width the number of columns in the map
 * 
 *
 */
//test for git

public class Level implements LevelInterface,Serializable{

	
	private Item map[][];
	private Movable movables[][];
	private ArrayList<Player> players;
    private ArrayList<Box> boxes;
    private ArrayList<Target> targets;
    private ArrayList<Wall> walls;
    private int min_steps;
    private int current_steps;
    private int height;
    private int width;
    private boolean alreadyWon;
    
    //ctors
    public Level(){
    	this.alreadyWon=false;
    	this.map=null;
    	this.movables=null;
    	this.players=new ArrayList<Player>();
    	this.targets=new ArrayList<Target>();
    	this.boxes=new ArrayList<Box>();
    	this.walls=new ArrayList<Wall>();
    	this.min_steps=0;
    	this.current_steps=0;
    	this.height=0;
    	this.width=0;
    	
    }
    public Level(Item map[][], Movable movables[][], int max_col, int max_row,  ArrayList<Box> boxes, ArrayList<Wall> walls, ArrayList<Target> targets,ArrayList<Player> players){    	
    	this.min_steps=0;
    	this.current_steps=0;
    	this.height=max_row;
    	this.width=max_col;
    	this.map=map;
    	this.players=players;
    	this.targets=targets;
    	this.boxes=boxes;
    	this.walls=walls;
    	this.movables=movables;
    }
    ///methods 
    
    /**
     * 
     * @param i row number
     * @param j col number 
     * @return Movable at position [i][j] or null if empty
     */
    public Movable getMovableAtPos(int i, int j){
    	return movables[i][j];
    }
    /**
     * 
     * @param i
     * @param j
     * @return Item from map[i][j]  or null if dont exist or i,j out of bondries
     */
    public Item getStaticItemAtPos(int i, int j){
    	if(i<this.height && j< this.width)
    	return map[i][j];
    return null;
    }

	public int getNumOfPlayers(){
    return this.players.size();	
    }
    public Item[][] getMap() {
		return map;
	}
	public void setMap(Item[][] map) {
		this.map = map;
	}
	public Movable[][] getMovables() {
		return movables;
	}
	public void setMovables(Movable[][] movables) {
		this.movables = movables;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	/**
	 * calculate the current number of boxes on a target
	 * @return int 
	 */
	public int numOfBoxesOnTargets(){
		int count=0;
	     for(int i=0;i<height;++i){
	    	 for(int j=0;j<width;++j){
	    		 if(movables[i][j] instanceof Box && map[i][j] instanceof Target)
	    			count++; 
	    	 }
	     }
		return count;
	}
/**
 * determines if a player has won the game -> All boxes are on ALL targets.
 * @return boolean True if the player won.
 */
	public boolean isPlayerWon(){
		if(this.numOfBoxesOnTargets() == boxes.size() && boxes.size()== targets.size()){
			this.alreadyWon=true;
			return true;
		}
		return false;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getNumOfTargets(){
    return this.targets.size();	
    }
    public int getNumOfWalls(){
    return this.walls.size();	
    }
    public int getNumOfBoxes(){
    return this.boxes.size();	
    }
    /**
     * Returns the player according to its num, when 1 player only -> playerNum=0
     * @param playerNum index of a plater 
     * @return {@link Position2D} 
     */
    public Position2D getPlayerPosition(int playerNum){
    	return players.get(playerNum).getPosition();
    }
    

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Box> getBoxes() {
		return boxes;
	}

	public void setBoxes(ArrayList<Box> boxes) {
		this.boxes = boxes;
	}

	public ArrayList<Target> getTargets() {
		return targets;
	}

	public void setTargets(ArrayList<Target> targets) {
		this.targets = targets;
	}

	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public void setWalls(ArrayList<Wall> walls) {
		this.walls = walls;
	}

	public int getMin_steps() {
		return min_steps;
	}

	public void setMin_steps(int min_steps) {
		this.min_steps = min_steps;
	}

	public int getCurrent_steps() {
		return current_steps;
	}

	public void setCurrent_steps(int current_steps) {
		this.current_steps = current_steps;
	}
	/**
	 * 
	 * @param pos a given position of (x,y)
	 * @return Item at a given position in the Level or else null
	 */
	public Item getItemAtPosition(Position2D pos){
		Item i=movableAtPosition(pos);
		if(i != null){
			return i;
		}
		int x=pos.getX();
		int y=pos.getY();
		//TDL gvulut gizra #11
		i=map[x][y];
		return i;
	}
   /**
    * returns a movable at a give position or else null
    * @param pos 2d position
    * @return Item 
    */
    public Item movableAtPosition(Position2D pos){

    	for (Box b : boxes){
    		if (pos.getX() == b.getPosition().getX() && pos.getY()==b.getPosition().getY())
    		{
    			return b;
    		}
    	}
    	for(Player p: players){
    		if (pos.getX() == p.getPosition().getX() && pos.getY()==p.getPosition().getY())
    		{
    			return p;
    		}
    	}
		return null;
    }
    
    
    public void updatePlayerPosition(int playerNum, Position2D newPosition){
    	Position2D oldPos=players.get(playerNum).getPosition();
    	movables[oldPos.getX()][oldPos.getY()]=null;
    	players.get(playerNum).setPosition(newPosition);
    	movables[newPosition.getX()][newPosition.getY()]=players.get(playerNum);
    	
    }
/**
 * The method will recive 2 positions and move the object[oldPos] to object[newPos]
 * @param oldPos - source destination at the map 
 * @param newPos - target position at the map
 */

    public void updatePosition(Position2D oldPos, Position2D newPos){
    	this.current_steps++;
    	Movable i= getMovableAtPos(oldPos.getX(),oldPos.getY());
    	/**
    	 * update the list if the instance is box or Player
    	 * */ 
    	if (i instanceof Box){
    		getUniqueItemFromList(boxes, oldPos).setPosition(newPos);
    	}
    	if (i instanceof Player){
    	    getUniqueItemFromList(players, oldPos).setPosition(newPos);
    	    Player p=(Player)getUniqueItemFromList(players,newPos);
    	    if(p!=null){
    	    p.setCurrent_steps(p.getCurrent_steps()+1);
    	    }
    	}
    	// update the matrix movables [][] 
    	movables[newPos.getX()][newPos.getY()]=movables[oldPos.getX()][oldPos.getY()];
    	movables[oldPos.getX()][oldPos.getY()]=null;
    }
    
    /**
     * private method used to return a unique Item from the map according to a give position
     * 
     * @param list
     * @param pos
     * @return Item or null if the Item does not exist in the lists.
     */
    public Item getUniqueItemFromList(Collection<? extends Item> list, Position2D pos ){
    	for (Item m : list){
    		if (m.getPosX() == pos.getX() && m.getPosY() == pos.getY() && pos.getX()<height && pos.getY()<width){
    			return m;
    		}
    	}
    	return null;
    }
    /**
     * update +1 step to a player 
     * @param playerNum index of player, if only one than 0;
     */
 public void addStepToPlayer(int playerNum){
	 players.get(playerNum).addStep();
 }
	@Override
	public char[][] getCharGameBoard() {
		if(map != null && map !=null){
		char[][] char_map=new char[this.height][this.width];
		char ch;
		int row=this.getHeight();
		int col=this.getWidth();
		for(int i=0;i<row;++i){
			for(int j=0;j<col;j++){
				
				if(movables[i][j]==null){
				     char_map[i][j]=map[i][j].getId_char();
				}else{
					if(map[i][j] instanceof Target){
						if(movables[i][j] instanceof Box){
							char_map[i][j]=new BoxOnTarget().getId_char();
						}
						if(movables[i][j] instanceof Player){
							char_map[i][j]=new PlayerOnTarget().getId_char();
						}
					}else
					char_map[i][j]=movables[i][j].getId_char();
				}
				
			}
		}
		return char_map;
	}
		return null;
	}
	public boolean alreadyWon() {
		return this.alreadyWon;
	}
	public void setAlreadyWon(boolean b) {
		this.alreadyWon=false;
	}

}
