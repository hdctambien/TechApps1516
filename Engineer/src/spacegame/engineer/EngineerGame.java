package spacegame.engineer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import spacegame.client.*;

public class EngineerGame implements Runnable
{
	EngineerGUI gui;
	private boolean running = false;
	Client eClient;
	EngineerProtocol eProtocol;
	String iaddress = "10.11.1.110";
	//String iaddress = "192.168.1.89";
	int port = 8080;
	JButton exit;
	JButton get;
	
	private Thread protocolThread;
	private Thread clientThread;
		
	public EngineerGame()
	{	
		try 
		{
			eClient = new Client(iaddress,port);
			clientThread = new Thread(eClient);
			clientThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui = new EngineerGUI(this, this.eClient);
		gui.start();
		eProtocol = new EngineerProtocol(eClient, this, gui);
		protocolThread = new Thread(eProtocol);
		protocolThread.start();
		//run();
	}
	
	public void Throttle(int throt)
	{
		eClient.sendMessage("set throttle "+Integer.toString(throt));
	}
	
	public void run()
	{
		running = true;
		while(running)
		{
						
		}		
	}
}
