package controller.commands;

import java.util.LinkedList;

import model.FModel;
import sokoban_utils.SokoUtil;

public class CommandSignUp implements Command {

	private FModel model;
	private String pName;
	private Long time;

	
	public CommandSignUp(FModel model) 
	{
		this.model = model;
		this.time=null;
		this.pName="";
	}
	
	@Override
	public void execute() 
	{	
		//new Thread(()->
	//	{
			model.signUpHighScore(pName, SokoUtil.extractLevelNameFromPath(model.getCurrentLevelPath()), model.getPlayerSteps(0), time);
	//	});
	}

	@Override
	public void init(LinkedList<String> params) 
	{
		this.pName = params.removeFirst();
		this.time=Long.parseLong(params.removeFirst());

	}

}
