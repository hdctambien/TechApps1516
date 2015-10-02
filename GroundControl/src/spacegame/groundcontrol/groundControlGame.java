package spacegame.groundcontrol;

import java.io.IOException;








import java.util.Random;

import spacegame.client.*;

/**
 * Starts up client and runs the game loop, initializes game logic and graphics threads.
 * 
 * @author Justin Pierre
 */
public class groundControlGame implements Runnable
{
	String iaddress = "10.11.1.112";
	int port = 8080;
	String name = "";
    boolean running = false;
    private groundControlGraphics guiThread;
	private boolean isRunning = false;
	private Client c;
	
	Client gcClient;
	groundControlProtocol gcProtocol;
	
	private boolean hasLink;
	private double rocketVelocity; // m/s
	private double rocketHeading;  //degrees (-180 to 180)
	private double rocketPosX;     //m
	private double rocketPosY;     //m 
	private double throttle;       //throttle percentage
	
	private Random Rand = new Random();
	
	public groundControlGame(String iAddress, int port, String name)
	{
		this.name = name;
		try {
			gcClient = new Client(iaddress,port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gcProtocol = new groundControlProtocol(gcClient, this);
		c = gcProtocol.getClient();
		c.sendMessage("set name " + name);
		c.sendMessage("set job GroundControl");
		c.sendMessage("subscribe all");
		run();
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

	public void setThrottle(String string) 
	{
		throttle = Double.parseDouble(string);
		guiThread.updateThrottle(throttle);
	}

	public void setHasLink(String string) 
	{
		hasLink = Boolean.parseBoolean(string);	
		guiThread.updateHasLink(hasLink);
	}

	public void setRocketPosX(String string) 
	{
		rocketPosX = Double.parseDouble(string);
		guiThread.updateRocketPosX(rocketPosX);
	}

	public void setRocketPosY(String string) 
	{
		rocketPosY = Double.parseDouble(string);
		guiThread.updateRocketPosY(rocketPosY);
	}

	public void setRocketHeading(String string) 
	{
		rocketHeading = Double.parseDouble(string);
		guiThread.updateRocketHeading(rocketHeading);
	}

	public void setRocketVelocity(String string) 
	{
		rocketVelocity = Double.parseDouble(string);
		guiThread.updateRocketVelocity(rocketVelocity);
		
	}
}

