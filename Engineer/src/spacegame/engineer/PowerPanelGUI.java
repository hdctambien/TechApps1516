package spacegame.engineer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;

import spacegame.map.GameMap;

public class PowerPanelGUI extends JPanel
{
	GameMap map;
	
	int height, width;
	
	JSlider com, shi, fue, gun;
	
	JLabel cLab, sLab, fLab, gLab;
	
	JPanel labels, slides;
	
	Border border;

	public PowerPanelGUI(GameMap m)
	{
		map = m;
		
		border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		setBorder(border);
		
		//setLayout(new BorderLayout());
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		labels = new JPanel(new GridLayout(1, 4));
		labels.setOpaque(false);
		
		slides = new JPanel(new GridLayout(1, 4));
		slides.setOpaque(false);
		
		com = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		com.setVisible(true);
		com.setOpaque(false);
		
		cLab = new JLabel("Comms");
		cLab.setPreferredSize(new Dimension(50, 25));
		
		shi = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		shi.setVisible(true);
		shi.setOpaque(false);
		
		sLab = new JLabel("Shield");
		sLab.setPreferredSize(new Dimension(50, 25));
		
		fue = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		fue.setVisible(true);
		fue.setOpaque(false);
		
		fLab = new JLabel("Fuel");
		fLab.setPreferredSize(new Dimension(50, 25));
		
		gun = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		gun.setVisible(true);
		gun.setOpaque(false);
		
		gLab = new JLabel("Gun");
		gLab.setPreferredSize(new Dimension(50, 25));
		
		//labels.setLayout(new BoxLayout(labels, BoxLayout.X_AXIS));
		
		labels.add(cLab);
		labels.add(sLab);
		labels.add(fLab);
		labels.add(gLab);
		
		slides.add(com);
		slides.add(shi);
		slides.add(fue);
		slides.add(gun);
		
		//add(labels);
		//add(slides);
		
		add(new JLabel("Hello"));
		add(new JLabel("Hi"));
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
		G.fillRoundRect(10, 10, width-20, height-20, 25, 25);
		G.setColor(null);
	}
}
