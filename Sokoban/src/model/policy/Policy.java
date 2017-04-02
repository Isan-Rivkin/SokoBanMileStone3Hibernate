package model.policy;

import java.util.ArrayList;

import common_data.item.Item;
/**
 * General Policy interface
 * @author Daniel Hake & Isan Rivkin
 *
 */
public interface Policy {

	boolean walkThroughWall();
    boolean pullBox();
    boolean pushBoxToWall();
    boolean pushBoxToBox();
	boolean pushBoxToBlock();
	boolean pushBoxToFloor();
	boolean walkOnFloor();
	boolean boxToTarget();
	boolean playerToTarget();
	boolean canBeMoved(Item i);
	int howManyCanBeMoved();
	int howManyPlayers();
	boolean allPathCanBeMoved(ArrayList<Item> items);

	
	
}
