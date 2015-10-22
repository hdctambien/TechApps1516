package spacegame.engineer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import spacegame.client.*;
import spacegame.map.GameMap;

public class EngineerGame implements Runnable
{
	EngineerGUI gui;
	private boolean running = false;
	Client eClient;
	EngineerProtocol eProtocol;
	String iaddress = "10.11.1.110";
	int port = 8080;
	private String SHIP_NAME = "Ship.1";
	
	private Thread protocolThread;
	private Thread clientThread;
	
	private ProtocolAggregator aggregator;
	
	private GameMap map;
	
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
		try {
			eClient = new Client(iaddress,port);
			BasicProtocol basic = new BasicProtocol(eClient);
			SerialProtocol serial = new SerialProtocol(eClient);
			aggregator = new ProtocolAggregator(eClient);
			aggregator.addProtocol(basic);
			aggregator.addProtocol(serial);
			
			clientThread = new Thread(eClient);
			clientThread.start();
			protocolThread = new Thread(aggregator);
			protocolThread.start();
			
			//eClient.sendMessage("set name " + name);
			eClient.sendMessage("set job Engineer");
			eClient.sendMessage("set ship " + SHIP_NAME);
			
			eClient.sendMessage("get map");
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
			
			MapUpdateProtocol update = new MapUpdateProtocol(eClient, map, SHIP_NAME, serial);
			aggregator.addProtocol(update);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui = new EngineerGUI(this, this.eClient, aggregator, map);
		gui.start();
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
				
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerFuel", Integer.toString((int)pFt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerComms", Integer.toString((int)pCt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerGuns", Integer.toString((int)pGt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerShield", Integer.toString((int)pSt));break;
				
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
				
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerFuel", Integer.toString((int)pFt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerComms", Integer.toString((int)pCt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerGuns", Integer.toString((int)pGt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerShield", Integer.toString((int)pSt));break;
				
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
				
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerFuel", Integer.toString((int)pFt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerComms", Integer.toString((int)pCt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerGuns", Integer.toString((int)pGt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerShield", Integer.toString((int)pSt));break;
				
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
				
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerFuel", Integer.toString((int)pFt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerComms", Integer.toString((int)pCt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerGuns", Integer.toString((int)pGt));
				map.getEntityByName(SHIP_NAME).getComponent("Power").setVariable("powerShield", Integer.toString((int)pSt));break;
				
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
