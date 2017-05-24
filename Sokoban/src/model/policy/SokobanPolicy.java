package model.policy;

import java.util.ArrayList;

import common_data.item.Box;
import common_data.item.Floor;
import common_data.item.Item;
import common_data.item.Target;
import sokoban_utils.SokoUtil;
/**
 * Specific game policy
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class SokobanPolicy implements Policy {

	private int allowd_steps;
	private SokoUtil util;
	private int allowed_players_num;
	public SokobanPolicy() 
	{
		util=new SokoUtil();
		this.allowed_players_num=1;
		allowd_steps=1;
	}
	
	@Override
	public boolean walkThroughWall() 
	{
		return false;
	}

	@Override
	public boolean pullBox()
	{
		return false;
	}

	@Override
	public boolean pushBoxToWall() 
	{
		return false;
	}

	@Override
	public boolean pushBoxToBox()
	{
		return false;
	}

	public boolean pushBoxToBlock() 
	{
		return false;
	}

	public boolean pushBoxToFloor() 
	{
		return true;
	}

	public boolean walkOnFloor()
	{
		return true;
	}

	@Override
	public boolean boxToTarget()
	{
		return true;
	}

	@Override
	public boolean playerToTarget()
	{
		return true;
	}

	@Override
	public boolean canBeMoved(Item i) 
	{
		
		if(i instanceof Box)
		{
			return true;
		}
		return false;
	}

	@Override
	public int howManyCanBeMoved() 
	{
		return this.allowd_steps;
	}

	@Override
	public boolean allPathCanBeMoved(ArrayList<Item> items) 
	{
		if(items == null || items.size()<1)
		{
			return false;
		}
			
		
		int count=0;
		int totalNum=items.size()-1;
		if(totalNum > this.howManyCanBeMoved())
		{
			return false;
		}
			
		for (int i=0;i<totalNum;++i)
		{
			if(this.canBeMoved(items.get(i)))
			{
				count++;
			}
				
		}
		if(count==totalNum && items.get(count) instanceof Floor || items.get(count) instanceof Target)
		{
			return true;
		}
			
	return false;
	}
	
	
	public int howManyPlayers() {
		return this.allowed_players_num;
	}
}



