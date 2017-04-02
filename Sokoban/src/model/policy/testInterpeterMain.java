package model.policy;

import java.util.HashMap;
import java.util.LinkedList;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class testInterpeterMain {

	public static void main(String[] args) {
		HashMap<String, String> player_index=new HashMap<String, String>();
		HashMap<String, String> player_cmd=new HashMap<String, String>();
		//move up
		player_cmd.put(KeyCode.UP.toString(), "move up");
		player_cmd.put(KeyCode.W.toString(), "move up");
		player_cmd.put("move up", "move up");
		player_cmd.put("w", "move up");
		//move down
		player_cmd.put(KeyCode.DOWN.toString(), "move down");
		player_cmd.put(KeyCode.S.toString(), "move down");
		player_cmd.put("move down", "move down");
		player_cmd.put("s", "move down");
		//move right
		player_cmd.put(KeyCode.RIGHT.toString(), "move right");
		player_cmd.put(KeyCode.D.toString(), "move right");
		player_cmd.put("move right", "move right");
		player_cmd.put("d", "move right");
		//move left
		player_cmd.put(KeyCode.LEFT.toString(), "move left");
		player_cmd.put("move left", "move left");
		player_cmd.put("a", "move left");
		player_cmd.put(KeyCode.A.toString(), "move left");
		
		
		//index up
		player_index.put(KeyCode.UP.toString(), "0");
		player_index.put(KeyCode.W.toString(), "0");
		player_index.put("move up", "0");
		player_index.put("w", "0");
		//index down
		player_index.put(KeyCode.S.toString(), "0");
		player_index.put(KeyCode.DOWN.toString(), "0");
		player_index.put("move down", "0");
		player_index.put("s", "0");
		//index left
		player_index.put(KeyCode.A.toString(), "0");
		player_index.put(KeyCode.LEFT.toString(), "0");
		player_index.put("move left", "0");
		player_index.put("a", "0");
		//index right
		player_index.put(KeyCode.D.toString(), "0");
		player_index.put(KeyCode.RIGHT.toString(), "0");
		player_index.put("move right", "0");
		player_index.put("d", "0");
		
		Iinterpeter interpeter1=new InterpeterMove(player_index, player_cmd);
		 
		IinterpeterCreator xmlCreator=new InterpeterXML();
		
		xmlCreator.saveInterpeter(interpeter1, "./configuration/KeyConfig.xml");
		//Iinterpeter interpeter=xmlCreator.loadInterpeter("./configuration/KeyConfig.xml");
		
		
		
		
//		LinkedList<String> params=new LinkedList<>();
//		
//		params=interpeter.interperate("move up");
//		System.out.println("move up------->");
//		for(String s:params){
//			System.out.print(" "+s);
//		}
//		System.out.println();
//		System.out.println("w------->");
//		params=interpeter.interperate(KeyCode.W.toString());
//		for(String s:params){
//			System.out.print(" "+s);
//		}
//		System.out.println();
//		params=interpeter.interperate(KeyCode.UP.toString());
//		System.out.println("KeyCode.Up------->");
//		for(String s:params){
//			System.out.print(" "+s);
//		}
//		System.out.println();
	}

}
