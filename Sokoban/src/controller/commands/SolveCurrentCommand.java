package controller.commands;

import java.util.LinkedList;

import model.FModel;
import view.FView;

public class SolveCurrentCommand implements Command
{
	private FModel model;
	private FView view;
	public SolveCurrentCommand(FModel model, FView view)
	{
		this.model=model;
	}
	@Override
	public void execute()
	{
		model.solveCurrentLevel();
	}

	@Override
	public void init(LinkedList<String> params) 
	{
		// TODO Auto-generated method stub
		
	}

}
