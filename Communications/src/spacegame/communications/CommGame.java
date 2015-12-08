package spacegame.communications;
import spacegame.client.*;
/**
 * Created by Avery on 9/28/2010.;
 */
import spacegame.map.GameMap;

import java.io.IOException;

import mapgui.MapComponent;
import mapgui.MapPanel;

import java.awt.Graphics;
import spacegame.client.*;


public class CommGame implements Runnable
{
    Client client;
    String iaddress = "10.11.1.92";
   // String iaddress = "192.168.1.89";
    int port = 8080;

    private Thread protocolThread;
    private Thread clientThread;
    private GameMap map = new GameMap();//import from client
    boolean running = false;
    CommGUI gui;
    private Client c;
    String name;
    static ProtocolAggregator aggregator;
    private static ClientUpdater clientUpdater;
    private static Thread updaterThread;


    public CommGame(String address, int p, String name)
    {
    	this.name=name;
        iaddress=address;
        port=p;
        try {
			c = new Client(iaddress, port);
			
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
			c.sendMessage("set job Communications");
			c.sendMessage("set ship " + name);
			
			c.sendMessage("get map");
			System.out.println("Getting Map...");
			while(!serial.hasSerial()){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			System.out.println("Building Map...");
			map = serial.getMapFromSerial();
			System.out.println("Map obtained!");
			
			clientUpdater = new ClientUpdater(map);
			updaterThread = new Thread(clientUpdater);
			updaterThread.start();
			MapUpdateProtocol update = new MapUpdateProtocol(c, clientUpdater, name, serial);
			aggregator.addProtocol(update);
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
        run();
        
        
        

    }
    public void run()
    {
        running=true;
        
        gui = new CommGUI(this, this.client, clientUpdater);
        Thread guiThread = new Thread(gui);
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
