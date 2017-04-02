package model.policy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.scene.input.KeyCode;
import sokoban_utils.SokoUtil;



public class InterpeterMove implements Iinterpeter,Serializable {

	private HashMap<String, String> player_index;
	private HashMap<String, String> player_cmd;
	private SokoUtil util;
	public InterpeterMove(){}
	public InterpeterMove(HashMap<String, String> player_index,HashMap<String, String> player_cmd) {
		this.util=new SokoUtil();
		this.player_index=player_index;
		this.player_cmd=player_cmd;
	}
	@Override
	public LinkedList<String> interperate(String cmd) {
		LinkedList<String> params=new LinkedList<String>();
		if(!player_cmd.containsKey(cmd) || !player_index.containsKey(cmd)){
			params.add("move");
			params.add("0");
			return params;
		}
		String command=player_cmd.get(cmd);
		String playerNum=player_index.get(cmd);
		Scanner s=new Scanner(command);
		s.useDelimiter(" ");
		while(s.hasNext()){
			params.add(s.next());
		}
			params.add(1, player_index.get(cmd));
		return params;
	}
	public HashMap<String, String> getPlayer_index() {
		return player_index;
	}
	public void setPlayer_index(HashMap<String, String> player_index) {
		this.player_index = player_index;
	}
	public HashMap<String, String> getPlayer_cmd() {
		return player_cmd;
	}
	public void setPlayer_cmd(HashMap<String, String> player_cmd) {
		this.player_cmd = player_cmd;
	}

}
