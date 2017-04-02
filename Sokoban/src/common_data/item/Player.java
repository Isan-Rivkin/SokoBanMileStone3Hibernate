package common_data.item;

import java.io.Serializable;
/**
 * 
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class Player extends CommonItems implements Movable,Item,Serializable{
	private int score;
	private int current_steps;
	
	public Player() {
		this.current_steps=0;
		this.id_char='A';
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCurrent_steps() {
		return current_steps;
	}

	public void setCurrent_steps(int current_steps) {
		this.current_steps = current_steps;
	}
	public void addStep(){
		this.current_steps+=1;
	}
	
}
