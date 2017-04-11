package controller.commands;

import java.util.LinkedList;

import view.FView;

public class CommandWinningAlert implements Command {

	private FView view; 
	private String message;
	
	public CommandWinningAlert(FView view) 
	{
		this.view=view;
	}
	@Override
	public void execute()
	{
		view.displayWinningAlert(message);
	}

	@Override
	public void init(LinkedList<String> params)
	{
		message=params.remove();

	}

}
