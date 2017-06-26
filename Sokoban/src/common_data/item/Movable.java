package common_data.item;

import java.io.Serializable;
/**
 * General movable interface
 * @author Daniel Hake & Isan Rivkin
 *
 */
public interface Movable extends Serializable{
	
	public char getId_char();	
	public int getPosX();
	public int getPosY();
	
}
