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
	int port = 8080;
	
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
	}
	
	public void Throttle(int throt)
	{
		eClient.sendMessage("set throttle " + Integer.toString(throt));
	}
	
	public void powerDist(int power, int pS, int pF, int pC, int pG)
	{
		
	}
	
	public void powerShield(int pShield)
	{
		eClient.sendMessage("set powerShield " + Integer.toString(pShield));
	}
	
	public void powerFuel(int pFuel)
	{
		eClient.sendMessage("set powerFuel " + Integer.toString(pFuel));
	}
	
	public void powerComms(int pComms)
	{
		eClient.sendMessage("set powerComms " + Integer.toString(pComms));
	}
	
	public void powerGuns(int pGuns)
	{
		eClient.sendMessage("set powerGuns " + Integer.toString(pGuns));
	}
	
	public void run()
	{
		running = true;
		while(running)
		{
						
		}		
	}
}
