package spacegame.engineer;

import java.io.IOException;

import spacegame.client.*;
import spacegame.map.GameMap;

public class EngineerGame implements Runnable
{
	EngineerGUI gui;
	private boolean running = false;
	Client eClient;
	EngineerProtocol eProtocol;
	String iaddress = "10.11.1.113";
	int port = 8080;
	private String SHIP_NAME = "Ship.1";
	
	private Thread protocolThread;
	private Thread clientThread;
	
	private ProtocolAggregator aggregator;
	
	private GameMap map;
	private ClientUpdater cUpdater;
	
	private String bigPower;
	
	private double maxPower;
	private double pSt = 25;
	private double pGt = 25;
	private double pFt = 25;
	private double pCt = 25;
	
	public EngineerGame()
	{	
		maxPower = 100;
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
			
			cUpdater = new ClientUpdater(map);
			MapUpdateProtocol update = new MapUpdateProtocol(eClient, cUpdater, SHIP_NAME, serial);
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
	
	public void powerDist(double pS, double pF, double pC, double pG, String change, double mP)
	{
		maxPower = mP;
		switch(change)
		{
			case "pS":
				if(pS + pG + pF + pC > maxPower)
				{
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
				
				cUpdater.addUserAction(SHIP_NAME, "powerFuel", Integer.toString((int)pFt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerComms", Integer.toString((int)pCt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerShield", Integer.toString((int)pSt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerGuns", Integer.toString((int)pGt), eClient);break;
				
			case "pF": 
				if(pS + pG + pF + pC > maxPower)
				{					
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
				
				cUpdater.addUserAction(SHIP_NAME, "powerFuel", Integer.toString((int)pFt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerComms", Integer.toString((int)pCt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerShield", Integer.toString((int)pSt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerGuns", Integer.toString((int)pGt), eClient);break;
				
			case "pC": 
				if(pS + pG + pF + pC > maxPower)
				{
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
				
				cUpdater.addUserAction(SHIP_NAME, "powerFuel", Integer.toString((int)pFt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerComms", Integer.toString((int)pCt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerShield", Integer.toString((int)pSt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerGuns", Integer.toString((int)pGt), eClient);break;
				
			case "pG": 
				if(pS + pG + pF + pC > maxPower)
				{					
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
				
				cUpdater.addUserAction(SHIP_NAME, "powerFuel", Integer.toString((int)pFt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerComms", Integer.toString((int)pCt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerShield", Integer.toString((int)pSt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerGuns", Integer.toString((int)pGt), eClient);break;
				
			case "mP":
				if(pS + pG + pF + pC > maxPower)
				{	
					int dif = (int)(maxPower - (pS + pG + pF + pC));
					int temp = (int)Math.max(Math.max(pS, pG), Math.max(pC, pF));
					if(temp == pS)
						pS -= dif;
					else
						if(temp == pF)
							pF -= dif;
						else
							if(temp == pG)
								pG -= dif;
							else
								if(temp == pC)
									pC -= dif;
				}				
				
				gui.pComms.setValue((int)pC);
				gui.pFuel.setValue((int)pF);
				gui.pShield.setValue((int)pS);
				gui.pGuns.setValue((int)pG);
				
				cUpdater.addUserAction(SHIP_NAME, "powerFuel", Integer.toString((int)pFt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerComms", Integer.toString((int)pCt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerShield", Integer.toString((int)pSt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerGuns", Integer.toString((int)pGt), eClient);break;
				
			default:
				pCt = 25;
				pSt = 25;
				pFt = 25;
				pGt = 25;break;
		}
	}
	
	public void power(int mP)
	{
		System.out.println(mP);
		maxPower = 200 - mP;
		cUpdater.addUserAction(SHIP_NAME, "power", Integer.toString((int)maxPower), eClient);
	}
	
	public void powerShield(int pShield)
	{
		powerDist(pShield, pFt, pCt, pGt, "pS", maxPower);
	}
	
	public void powerFuel(int pFuel)
	{
		powerDist(pSt, pFuel, pCt, pGt, "pF", maxPower);
	}
	
	public void powerComms(int pComms)
	{
		powerDist(pSt, pFt, pComms, pGt, "pC", maxPower);
	}
	
	public void powerGuns(int pGuns)
	{
		powerDist(pSt, pFt, pCt, pGuns, "pG", maxPower);
	}
	
	public void run()
	{
		running = true;
		while(running)
		{
						
		}		
	}
}
