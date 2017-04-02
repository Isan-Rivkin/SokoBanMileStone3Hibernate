package controller.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javafx.beans.Observable;
import model.policy.Iinterpeter;

public interface ClientHandler{
	public void addObserverToHandler(SokoServer s);
	public void handleClient(InputStream in, OutputStream out);
	public void addMessageToQueue(String line);
	public void stop();
	public void setMoveInterpeter(Iinterpeter interpeter);

}
