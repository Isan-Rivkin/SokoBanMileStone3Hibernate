package common_data.item;

import java.io.Serializable;
/**
 * 
 * @author Daniel Hake & Isan Rivkin
 *
 */
public abstract class CommonItems implements Item,Serializable{
	//Consider adding strategy pattern atm all positions are 2d 
	Position2D position;
	public char id_char;
	
	public void setPosition(Position2D position)
	{
		this.position = position;
	}
	/**
	 * 
	 * @return x->Position[x][y] 
	 */
public int getPosX(){
	return position.getX();
}
/**
 * 
 * @return y->Position[x][y] 
 */
public int getPosY(){
	return position.getY();
}
/**
 * 
 * @return item ID
 */
public char getId_char() {
		return id_char;
	}
/**
 * 
 * @param id_char
 */
	public void setId_char(char id_char) {
		this.id_char = id_char;
	}

	public CommonItems() {
	//this.position=new Position2D();
	}

    public void print(){
    	System.out.println(this.toString());
    }
    
	public Position2D getPosition()
	{
		return position;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 */
    public void setPosition(int x,int y)
    {
    	if(this.position == null)
    		this.position=new Position2D();
    	this.position.setCoordinate(x, y);
    }
    /**
     * 
     * @param pos
     */
	public void setPosition(Position pos) {
		this.position = (Position2D)pos;
	}
	@Override
	public String toString() 
	{
		//return ""+getId_char()+"-"+getPosition(); 
		return ""+getId_char();
	}
	// TODO DELETE ADDED AS TEST FOR STRIPS 
//	@Override
//	public int hashCode() 
//	{
//		return toString().hashCode();
//	}
//	@Override
//	public boolean equals(Object obj) 
//	{
//		return obj.toString().equals(this.toString().substring(0,1));
//	}
}
