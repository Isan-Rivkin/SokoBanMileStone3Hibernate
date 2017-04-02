package model.data.itemGeneratos;

import common_data.item.Item;
import common_data.item.Player;
/**
 * Generates a player
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class PlayerGenerator implements IitemGenerator {

	@Override
	public Item generate() {
		return new Player();
	}

}
