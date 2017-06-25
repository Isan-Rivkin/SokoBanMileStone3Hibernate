package controller.general_controller;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import controller.commands.Command;

public class GeneralController 
{
	
	private ArrayBlockingQueue<Command> queue;
	private boolean keepRunning;
	private ArrayList<Thread> threads_history;
	public GeneralController() 
	{
		this.queue=new ArrayBlockingQueue<Command>(50);
		this.threads_history=new ArrayList<Thread>();
		keepRunning=true;
	}
	
	public void start()
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (keepRunning)
				{
					try
					{
						Command cmd = queue.take();
						if (cmd != null) 
						{
							cmd.execute();
						}
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}

				}
			}
		});
		t.start();
		threads_history.add(t);

	}
	public void stop(){
		this.keepRunning=false;
	}
	public void insertCommand(Command cmd){
		try {
			queue.put(cmd);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
