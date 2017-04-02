package model.data.itemGeneratos;

import common_data.item.Floor;
import common_data.item.Item;
/**
 * Generate a floor
 * @author Daniel
 *
 */
public class FloorGenerator implements IitemGenerator {

	@Override
	public Item generate() {
		return new Floor();
	}

}
