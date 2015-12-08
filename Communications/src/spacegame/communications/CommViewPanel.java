package spacegame.communications;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class CommViewPanel extends JPanel{
	
	private int mouseX;
	private int mouseY;
	
	MouseEvent event;
	private MouseAdapter mouse = new MouseAdapter()
    {
		public void mouseMoved(MouseEvent e)
        {
        	event=e;
        	mouseX=e.getX();
        	mouseY=e.getY();
        	System.out.println("mouse moved");
        	System.out.println("mouse moved");

        }
		public void paintComponent(Graphics G) 
		{
			Graphics2D g = (Graphics2D) G;
			g.setColor(new Color(30,138,49,100));//transparency
			g.fillRect(getWidth()/2, getHeight()/2, mouseX, mouseY);
		}
		
    };
	
	
}
