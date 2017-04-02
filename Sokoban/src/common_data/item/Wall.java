package common_data.item;

import java.io.Serializable;
/**
 * 
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class Wall extends CommonItems implements Item,Serializable{
	public Wall() {
		this.id_char='#';
	}
}
