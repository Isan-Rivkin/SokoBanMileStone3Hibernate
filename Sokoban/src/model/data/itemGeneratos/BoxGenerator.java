package model.data.itemGeneratos;

import common_data.item.Box;
import common_data.item.Item;
/**
 * Generets a box
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class BoxGenerator implements IitemGenerator {

	@Override
	public Item generate() {
		return new Box();
	}

}
