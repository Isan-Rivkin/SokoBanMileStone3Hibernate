package controller.commands;

import java.io.OutputStream;
import java.util.LinkedList;

import javax.swing.plaf.synth.SynthSpinnerUI;

import common_data.item.Box;
import common_data.item.BoxOnTarget;
import common_data.item.Floor;
import common_data.item.Player;
import common_data.item.PlayerOnTarget;
import common_data.item.Target;
import common_data.item.Wall;
import controller.server.SokoServer;
import model.FModel;
import sokoban_utils.SokoUtil;
import view.FView;
/**
 * When User writes display
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class CommandDisplay implements Command {

	
	private char[][] map;
	private char[][] map_static;
	private char[][] map_movables;
	private SokoUtil util;
	private OutputStream out;
	private boolean valid;
	private FView view;
	private FModel model;
	private SokoServer server;
	private LinkedList<String> params;

	//TDL delete default ctor
	public CommandDisplay(FView view, FModel model, SokoServer server){
		this.map_movables=null;
		this.map_static=null;
		this.view=view;
		this.model=model;
		valid=false;
		util=new SokoUtil();
		map=null;
		this.server=server;
		this.params=new LinkedList<String>();
	}
	@Override
	public void execute() {
	if(this.valid){
		extractMovablesStaticCharMap();
		view.redraw(map_static, map_movables,model.getPlayerSteps(0));
		if(server!=null && server.hasClient()){
			server.sendMapToClient(map_movables, map_static);
		}
	
	}else{
		util.notifyUser("View,Display", "Cannot display");
	}
	}
	
	public void init(LinkedList<String> params){
		valid=true;
		this.params=params;
		this.map=model.getCurrentCharLevel();
	}
	private void extractMovablesStaticCharMap(){
		this.map_movables=new char[map.length][map[0].length];
		this.map_static=new char[map.length][map[0].length];
		
		for(int i=0;i<this.map.length;++i){
			for(int j=0;j<this.map[0].length;++j){
				char ch=map[i][j];
				if(ch ==new PlayerOnTarget().getId_char() || ch==new BoxOnTarget().getId_char()){
					map_movables[i][j]=util.getMovableChar(ch);
					map_static[i][j]=new Target().getId_char();
					}
				else if(util.isStatic(ch)){
					map_movables[i][j]='N';
					map_static[i][j]=ch;
				}else if(!util.isStatic(ch)){
					map_movables[i][j]=ch;
					map_static[i][j]=new Floor().getId_char();}
				}
			}
		
		}
	
	}

