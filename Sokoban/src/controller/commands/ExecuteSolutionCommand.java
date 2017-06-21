package controller.commands;

import java.util.LinkedList;

import model.FModel;
import searchable.Action;
import searchable.Solution;
import view.FView;

public class ExecuteSolutionCommand implements Command
{

	private FModel model;
	private FView view;
	private String levelPath;
	private LinkedList<String> solution;
	
	public ExecuteSolutionCommand(FModel model, FView view) 
	{
		this.model=model;
		this.view=view;
	}
	@Override
	public void execute() 
	{
		//model.loadLevel(levelPath);
		LinkedList<Action> actions = new LinkedList<>();
		for(String s : solution)
		{
			actions.add(new Action(s));
		}
		Solution sol = new Solution(actions);
		view.executeSolution(sol);
	}

	@Override
	public void init(LinkedList<String> params)
	{
		levelPath = params.removeFirst();
		solution = params;
	}
	
}
