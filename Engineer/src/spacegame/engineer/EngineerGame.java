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
	
	private double pFR = .25;
	private double pCR = .25;
	private double pGR = .25;
	private double pSR = .25;
	
	private double pSt = 25;
	private double pGt = 25;
	private double pFt = 25;
	private double pCt = 25;
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
	
	public void powerDist(double pS, double pF, double pC, double pG, String change)
	{
		pSR = pS/100;
		pFR = pF/100;
		pCR = pC/100;
		pGR = pG/100;
		
		switch(change)
		{
			case "pS":
				if(pS + pG + pF + pC > 100)
				{
					power = 100 - pS;
					if(pFt > 0)
						pFt--;
					if(pCt > 0)
						pCt--;
					if(pGt > 0)
						pGt--;
				}
				pSt = pS;				

				gui.pComms.setValue((int)pCt);
				gui.pFuel.setValue((int)pFt);
				gui.pShield.setValue((int)pSt);
				gui.pGuns.setValue((int)pGt);
				
				eClient.sendMessage("set powerFuel " + Integer.toString((int)pFt));
				eClient.sendMessage("set powerComms " + Integer.toString((int)pCt));
				eClient.sendMessage("set powerGuns " + Integer.toString((int)pGt));
				eClient.sendMessage("set powerShield " + Integer.toString((int)pSt));break;
				
			case "pF": 
				if(pS + pG + pF + pC > 100)
				{					
					power = 100 - pF;
					if(pCt > 0)
						pCt--;
					if(pGt > 0)
						pGt--;
					if(pSt > 0)
						pSt--;
				}				
				pFt = pF;
				
				gui.pComms.setValue((int)pCt);
				gui.pFuel.setValue((int)pFt);
				gui.pShield.setValue((int)pSt);
				gui.pGuns.setValue((int)pGt);
				
				eClient.sendMessage("set powerShield " + Integer.toString((int)pSt));
				eClient.sendMessage("set powerComms " + Integer.toString((int)pCt));
				eClient.sendMessage("set powerGuns " + Integer.toString((int)pGt));
				eClient.sendMessage("set powerFuel " + Integer.toString((int)pFt));break;
				
			case "pC": 
				if(pS + pG + pF + pC > 100)
				{
					power = 100 - pC;
					if(pFt > 0)
						pFt--;
					if(pGt > 0)
						pGt--;
					if(pSt > 0)
						pSt--;
				}
				pCt = pC;
				
				gui.pComms.setValue((int)pCt);
				gui.pFuel.setValue((int)pFt);
				gui.pShield.setValue((int)pSt);
				gui.pGuns.setValue((int)pGt);
				
				eClient.sendMessage("set powerFuel " + Integer.toString((int)pFt));
				eClient.sendMessage("set powerShield " + Integer.toString((int)pSt));
				eClient.sendMessage("set powerGuns " + Integer.toString((int)pGt));
				eClient.sendMessage("set powerComms " + Integer.toString((int)pCt));break;
				
			case "pG": 
				if(pS + pG + pF + pC > 100)
				{					
					power = 100 - pG;
					if(pFt > 0)
						pFt--;
					if(pCt > 0)
						pCt--;
					if(pSt > 0)
						pSt--;
				}				
				pGt = pG;
				
				gui.pComms.setValue((int)pCt);
				gui.pFuel.setValue((int)pFt);
				gui.pShield.setValue((int)pSt);
				gui.pGuns.setValue((int)pGt);
				
				eClient.sendMessage("set powerFuel " + Integer.toString((int)pFt));
				eClient.sendMessage("set powerShield " + Integer.toString((int)pSt));
				eClient.sendMessage("set powerComms " + Integer.toString((int)pCt));
				eClient.sendMessage("set powerGuns " + Integer.toString((int)pGt));break;
				
			default:
				pCt = 25;
				pSt = 25;
				pFt = 25;
				pGt = 25;break;
		}
	}
	
	public void powerShield(int pShield)
	{
		powerDist(pShield, pFt, pCt, pGt, "pS");
	}
	
	public void powerFuel(int pFuel)
	{
		powerDist(pSt, pFuel, pCt, pGt, "pF");
	}
	
	public void powerComms(int pComms)
	{
		powerDist(pSt, pFt, pComms, pGt, "pC");
	}
	
	public void powerGuns(int pGuns)
	{
		powerDist(pSt, pFt, pCt, pGuns, "pG");
	}
	
	public void run()
	{
		running = true;
		while(running)
		{
						
		}		
	}
}
