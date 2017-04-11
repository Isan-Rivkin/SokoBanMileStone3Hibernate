package controller.commands;

import java.util.LinkedList;

import model.FModel;
import model.database.LevelP;

public class CommandInseretLevelDB implements Command {

	private FModel model;
	private String lName;
	private String path;
	
	public CommandInseretLevelDB(FModel model) 
	{
		this.model=model;
		this.lName="";
		this.path="";
	}
	
	@Override
	public void execute() 
	{
		LevelP line = new LevelP(lName, path);
		model.save(line);
	}

	@Override
	public void init(LinkedList<String> params) 
	{
		this.lName=params.removeFirst();
		this.path= params.removeFirst();

	}

}
