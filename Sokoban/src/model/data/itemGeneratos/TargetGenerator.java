package model.data.itemGeneratos;

import common_data.item.Item;
import common_data.item.Target;
/**
 * Generates a target
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class TargetGenerator implements IitemGenerator {

	@Override
	public Item generate() {
		return new Target();
	}
}
