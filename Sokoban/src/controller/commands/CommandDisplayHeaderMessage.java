package controller.commands;

import java.util.LinkedList;

import controller.server.SokoServer;
import view.FView;

public class CommandDisplayHeaderMessage implements Command {

	private SokoServer server;
	private FView view;
	private LinkedList<String> params;
	
	public CommandDisplayHeaderMessage(SokoServer server, FView view) {
		this.params=null;
		this.server=server;
		this.view=view;
	}
	@Override
	public void execute() {
		String line=params.removeFirst();
		if(server != null && server.hasClient())
			server.sendToClient(line);
		if(view !=null)
			view.displayHeaderMessage(line);
	}

	@Override
	public void init(LinkedList<String> params) {
		if(params !=null)
			this.params=params;

	}

}
