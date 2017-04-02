package model.data.itemGeneratos;

import common_data.item.Box;
import common_data.item.Item;
import common_data.item.Wall;
/**
 * Generates a wall
 * @author Daniel
 *
 */
public class WallGenerator implements IitemGenerator {

	@Override
	public Item generate() {
		return new Wall();
	}

}
