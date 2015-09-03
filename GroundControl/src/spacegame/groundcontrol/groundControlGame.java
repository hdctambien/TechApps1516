package spacegame.groundcontrol;

import spacegame.client.*;


public class groundControlGame implements Runnable
{
    boolean running = false;
    private Thread guiThread;
	
	private boolean isRunning = false;
	public groundControlGame()
	{
		
	}
	
	public void run() 
	{
		running = true;
	    guiThread = new groundControlGraphics(this);
	    guiThread.start();
	    gameLogic();
	}	
	
	private void gameLogic()
	{
		while(running)
		{
			System.out.println("Game Thread");
		}
	}
}

