package controller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import model.policy.Iinterpeter;
import sokoban_utils.SokoUtil;

public class CLIHandler extends Observable implements ClientHandler {
	private ArrayBlockingQueue<String> queue;
	private volatile boolean hasClient;
	private SokoUtil util;
	private boolean keepRunning;
	private Iinterpeter interpeterMove;
	
	public CLIHandler() {
		util=new SokoUtil();
		queue=new ArrayBlockingQueue<String>(200);
		this.hasClient=true;
	}
	@Override
	public void handleClient(InputStream in, OutputStream out) {
		try {
		this.keepRunning=true;
		this.hasClient=true;
		BufferedReader readFromClient=new BufferedReader(new InputStreamReader(in));
		BufferedReader readFromServer=new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writeToClient=new PrintWriter(out);
		PrintWriter writeToServer=new PrintWriter(System.out);
		welcomeClient(writeToClient);
		Thread t1=aSyncReadInputAndSent( readFromClient, writeToServer, "cya");
		Thread t3=aSyncSendToClient(writeToClient);
		t1.join();
		t3.join();
		readFromClient.close();
		//readFromServer.close();
		writeToClient.close();
		//writeToServer.close();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private Thread aSyncSendToClient(PrintWriter writeToClient) {
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				sendToClient(writeToClient);
			}
		});
		t.start();	
		return t;
	}
	private void readInputAndSend(BufferedReader in, PrintWriter out,String exitStr){
		String line;
		try {
			while(this.keepRunning){
			//	if(in.ready()){
				line=in.readLine();
				if(line.equals(exitStr)){
					addMessageToQueue("bye");
					setChanged();
					LinkedList<String> params=new LinkedList<String>();
					params.add("disconnected");
					notifyObservers(params);
					this.keepRunning=false;
					this.stop();
					continue;
				}

				if(util.isValidCommand(line)){
					LinkedList<String> params=new LinkedList<String>();
					if(util.isValidMoveCommand(util.extractCmd(line))){
						params=interpeterMove.interperate(line);
					}else{
						Scanner s=new Scanner(line);
						s.useDelimiter(" ");
						while(s.hasNext()){
							params.add(s.next());
						}
					}
					out.println(line);
					setChanged();
					notifyObservers(params);
					out.flush();
				}
				
					//end of while
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private Thread aSyncReadInputAndSent(BufferedReader in, PrintWriter out,String exitStr){
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				try{
				readInputAndSend(in, out, exitStr);
				}catch(Exception e){ e.printStackTrace();}
			}
		});
		t.start();
		
		return t;
		
	}

	public void addObserverToHandler(SokoServer s){
		this.addObserver(s);
	}
	@Override
	public void addMessageToQueue(String line) {
		try {
			this.queue.put(line);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void sendToClient(PrintWriter writeToClient){
		while(hasClient){
			try {
				String line=queue.take();
				if(line !=null){
					writeToClient.println(line);
					writeToClient.flush();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void stop(){
		this.hasClient=false;
	}
	public void start(){
		this.hasClient=true;
	}
	@Override
	public void setMoveInterpeter(Iinterpeter interpeter) {
		
		this.interpeterMove=interpeter;
	}

	public void welcomeClient(PrintWriter out){
		addMessageToQueue("   ___      ___     _  __     ___      ___      ___     _  _");
		addMessageToQueue("  / __|    / _ \\   | |/ /    / _ \\    | _ )    /   \\   | \\| |");
		addMessageToQueue("  \\__ \\   | (_) |  | ' <    | (_) |   | _ \\    | - |   | .` |");
		addMessageToQueue("  |___/    \\___/   |_|\\_\\    \\___/    |___/    |_|_|   |_|\\_|");
		addMessageToQueue("\n--------------------- Welcome To Sokoban ---------------------");
		addMessageToQueue("Developers: \n-Isan Rivkin\n-Daniel Hake");
		addMessageToQueue("-------------------    KeyBoard settings: --------------------");
		addMessageToQueue("                       load           levels/<level_name>");
		addMessageToQueue("                       save           myLevels/<level_name> ");
		addMessageToQueue("                       move           <left,right,up,down> or <a,d,w,s>");
		addMessageToQueue("                       display =      show current level");
		addMessageToQueue("                       reset   =      reset current level ");
		addMessageToQueue("                       'cya'   =      exit command");
		addMessageToQueue("                       'exit'  =      TERMINATE SERVER completley");
		addMessageToQueue("-------------------    IMPORTANT --------------------");
		addMessageToQueue("1) To load a level use the namespace levels/<level name> ");
		addMessageToQueue("2) A level will be always followed by the name: levelx.txt/xml/ser/obj.");
		addMessageToQueue("  Example: load levels/level1.txt");
		addMessageToQueue("3) 'cya' command will close the client, 'exit' will terminate the SERVER as well."); 
	}

}

