package view;

import java.util.LinkedList;
import java.util.List;

import model.database.HighScoreP;
import model.policy.Iinterpeter;
import searchable.Solution;



public interface FView {
	public void displayHeaderMessage(String message);
	public void displayFooterMessage(String message);
	public void displayAlert(String message);
	public void displayWinningAlert(String message);
	public void redraw(char[][] map_static, char[][] map_movables,int steps);
	public void setInterpeterMove(Iinterpeter interpeterMove);
	public void onExit();
	public void setPort(int port);
	public int getPort();
	//highscore
	public void updateHighScoreTable(List<HighScoreP> list);
	
}
