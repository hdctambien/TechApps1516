package spacegame.groundcontrol;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;








import java.util.Random;

import spacegame.client.*;
import spacegame.map.GameMap;

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
	public final String SHIP_NAME = "Ship.1";
    boolean running = false;
    private groundControlGraphics guiThread;
	private boolean isRunning = false;
	Client c;
	private static Thread clientThread;
	private static Thread protocolThread;
	private static Thread updaterThread;
	static ProtocolAggregator aggregator;
	private static GameMap map;
	private static ClientUpdater clientUpdater;
	
	public groundControlGame(String iAddress, int port, String name)
	{
		this.name = name;
		try {
			c = new Client(iaddress,port);
			BasicProtocol basic = new BasicProtocol(c);
			SerialProtocol serial = new SerialProtocol(c);
			aggregator = new ProtocolAggregator(c);
			aggregator.addProtocol(basic);
			aggregator.addProtocol(serial);
			
			clientThread = new Thread(c);
			clientThread.start();
			protocolThread = new Thread(aggregator);
			protocolThread.start();
			
			c.sendMessage("set name " + name);
			c.sendMessage("set job GroundControl");
			c.sendMessage("set ship " + SHIP_NAME);
			
			c.sendMessage("get map");
			System.out.println("Getting Map...");
			while(!serial.hasSerial()){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Building Map...");
			map = serial.getMapFromSerial();
			System.out.println("Map obtained!");
			
			clientUpdater = new ClientUpdater(map);
			updaterThread = new Thread(clientUpdater);
			updaterThread.start();
			MapUpdateProtocol update = new MapUpdateProtocol(c, clientUpdater, SHIP_NAME, serial);
			aggregator.addProtocol(update);
		} catch (IOException e) {
			e.printStackTrace();
		}
		run();
	}
	
	public void run() 
	{
		running = true;
	    guiThread = new groundControlGraphics(this,c,SHIP_NAME,aggregator,clientUpdater);
	    guiThread.start();
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
}

