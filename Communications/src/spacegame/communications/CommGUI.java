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
import spacegame.gui.*;
import spacegame.map.GameMap;
import mapgui.*;



public class CommGUI extends JPanel implements Runnable {

	private double heading = 0;
	private String name = "Ship.1";//has to be Ship.1
    private static CommGame commGame;
    private static Client client;
   
    public JFrame windowFrame;
    private HeadingDial headingDial = new HeadingDial();
    public Container contentPane;
    
    private JPanel dataPanel = new JPanel(new BorderLayout());
    
    private JPanel windowPanel = new JPanel(new BorderLayout());
    
    
    GameMap map = new GameMap();
	private GameMap renderMap;
	private MapViewPanel mapPanel;
	//private MiniMapViewPanel mapPanel;
	
	private MiniMapViewPanel miniMap;
	ClientUpdater clientUpdater;

    
    public CommGUI(CommGame game, Client c, ClientUpdater clientUpdater){

    	
    	this.clientUpdater=clientUpdater;
    	map = clientUpdater.getMap();
    	renderMap = clientUpdater.getRenderMap();
    	this.commGame=game;
    	this.client=c;
    	//mapPanel = new mapgui.MiniMapViewPanel(renderMap, name);
    	mapPanel = new mapgui.MapViewPanel(renderMap, name);
	//	mapPanel.setSize(new Dimension(100,200));
	
		
    	miniMap = new mapgui.MiniMapViewPanel(renderMap, name);
		
		windowFrame = new JFrame();

		
		
		
	//	miniMap.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.X_AXIS));
    	//chat panel stuff
       
        headingDial.setRadius(100);
        dataPanel.add(headingDial, BorderLayout.CENTER);        
        dataPanel.add(mapPanel, BorderLayout.EAST);//, BorderLayout.EAST);
        dataPanel.add(Box.createRigidArea(new Dimension(36,0)));
        
    
		
        
      
        dataPanel.setPreferredSize(new Dimension(1600,250));
        windowPanel.add(miniMap,BorderLayout.CENTER);
        windowPanel.add(dataPanel, BorderLayout.SOUTH);
        
        
// 		  contentPane = windowFrame.getContentPane();      
//        contentPane.add(windowPanel, BorderLayout.CENTER);
//        contentPane.add(dataPanel, BorderLayout.SOUTH);
//        contentPane.addMouseListener(mouse);
        
        
        
        windowFrame.add(windowPanel);
        windowFrame.addMouseListener(mouse);
        windowFrame.setPreferredSize(new Dimension(1600,900));
    	windowFrame.setVisible(true);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        windowFrame.pack();
        windowFrame.setVisible(true);
}		
    private boolean mouseClick = false;
	private MouseAdapter mouse = new MouseAdapter()
    {
        public void mousePressed(MouseEvent e)
        {
        	System.out.println("click");
        	mouseClick = true;
        }
        public void mouseReleased(MouseEvent e)
        {
        	System.out.println("unclick");
        	mouseClick = false;
        }
    };
    
    public void run()
	{
		while(commGame.running)
		{
//			if(right)
//			{
//				heading -= -0.05;
//			}
//			if(left)
//			{
//				heading += 0.05;
//			}
//			
//			if(right || left)
//			{
//				System.out.println(heading);
//				clientUpdater.addUserAction(SHIP_NAME, "heading", Double.toString(heading), c);
//			}
//			
//			if(move)
//			{
//				if(Double.parseDouble(renderMap.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle")) < 0.1)
//					clientUpdater.addUserAction(SHIP_NAME, "throttle", Double.toString(100), c);
//			}
//			else
//			{
//				if(Double.parseDouble(renderMap.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle")) > 99.9)
//					clientUpdater.addUserAction(SHIP_NAME, "throttle", Double.toString(0), c);
//			}
//				
//			try
//			{
//				Thread.sleep(25);
//			}
//			catch(InterruptedException e)
//			{
//				e.printStackTrace();
//			}
			if(!(clientUpdater.isDirty() || clientUpdater.isRenderLocked()) && clientUpdater.isDrawDirty())
			{
				continue;
			}
			else
			{
				clientUpdater.setRenderLock(true); 
				
	//			mapPanel.setHeading(heading * 180 / Math.PI);
	//			mapPanel.setPosition((int) Double.parseDouble(renderMap.getEntityByName(SHIP_NAME).getComponent("Position").getVariable("posX")), 
	//					(int)Double.parseDouble(renderMap.getEntityByName(SHIP_NAME).getComponent("Position").getVariable("posY")));
				
		//		throttle.setValue((int) Double.parseDouble(renderMap.getEntityByName(name).getComponent("Fuel").getVariable("throttle")));
				headingDial.setHeading(heading);
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
