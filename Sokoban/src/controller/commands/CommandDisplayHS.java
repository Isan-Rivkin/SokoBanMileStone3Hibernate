package controller.commands;

import java.util.LinkedList;
import java.util.List;

import model.FModel;
import model.database.HighScoreP;
import view.FView;

public class CommandDisplayHS implements Command 
{

	private FModel model;
	private FView view;
	private List<HighScoreP> list;
	
	public CommandDisplayHS(FModel model, FView view) 
	{
		this.view = view;
		this.model = model;
		this.list = null;
	}
	
	@Override
	public void execute() 
	{
		view.updateHighScoreTable(list);
	}

	@Override
	public void init(LinkedList<String> params) 
	{
		list = model.getCurrentHighScoreList();
	}

}
