package spacegame.groundcontrol;

import java.io.IOException;


import spacegame.client.*;


public class groundControlGame implements Runnable
{
	String iaddress = "10.11.1.110";
	int port = 8080;
    boolean running = false;
    private groundControlGraphics guiThread;
	private boolean isRunning = false;
	private Client c;
	
	Client gcClient;
	groundControlProtocol gcProtocol;
	
	public groundControlGame()
	{
		try {
			gcClient = new Client(iaddress,port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gcProtocol = new groundControlProtocol(gcClient, this);
		c = gcProtocol.getClient();
	}
	
	public void run() 
	{
		running = true;
	    guiThread = new groundControlGraphics(this,c);
	    guiThread.start();
	    gameLogic();
	}	
	
	private void gameLogic()
	{
		while(running)
		{
			try
			{
				Thread.sleep(10);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}

