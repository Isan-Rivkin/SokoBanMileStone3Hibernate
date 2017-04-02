package model.policy;

import common_data.level.Level;

public interface Imove {
	public boolean move(Level l, String direction);
	public Level getLevel();
}
