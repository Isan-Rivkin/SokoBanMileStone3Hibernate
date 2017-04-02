package common_data.item;

import java.io.Serializable;
/**
 * General position interface
 * @author Daniel Hake & Isan Rivkin
 *
 */
public interface Position extends Serializable {
	public int getX();
	public int getY();
	public void setX(int x);
	public void setY(int y);
	public void setCoordinate(int x, int y);
	
}
