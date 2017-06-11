package controller.commands;

import java.util.LinkedList;

import model.FModel;
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
		System.out.println("THIS IS THE SOLUTION IM GETTING ");
		for(String s : solution)
		{
			System.out.println("---------------------------");
			System.out.println(s);
		}
		//view.executeSolution(solution);
	}

	@Override
	public void init(LinkedList<String> params)
	{
		levelPath = params.removeFirst();
		solution = params;
	}
	
}
