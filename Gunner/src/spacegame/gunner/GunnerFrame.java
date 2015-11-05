package spacegame.gunner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import mapgui.MapComponent;
import mapgui.MapPanel;
import spacegame.client.Client;

public class GunnerFrame extends JFrame {//this is a frame
	
	Client c;
	


	public  GunnerFrame() {
        
    	MapComponent map = new MapComponent();
    	setVisible(true);
    	setExtendedState(JFrame.MAXIMIZED_BOTH); 
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	add(map,BorderLayout.CENTER);
    	addMouseListener(mouse);
    	setResizable(false);
    	
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("hi");
    }
    
    public static void main(String args[])
    {
    	GunnerFrame frame = new GunnerFrame();
    	frame.run();
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
       		//System.out.println("game running2");
       		if(mouseClick)
       		{
       			System.out.println("test");
               	repaint();
       		}
       		
       		repaint();
       	}
       	
      }        
       
}
