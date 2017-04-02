package oldView.displayers;

import java.io.OutputStream;

import common_data.level.Level;
/**
 * a general receiver - displayer
 * @author Daniel Hake & Isan Rivkin
 *
 */
public interface GeneralDisplay {
	public void display(Level l, OutputStream out);

	void display(char[][] map, OutputStream out);
	
}
