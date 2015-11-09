package spacegame.communications;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import mapgui.MapComponent;

import java.awt.Graphics;




import spacegame.client.*;
import spacegame.gui.*;
import spacegame.map.GameMap;
import mapgui.*;


public class CommGUI extends JPanel implements Runnable {

	
	private String name = "Communications";
    private static CommGame commGame;
    private static Client client;
   
    public JFrame windowFrame;
    private HeadingDial headingDial = new HeadingDial();
    public Container contentPane;
    
    private JPanel windowPanel = new JPanel(new BorderLayout());
    private JPanel dataPanel = new JPanel(new BorderLayout());
    
    GameMap map = new GameMap();
	private GameMap renderMap;
	private MapViewPanel mapPanel;
	

    
    public CommGUI(CommGame game, Client c, ClientUpdater clientUpdater){
        	
    	map = clientUpdater.getMap();
    	renderMap = clientUpdater.getRenderMap();
    	this.commGame=game;
    	this.client=c;
		mapPanel = new mapgui.MapViewPanel(renderMap, name);
		windowFrame = new JFrame();
    	
    	windowFrame.setVisible(true);
    	windowFrame.setSize(new Dimension(1600,900));
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    	//chat panel stuff
       
        headingDial.setRadius(100);
        dataPanel.add(headingDial, BorderLayout.CENTER);
        
        contentPane = windowFrame.getContentPane();
        
        contentPane.add(mapPanel,BorderLayout.CENTER);
        contentPane.add(windowPanel, BorderLayout.CENTER);
        contentPane.add(dataPanel, BorderLayout.SOUTH);
        contentPane.addMouseListener(mouse);
        
        
       // windowFrame.pack();
      
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
    //	headingDial.setHeading(map.getHeading());
    	System.out.println("game running1");
    	for(double i=0;true;)
    	{
    		System.out.println("game running2");
    		if(mouseClick)
    		{
    			i+=.0001;
    		//	headingDial.setRadius((int)i+1);
    			System.out.println("test");
            	headingDial.setHeading(i);
            	windowFrame.repaint();
    		}
    		
    	
    	}
    	
    }
}
