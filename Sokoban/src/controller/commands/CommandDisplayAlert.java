package controller.commands;

import java.util.LinkedList;

import controller.server.SokoServer;
import view.FView;

public class CommandDisplayAlert implements Command {
	FView view;
	SokoServer server;
	String message;
	
	public CommandDisplayAlert(SokoServer server, FView view) {
		this.view=view;
		this.server=server;
	}
	@Override
	public void execute() {
		if(view != null){
			view.displayAlert(message);
		}
		if(server != null &&server.hasClient() ){
			server.sendToClient(message);
		}

	}
	@Override
	public void init(LinkedList<String> params) {
		if(params!= null)
			this.message=params.removeLast();

	}
}
