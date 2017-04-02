package controller.commands;

import java.util.LinkedList;

/**
 * 
 * @author Daniel Hake & Isan Rivkin
 *
 */
public interface Command {

	public void execute();
	public void init(LinkedList<String> params);
}
