package model.data.itemGeneratos;

import common_data.item.BoxOnTarget;
import common_data.item.Item;

public class BoxOnTragetGenerator implements IitemGenerator {

	@Override
	public Item generate() {
		return new BoxOnTarget();
	}

}
