package spacegame.communications;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import java.awt.Graphics;




import spacegame.client.*;
import spacegame.gui.*;
import spacegame.map.GameMap;
import spacegame.mapgui.*;

public class CommGUI extends JPanel implements Runnable {


    protected JButton button;
    private static CommGame game;
    private static Client client;
    private MouseListener m;
    public JFrame windowFrame;
    private HeadingDial headingDial = new HeadingDial();
    public Container contentPane;
    
    private JPanel windowPanel = new JPanel(new BorderLayout());
    private JPanel dataPanel = new JPanel(new BorderLayout());

    public CommGUI(CommGame game, Client c, MapComponent map){
        
    	windowFrame.setVisible(true);
    	windowFrame.setSize(new Dimension(1600,900));
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    	
    	this.game=game;
        client=c;

        windowFrame = new JFrame();
        this.client = c;



       
        headingDial.setRadius(100);
        dataPanel.add(headingDial, BorderLayout.CENTER);
        
       // windowPanel.setSize(map.size());
        windowPanel.add(map,BorderLayout.CENTER);           

        
        contentPane = windowFrame.getContentPane();
        contentPane.add(windowPanel, BorderLayout.CENTER);
        contentPane.add(dataPanel, BorderLayout.EAST);
        
        
//        contentPane.add(buttonPane, BorderLayout.PAGE_END);
        contentPane.addMouseListener(mouse);
        
        
        
        
     
        windowFrame.pack();
     
   
      
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
    		
    	//	windowFrame.repaint();
    	}
    	
    }
}
