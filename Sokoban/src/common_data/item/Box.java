package common_data.item;

import java.io.Serializable;
/**
 * 
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class Box extends CommonItems implements Movable,Item,Serializable{
	public Box() {
		this.id_char='@';
	}
	@Override
	public String toString() {
		return "@";
	}
}
