package view;

import java.io.OutputStream;

import model.policy.Iinterpeter;



public interface FView {
	public void displayHeaderMessage(String message);
	public void displayFooterMessage(String message);
	public void displayAlert(String message);
	public void redraw(char[][] map_static, char[][] map_movables,int steps);
	public void setInterpeterMove(Iinterpeter interpeterMove);
	public void onExit();
	public void setPort(int port);
	public int getPort();

}
