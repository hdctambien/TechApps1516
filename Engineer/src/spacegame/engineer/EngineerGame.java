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
	
	private double pFR, pCR, pGR, pSR;
	
	private int pSt;
	private int pGt;
	private int pFt;
	private int pCt;
	private double power;
		
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
	
	public void powerDist(int pS, int pF, int pC, int pG, String change)
	{
		switch(change)
		{
			case "pS": 
				pFR = pF/100;
				pCR = pC/100;
				pGR = pG/100;
				power = 100 - pS;
				pF = (int)Math.floor(pFR * power);
				pC = (int)Math.floor(pCR * power);
				pG = (int)Math.floor(pGR * power);
				eClient.sendMessage("set powerShield " + Integer.toString(pS));
				eClient.sendMessage("set powerFuel " + Integer.toString(pF));
				eClient.sendMessage("set powerComms " + Integer.toString(pC));
				eClient.sendMessage("set powerGuns " + Integer.toString(pG));break;
				
			case "pF": 
				pSR = pS/100;
				pCR = pC/100;
				pGR = pG/100;
				power = 100 - pF;
				pS = (int)Math.floor(pSR * power);
				pC = (int)Math.floor(pCR * power);
				pG = (int)Math.floor(pGR * power);
				eClient.sendMessage("set powerFuel " + Integer.toString(pF));
				eClient.sendMessage("set powerShield " + Integer.toString(pS));
				eClient.sendMessage("set powerComms " + Integer.toString(pC));
				eClient.sendMessage("set powerGuns " + Integer.toString(pG));break;
				
			case "pC": 
				pFR = pF/100;
				pSR = pS/100;
				pGR = pG/100;
				power = 100 - pC;
				pS = (int)Math.floor(pSR * power);
				pG = (int)Math.floor(pGR * power);
				pF = (int)Math.floor(pFR * power);
				eClient.sendMessage("set powerComms " + Integer.toString(pC));
				eClient.sendMessage("set powerFuel " + Integer.toString(pF));
				eClient.sendMessage("set powerShield " + Integer.toString(pS));
				eClient.sendMessage("set powerGuns " + Integer.toString(pG));break;
				
			case "pG": 
				pFR = pF/100;
				pCR = pC/100;
				pSR = pS/100;
				power = 100 - pG;
				pS = (int)Math.floor(pSR * power);
				pG = (int)Math.floor(pGR * power);
				pF = (int)Math.floor(pFR * power);
				eClient.sendMessage("set powerComms " + Integer.toString(pG));
				eClient.sendMessage("set powerFuel " + Integer.toString(pF));
				eClient.sendMessage("set powerShield " + Integer.toString(pS));
				eClient.sendMessage("set powerGuns " + Integer.toString(pC));break;
		}
	}
	
	public void powerShield(int pShield)
	{
		//eClient.sendMessage("set powerShield " + Integer.toString(pShield));
		powerDist(pShield, pF, pC, pG, "pS");
	}
	
	public void powerFuel(int pFuel)
	{
		//eClient.sendMessage("set powerFuel " + Integer.toString(pFuel));
		powerDist(pS, pFuel, pC, pG, "pF");
	}
	
	public void powerComms(int pComms)
	{
		//eClient.sendMessage("set powerComms " + Integer.toString(pComms));
		powerDist(pS, pF, pComms, pG, "pC");
	}
	
	public void powerGuns(int pGuns)
	{
		//eClient.sendMessage("set powerGuns " + Integer.toString(pGuns));
		powerDist(pS, pF, pC, pGuns, "pG");
	}
	
	public void run()
	{
		running = true;
		while(running)
		{
						
		}		
	}
}
