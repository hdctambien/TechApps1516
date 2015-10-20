package spacegame.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import spacegame.map.GameMap;

public class DisplayPanel extends JPanel
{
	private GameMap map;
	
	public DisplayPanel(GameMap map)
	{
		this.map = map;		
		this.setBackground(Color.RED);
		this.setVisible(true);
	}
	
/*	@Override
	public void paintComponent(Graphics g)
	{
		if(map.isLocked())
		{
			return;
		}
		else 
		{
			Graphics2D g2 = (Graphics2D) g;
			//DRAW ALL THE ENTITIES
		}
	}*/
}
