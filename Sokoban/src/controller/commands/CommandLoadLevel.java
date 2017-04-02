package controller.commands;

import java.util.LinkedList;

import model.FModel;
import sokoban_utils.SokoUtil;
/**
 * when user writes load
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class CommandLoadLevel implements Command {
 
	private SokoUtil util;
	private String path;
	private FModel model;
	private LinkedList<String> params;
	
	public CommandLoadLevel() {
		System.out.println("cmd-load-level: I am being called");
		this.model=null;
		this.util=new SokoUtil();
 
	}
	public CommandLoadLevel(FModel model){
		this.model=model;
		this.util=new SokoUtil();
	}
	public void init(LinkedList<String> params){
		this.params=params;
		this.path=params.removeFirst();
	}
	@Override
	public void execute(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
				model.loadLevel(path);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}
}
	




