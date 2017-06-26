package common_data.level;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import common_data.item.Box;
import common_data.item.Item;
import common_data.item.Movable;
import common_data.item.Player;
import common_data.item.Position2D;
import common_data.item.Target;
import common_data.item.Wall;
import metadata.LevelModel;
/**
 * 
 * @author Isan Rivkin & Daniel Hake
 * Empty interface: Serializable and is implemented by Level class
 *
 */
public interface LevelInterface extends Serializable {
    /**
     * 
     * @param i row number
     * @param j col number 
     * @return Movable at position [i][j] or null if empty
     */
    public Movable getMovableAtPos(int i, int j);
    /**
     * 
     * @param i
     * @param j
     * @return Item from map[i][j]  or null if dont exist or i,j out of bondries
     */
    public char[][] getCharGameBoard();
    public Item getStaticItemAtPos(int i, int j);
	public int getNumOfPlayers();
    public Item[][] getMap();
	public void setMap(Item[][] map);
	public Movable[][] getMovables();
	public void setMovables(Movable[][] movables);
	public int getHeight();
	public void setHeight(int height);
	public int getWidth();
	public int numOfBoxesOnTargets();
	public boolean isPlayerWon();
	public void setWidth(int width);
	public int getNumOfTargets();
    public int getNumOfWalls();
    public int getNumOfBoxes();
    public Position2D getPlayerPosition(int playerNum);
	public ArrayList<Player> getPlayers();
	public void setPlayers(ArrayList<Player> players);
	public ArrayList<Box> getBoxes();
	public void setBoxes(ArrayList<Box> boxes);
	public ArrayList<Target> getTargets();
	public void setTargets(ArrayList<Target> targets);
	public ArrayList<Wall> getWalls();
	public void setWalls(ArrayList<Wall> walls);
	public int getMin_steps();
	public void setMin_steps(int min_steps);
	public int getCurrent_steps();
	public void setCurrent_steps(int current_steps);
	public Item getItemAtPosition(Position2D pos);
    public Item movableAtPosition(Position2D pos);
    public void updatePlayerPosition(int playerNum, Position2D newPosition);
    public void updatePosition(Position2D oldPos, Position2D newPos);
    public void addStepToPlayer(int playerNum);
    public Item getUniqueItemFromList(Collection<? extends Item> list, Position2D pos );
    public LevelModel getModel();
}
