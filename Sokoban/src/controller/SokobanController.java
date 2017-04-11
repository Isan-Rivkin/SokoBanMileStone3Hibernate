package controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import controller.commands.Command;
import controller.commands.CommandDBSearch;
import controller.commands.CommandDisplay;
import controller.commands.CommandDisplayAlert;
import controller.commands.CommandDisplayFooterMessage;
import controller.commands.CommandDisplayHS;
import controller.commands.CommandDisplayHeaderMessage;
import controller.commands.CommandExit;
import controller.commands.CommandInseretLevelDB;
import controller.commands.CommandLevelSaver;
import controller.commands.CommandLoadLevel;
import controller.commands.CommandMove;
import controller.commands.CommandReset;
import controller.commands.CommandSignUp;
import controller.commands.CommandWinningAlert;
import controller.general_controller.GeneralController;
import controller.server.CLIHandler;
import controller.server.SokoServer;
import model.FModel;
import model.policy.Iinterpeter;
import sokoban_utils.SokoUtil;
import view.FView;

public class SokobanController implements FController,Observer {
	private FModel model;
	private FView view;
	private GeneralController controller;
	private SokoServer server;
	private HashMap<String, Command> create_cmd;
	private SokoUtil util;
	private boolean keepRunning;
	private InputStream in;
	private OutputStream out;
	private Iinterpeter interpeter;
	public SokobanController(FModel model, FView view,Iinterpeter iinterpeter, int port) {
		this.controller=new GeneralController();
		this.model=model;
		this.interpeter=iinterpeter;
		this.view=view;
		this.util=new SokoUtil();
		this.keepRunning=true;
		this.in=null;
		this.out=null;
		if(port > 999 && port < 10000){
			this.view.setPort(port);
			this.server=new SokoServer(port,interpeter ,new CLIHandler());
			this.server.addObserver(this);
			this.server.start();
		}
		initCommandsMap();
	}
	private void initCommandsMap(){
		this.create_cmd=new HashMap<String,Command>();
		this.create_cmd.put("display", new CommandDisplay(view, model, server));
		this.create_cmd.put("move",    new CommandMove(model));
		this.create_cmd.put("load",    new CommandLoadLevel(model));
		this.create_cmd.put("save",    new CommandLevelSaver(model));
		this.create_cmd.put("exit",    new CommandExit(view, model, server, this));
		this.create_cmd.put("msgheader", new CommandDisplayHeaderMessage(server, view));
		this.create_cmd.put("alert", new CommandDisplayAlert(server,view));
		this.create_cmd.put("msgfooter",new CommandDisplayFooterMessage(server, view));
		this.create_cmd.put("reset", new CommandReset(model));
		this.create_cmd.put("search", new CommandDBSearch(model));
		this.create_cmd.put("signup", new CommandSignUp(model));
		this.create_cmd.put("displayhs", new CommandDisplayHS(model,view));
		this.create_cmd.put("winalert", new CommandWinningAlert(view));
		this.create_cmd.put("insertleveltodb", new CommandInseretLevelDB(model));
		
	}
	@Override
	public void update(Observable o, Object arg) {
		LinkedList<String> params = (LinkedList<String>) arg;
		String commandKey = params.removeFirst();
		Command c = create_cmd.get(commandKey);
		if(c==null){
			view.displayHeaderMessage("Command not found");
			return;
		}
		c.init(params);
		controller.insertCommand(c);

	}
	@Override
	public void start() {
		controller.start();
	}
	@Override
	public void stop() {
		controller.stop();
	}

	
}
