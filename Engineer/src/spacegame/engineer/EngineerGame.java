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
	
	private int pSt = 25;
	private int pGt = 25;
	private int pFt = 25;
	private int pCt = 25;
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
				System.out.println(pFR);
				pCR = pC/100;
				pGR = pG/100;
				power = 100 - pS;
				pSt = pS;
				pFt = (int)Math.floor(pFR * power);
				pCt = (int)Math.floor(pCR * power);
				pGt = (int)Math.floor(pGR * power);
				eClient.sendMessage("set powerShield " + Integer.toString(pSt));
				eClient.sendMessage("set powerFuel " + Integer.toString(pFt));
				eClient.sendMessage("set powerComms " + Integer.toString(pCt));
				eClient.sendMessage("set powerGuns " + Integer.toString(pGt));break;
				
			case "pF": 
				pSR = pS/100;
				pCR = pC/100;
				pGR = pG/100;
				power = 100 - pF;
				pSt = (int)Math.floor(pSR * power);
				pFt = pF;
				pCt = (int)Math.floor(pCR * power);
				pGt = (int)Math.floor(pGR * power);
				eClient.sendMessage("set powerFuel " + Integer.toString(pFt));
				eClient.sendMessage("set powerShield " + Integer.toString(pSt));
				eClient.sendMessage("set powerComms " + Integer.toString(pCt));
				eClient.sendMessage("set powerGuns " + Integer.toString(pGt));break;
				
			case "pC": 
				pFR = pF/100;
				pSR = pS/100;
				pGR = pG/100;
				power = 100 - pC;
				pSt = (int)Math.floor(pSR * power);
				pFt = (int)Math.floor(pFR * power);
				pCt = pC;
				pGt = (int)Math.floor(pGR * power);
				eClient.sendMessage("set powerComms " + Integer.toString(pCt));
				eClient.sendMessage("set powerFuel " + Integer.toString(pFt));
				eClient.sendMessage("set powerShield " + Integer.toString(pSt));
				eClient.sendMessage("set powerGuns " + Integer.toString(pGt));break;
				
			case "pG": 
				pFR = pF/100;
				pCR = pC/100;
				pSR = pS/100;
				power = 100 - pG;
				pSt = (int)Math.floor(pSR * power);
				pFt = (int)Math.floor(pFR * power);
				pCt = (int)Math.floor(pCR * power);
				pGt = pG;
				eClient.sendMessage("set powerGuns " + Integer.toString(pGt));
				eClient.sendMessage("set powerFuel " + Integer.toString(pFt));
				eClient.sendMessage("set powerShield " + Integer.toString(pSt));
				eClient.sendMessage("set powerComms " + Integer.toString(pCt));break;
				
			default:
				pCt = 25;
				pSt = 25;
				pFt = 25;
				pGt = 25;break;
		}
		System.out.println(pCt);
		System.out.println(pFt);
		System.out.println(pSt);
		System.out.println(pGt);
		gui.pComms.setValue(pCt);
		gui.pFuel.setValue(pFt);
		gui.pShield.setValue(pSt);
		gui.pGuns.setValue(pGt);
	}
	
	public void powerShield(int pShield)
	{
		//eClient.sendMessage("set powerShield " + Integer.toString(pShield));
		powerDist(pShield, pFt, pCt, pGt, "pS");
	}
	
	public void powerFuel(int pFuel)
	{
		//eClient.sendMessage("set powerFuel " + Integer.toString(pFuel));
		powerDist(pSt, pFuel, pCt, pGt, "pF");
	}
	
	public void powerComms(int pComms)
	{
		//eClient.sendMessage("set powerComms " + Integer.toString(pComms));
		powerDist(pSt, pFt, pComms, pGt, "pC");
	}
	
	public void powerGuns(int pGuns)
	{
		//eClient.sendMessage("set powerGuns " + Integer.toString(pGuns));
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
