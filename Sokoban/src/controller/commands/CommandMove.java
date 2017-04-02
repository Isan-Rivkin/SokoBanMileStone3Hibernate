package controller.commands;

import java.util.LinkedList;

import common_data.level.Level;
import model.FModel;
import model.policy.CalculateMove;
import model.policy.SokobanPolicy;
import sokoban_utils.SokoUtil;
/**
 * when user writes move
 * @author Daniel Hake & Isan Rivkin
 *
 */
public class CommandMove implements Command {

	private CalculateMove calculate_move;
	private String direction;
	private SokoUtil util;
	private Level level;
	private FModel model;
	private int playerNum;
	private boolean valid;
	private LinkedList<String> params;
	public CommandMove() {
		this.valid=false;
		this.playerNum=0;
		this.model=null;
		   this.calculate_move=new CalculateMove(new SokobanPolicy());
	       this.util=new SokoUtil();
	       this.direction=null;
	       this.level=null;
	}
	public CommandMove(FModel model){
		this.valid=false;
		
		this.playerNum=0;
		this.model=model;
		   this.calculate_move=new CalculateMove(model.getPolicy());
	       this.util=new SokoUtil();
	       this.direction=null;
	       this.level=model.getCurrentLevel();
	       this.model=model;
	}
	@Override
	public void execute(){
		if(this.valid){
		model.move(playerNum, direction);	
		}
	
	}
public void init(LinkedList<String> params){
	//first param = playnum, second param = direction
	if(util.isValidDirection(params.getLast())){
	playerNum = Integer.parseInt(params.removeFirst());
	direction=params.removeFirst();
	this.params=params;
	if(playerNum>=0 && model!= null && util.isValidDirection(direction)){
		this.playerNum=playerNum;
		this.valid=true;
	}
	}else{
	params.clear();
	this.valid=false;
	}

}

}
