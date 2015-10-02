package spacegame.groundcontrol;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	String iaddress = "10.11.1.110";
	int port = 8080;
	String name = "";
    boolean running = false;
    private groundControlGraphics guiThread;
	private boolean isRunning = false;
	private Client c;
	
	Client gcClient;
	groundControlProtocol gcProtocol;
	
	private boolean hasLink;
	private double velocity; // m/s
	private double heading;  //degrees (-180 to 180)
	private double posX = 0;     //m
	private double posY = 0;     //m 
	private double throttle;       //throttle percentage
	
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
		c.sendMessage("set ship Ship.1");
		c.sendMessage("subscribe all");
		run();
	}
	
	public void run() 
	{
		running = true;
	    guiThread = new groundControlGraphics(this,c);
	    guiThread.start();
	    guiThread.addKeyListener(arrowKeys);
	    gameLogic();
	}	
	
	private void gameLogic()
	{
		while(running)
		{
			try
			{
				Thread.sleep(50);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	KeyListener arrowKeys = new KeyListener()
	{
		@Override
		public void keyPressed(KeyEvent arg0) 
		{
			if(arg0.getKeyCode() == KeyEvent.VK_UP)
			{
				posY-=5;
				c.sendMessage("set posY " + posY);
			}
			if(arg0.getKeyCode() == KeyEvent.VK_DOWN)
			{
				posY+=5;
				c.sendMessage("set posY " + posY);
			}	
			if(arg0.getKeyCode() == KeyEvent.VK_LEFT)
			{
				posX-=5;
				c.sendMessage("set posX " + posX);
			}
			if(arg0.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				posX+=5;
				c.sendMessage("set posX " + posX);
			}	
		}

		@Override
		public void keyReleased(KeyEvent arg0) 
		{
		
		}

		@Override
		public void keyTyped(KeyEvent arg0) 
		{
			
			
		}
	};

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
		posX = Double.parseDouble(string);
		guiThread.updateRocketPosX(posX);
	}

	public void setRocketPosY(String string) 
	{
		posY = Double.parseDouble(string);
		guiThread.updateRocketPosY(posY);
	}

	public void setRocketHeading(String string) 
	{
		heading = Double.parseDouble(string);
		guiThread.updateRocketHeading(heading);
	}

	public void setRocketVelocity(String string) 
	{
		velocity = Double.parseDouble(string);
		guiThread.updateRocketVelocity(velocity);
		
	}
}

