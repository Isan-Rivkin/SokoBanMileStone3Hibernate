package controller.commands;

import java.sql.Time;
import java.util.LinkedList;

import controller.SokobanController;
import controller.server.SokoServer;
import model.FModel;
import view.FView;
/**
 *  When user writes Exit
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class CommandExit implements Command {

	private FView view;
	private FModel model;
	private SokoServer server;
	private SokobanController controller;

	public CommandExit(FView view,FModel model, SokoServer server, SokobanController controller) {
		this.view=view;
		this.model=model;
		this.server=server;
		this.controller=controller;
	}
	@Override
	public void execute() {
		if(server!=null){
		server.sendToClient("Closing Connection.");
		server.stop();
		}
		view.displayHeaderMessage("Closing game.");
		view.onExit();
		controller.stop();
		
	}
	
	@Override
	public void init(LinkedList<String> params) {
		
	}
	
	

}
