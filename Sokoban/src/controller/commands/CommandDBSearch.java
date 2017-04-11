package controller.commands;

import java.util.LinkedList;

import model.FModel;
import sokoban_utils.SokoUtil;

public class CommandDBSearch implements Command
{

	private FModel model;
	private String lName;
	private String pName;
	private String flag;
	private String orderBy;
	
	
	public CommandDBSearch(FModel model) 
	{
		this.model = model;
		this.pName="";
		this.lName="";
		this.flag="";
		this.orderBy="";
	}
	
	@Override
	public void execute() 
	{
		new Thread(()->
		{
			if (flag.equals("l") && lName.equals(""))
			{
				model.search(flag, SokoUtil.extractLevelNameFromPath(model.getCurrentLevelPath()), pName,orderBy);
			}
			else
			{
				model.search(flag,lName,pName, orderBy);
			}
		}).start();
	}

	@Override
	public void init(LinkedList<String> params) 
	{
		this.flag=params.removeFirst();
		this.lName=params.removeFirst();
		this.pName=params.removeFirst();
		this.orderBy=params.removeFirst();
	}

}