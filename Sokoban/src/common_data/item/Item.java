package common_data.item;

import java.io.Serializable;
/**
 * General Item interface
 * @author Daniel Hake & Isan Rivkin
 *
 */
public interface Item extends Serializable {
public void setPosition(Position pos);
public Position2D getPosition();
public void print();
public char getId_char();
public int getPosX();
public int getPosY();

}

