package controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import model.policy.Iinterpeter;
import sokoban_utils.SokoUtil;

public class SokoServer extends Observable implements Observer{

	private int port;
	private ClientHandler ch;
	private volatile boolean hasClient;
	private volatile boolean stop;
	private SokoUtil util;
	private Iinterpeter interpeter;

	public SokoServer(int port, Iinterpeter interpeter,ClientHandler ch) {
		this.interpeter=interpeter;
		this.port=port;
		this.ch=ch;	
		this.stop=false;
		this.hasClient=false;
		this.util=new SokoUtil();
		this.ch.addObserverToHandler(this);
		this.ch.setMoveInterpeter(interpeter);
	}
	
	public void runServer() throws IOException{
		ServerSocket server=new ServerSocket(port);
		
		util.notifyUser("Server", "Server is alive listening on port: "+port);
		server.setSoTimeout(1000);
		while(!stop){
			try{
			Socket aClient=server.accept();
			util.notifyUser("Server", "Client has connected");
			this.hasClient=true;
					ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
					//aClient.getInputStream().close();
				//	aClient.getOutputStream().close();
					aClient.close();
					System.out.println("Client has left and hopefully died.");

			}catch(SocketTimeoutException e){}
		}
		server.close();
	}
	public void start(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					runServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	public void stop(){
		this.stop=true;
		this.ch.stop();
		util.notifyUser("Server", "The server is dead");
	}

	@Override
	public void update(Observable o, Object arg) {
		LinkedList<String> params=(LinkedList<String>)arg;
		String cmd=params.getFirst();
		if(cmd.equals("disconnected"))
		{
			this.hasClient=false;
		}else{
		setChanged();
		notifyObservers(arg);
		}
	}
	public boolean hasClient(){
		return this.hasClient;
	}
	public void sendToClient(String line){
		ch.addMessageToQueue(line);
	}
	public void sendMapToClient(char[][] map_movables,char [][] map_static){

		if(map_movables == null || map_static== null){
			this.sendToClient("No map to display.");
			return;
		}
		String line="";
		for(int i=0;i<map_movables.length;++i){
			for(int j=0;j<map_movables[0].length;++j){
				if(map_movables[i][j]=='N')
					line+=map_static[i][j];
				else
					line+=map_movables[i][j];
			}
			this.sendToClient(line);
			line="";
		}
		
	}

}
