package spacegame.communications;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import mapgui.MapComponent;
import mapgui.MiniMapViewPanel;
import spacegame.client.*;
import spacegame.client.chat.ChatPanel;
import spacegame.client.chat.ChatProtocol;
import spacegame.gui.*;
import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.GameMap;
import spacegame.map.components.PositionComponent;
import mapgui.*;



public class CommGUI extends JPanel implements Runnable {

	private double heading = 0;
	private String name = "Ship.1";//has to be Ship.1
    private static CommGame commGame;
    private static Client client;
	private ChatPanel chatPanel;

    public JFrame windowFrame;
    private HeadingDial headingDial = new HeadingDial();
    public Container contentPane;
    
    private JPanel dataPanel = new JPanel(new BorderLayout());
    
    private JPanel windowPanel = new JPanel(new BorderLayout());
    
    
    private GameMap map = new GameMap();
	private GameMap renderMap;
	//private MapViewPanel mapPanel;
	//private MiniMapViewPanel mapPanel;
	private ChatProtocol chatProtocol;
	private static ProtocolAggregator aggregator;
	Entity ship;

	
	//private MiniMapViewPanel miniMap;
	private CommViewPanel commPanel;
	ClientUpdater clientUpdater;

    
    public CommGUI(CommGame game, Client c, ClientUpdater clientUpdater,ProtocolAggregator aggregator){
    	ship = map.getEntityByName(name);
    	
		chatPanel = new ChatPanel(300,225);

    	this.clientUpdater=clientUpdater;
    	map = clientUpdater.getMap();
    	renderMap = clientUpdater.getRenderMap();
    	this.commGame=game;
    	this.client=c;
    	
    //	mapPanel = new mapgui.MapViewPanel(renderMap, name);

		
    //	miniMap = new mapgui.MiniMapViewPanel(renderMap, name);
		commPanel = new CommViewPanel(map, name);
		windowFrame = new JFrame();

		try
		{
			this.aggregator = aggregator;
			chatProtocol = new ChatProtocol(c, chatPanel);
			chatPanel.addChatListener(chatProtocol);
			aggregator.addProtocol(chatProtocol);
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.X_AXIS));
		dataPanel.add(chatPanel);
		
		//chat panel stuff
       
		
        headingDial.setRadius(100);
        dataPanel.add(headingDial, BorderLayout.CENTER);        
   //   dataPanel.add(mapPanel, BorderLayout.EAST);//, BorderLayout.EAST);
        dataPanel.add(Box.createRigidArea(new Dimension(36,0)));

        dataPanel.setPreferredSize(new Dimension(1600,250));
        windowPanel.add(commPanel, BorderLayout.CENTER);
     //   windowPanel.add(miniMap,BorderLayout.CENTER);
       
        windowPanel.add(dataPanel, BorderLayout.SOUTH);
        
        windowFrame.add(windowPanel);
        windowFrame.setPreferredSize(new Dimension(1600,900));
    	windowFrame.setVisible(true);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        windowFrame.pack();
        windowFrame.setVisible(true);
}		
    
    public void run()
	{
		while(commGame.running)
		{
			
			heading=map.getEntityByName(name).getComponent("Heading").getDouble("heading");
			
			
			try
			{
				Thread.sleep(25);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			if(!(clientUpdater.isDirty() || clientUpdater.isRenderLocked()) && clientUpdater.isDrawDirty())
			{
				continue;
			}
			else
			{
				clientUpdater.setRenderLock(true); 
			
	//			mapPanel.setHeading(heading * 180 / Math.PI);
	//			mapPanel.setPosition((int) Double.parseDouble(renderMap.getEntityByName(SHIP_NAME).getComponent("Position").getVariable("posX")), 
	//			(int)Double.parseDouble(renderMap.getEntityByName(SHIP_NAME).getComponent("Position").getVariable("posY")));
	//			throttle.setValue((int) Double.parseDouble(renderMap.getEntityByName(name).getComponent("Fuel").getVariable("throttle")));
		
		//		headingDial.setHeading(Integer.parseInt(renderMap.getEntityByName(name).getComponent("Heading").getVariable("Heading")));

				
//				pFuel.setValue(Integer.parseInt(renderMap.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerFuel")));
//				pGuns.setValue(Integer.parseInt(renderMap.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerGuns")));
//				pShield.setValue(Integer.parseInt(renderMap.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerShield")));
//				pComms.setValue(Integer.parseInt(renderMap.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerComms")));
				
				windowPanel.repaint();
				clientUpdater.setRenderLock(false);
				clientUpdater.setDrawDirty(false);
			}
		} 
	}
}
