package controller.commands;

import java.util.LinkedList;

import model.FModel;

public class CommandReset implements Command {

private FModel model;
	
	public CommandReset(FModel model){
		this.model=model;		
	}
	@Override
	public void execute() {
		if(model.getCurrentLevelPath()!=null){
			model.loadLevel(model.getCurrentLevelPath());
		}
	}
	@Override
	public void init(LinkedList<String> params) {
	
	}
}
