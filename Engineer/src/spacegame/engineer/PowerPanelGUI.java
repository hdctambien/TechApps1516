package spacegame.engineer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSlider;

import spacegame.map.GameMap;

public class PowerPanelGUI extends JPanel
{
	GameMap map;
	
	int height, width;
	
	JSlider com, shi, fue, gun;
	
	public PowerPanelGUI(GameMap m)
	{
		map = m;
		
		com = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		com.setVisible(true);
		com.setOpaque(false);
		
		shi = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		shi.setVisible(true);
		shi.setOpaque(false);
		
		fue = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		fue.setVisible(true);
		fue.setOpaque(false);
		
		gun = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		gun.setVisible(true);
		gun.setOpaque(false);
		
		this.setLayout(new GridLayout(1, 4));
		this.add(com);
		this.add(shi);
		this.add(fue);
		this.add(gun);
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D G = (Graphics2D)g;
		
		width  = getWidth();
		height = getHeight();
		
		G.clearRect(0, 0, width, height);
		
		G.setColor(Color.GREEN);
		G.setBackground(Color.GREEN);
		G.fillRoundRect(5, 5, width-10, height-10, 25, 25);
		G.setColor(Color.WHITE);
		G.fillRoundRect(15, 15, width-30, height-30, 25, 25);
	}
}
