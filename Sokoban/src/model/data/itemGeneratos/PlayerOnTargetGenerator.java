package model.data.itemGeneratos;

import common_data.item.Item;
import common_data.item.PlayerOnTarget;

public class PlayerOnTargetGenerator implements IitemGenerator {

	@Override
	public Item generate() {
		return new PlayerOnTarget();
	}

}
