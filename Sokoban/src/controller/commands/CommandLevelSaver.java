package controller.commands;

import java.io.OutputStream;
import java.util.LinkedList;

import model.FModel;
import sokoban_utils.SokoUtil;
/**
 * when user writes save
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class CommandLevelSaver implements Command {
	private OutputStream out;
	private String path;
	private FModel model;
	private boolean valid;
	private SokoUtil util;
	private LinkedList<String> params;
	
	public CommandLevelSaver() {
		this.out=null;
		this.model=null;
		this.valid=false;
		this.util=new SokoUtil();
	}
	public CommandLevelSaver(FModel model){
		this.model=model;
		this.valid=false;
		this.util=new SokoUtil();
		this.out=null;
		this.params=new LinkedList<String>();
	}
	@Override
	public void execute(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				model.saveCurrentLevel(path);
				
			}
		}).start();
		
	}
	public void init(LinkedList<String> params){
			this.path=params.removeFirst();
			this.valid=true;
	}
	
}
