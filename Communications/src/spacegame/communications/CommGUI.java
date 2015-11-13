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
	

    
    public CommGUI(CommGame game, Client c, ClientUpdater clientUpdater){

    	
    	
    	map = clientUpdater.getMap();
    	renderMap = clientUpdater.getRenderMap();
    	this.commGame=game;
    	this.client=c;
    	//mapPanel = new mapgui.MiniMapViewPanel(renderMap, name);
    	mapPanel = new mapgui.MapViewPanel(renderMap, name);
	//	mapPanel.setSize(new Dimension(100,200));
		miniMap = new mapgui.MiniMapViewPanel(renderMap, name);
	//	miniMap.setPreferredSize(new Dimension(10,10));
		
		
		windowFrame = new JFrame();

		
		
		
	//	miniMap.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.X_AXIS));
    	//chat panel stuff
       
        headingDial.setRadius(100);
        dataPanel.add(headingDial, BorderLayout.CENTER);        
        dataPanel.add(miniMap, BorderLayout.EAST);//, BorderLayout.EAST);
        dataPanel.add(Box.createRigidArea(new Dimension(50,0)));
        
        windowPanel.add(mapPanel,BorderLayout.CENTER);
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
    //	headingDial.setHeading(map.getHeading());
    	System.out.println("game running1");
    	for(double i=0;true;)
    	{
    		System.out.println("game running2");
    		if(mouseClick)
    		{
    		//	i+=.0001;
    		//	headingDial.setRadius((int)i+1);
    			System.out.println("test");
            	headingDial.setHeading(i);
            	windowFrame.repaint();
    		}
    		
    	
    	}
    	
    }
}
