package common_data.item;

import java.io.Serializable;

public class BoxOnTarget implements Item,Serializable
{
	private char char_id;
	
	public BoxOnTarget() 
	{	this.
		char_id='$';
	}
	@Override
	public String toString() {
		return "$";
	}
	@Override
	public void setPosition(Position pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Position2D getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public char getId_char() {
		return char_id;
	}

	@Override
	public int getPosX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPosY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
