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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import spacegame.map.GameMap;

public class PowerPanelGUI extends JPanel
{
	GameMap map;
	
	EngineerGame game;
	
	int height, width, labelW;
	
	int pF, pC, pS, pG;
	
	JSlider com, shi, fue, gun;
	
	JLabel cLab, sLab, fLab, gLab;
	
	JPanel labels, slides, cont;
	
	Border border;

	public PowerPanelGUI(GameMap m, EngineerGame ga)
	{
		map = m;
		
		game = ga;
		
		labelW = (int) Math.floor(getWidth()/4);
		
		cont = new JPanel();
		cont.setLayout(new BorderLayout());
		
		border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		setBorder(border);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		labels = new JPanel(new GridLayout(1, 4));
		labels.setOpaque(false);
		
		slides = new JPanel(new GridLayout(1, 4));
		slides.setOpaque(false);
		
		com = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		com.setVisible(true);
		com.setOpaque(false);
		com.setMajorTickSpacing(10);
		com.setMinorTickSpacing(5);
		com.setPaintTicks(true);
		com.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pC = ((JSlider) e.getSource()).getValue();
					game.powerComms(pC);
				}					
			});
		
		cLab = new JLabel("Comms");
		cLab.setPreferredSize(new Dimension(labelW, 25));
		cLab.setHorizontalAlignment(JLabel.CENTER);
		
		shi = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		shi.setVisible(true);
		shi.setOpaque(false);
		shi.setMajorTickSpacing(10);
		shi.setMinorTickSpacing(5);
		shi.setPaintTicks(true);
		shi.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pS = ((JSlider) e.getSource()).getValue();
					game.powerShield(pS);
				}					
			});
		
		sLab = new JLabel("Shield");
		sLab.setPreferredSize(new Dimension(labelW, 25));
		sLab.setHorizontalAlignment(JLabel.CENTER);
		
		fue = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		fue.setVisible(true);
		fue.setOpaque(false);
		fue.setMajorTickSpacing(10);
		fue.setMinorTickSpacing(5);
		fue.setPaintTicks(true);
		fue.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pF = ((JSlider) e.getSource()).getValue();
					game.powerFuel(pF);
				}					
			});
		
		fLab = new JLabel("Fuel");
		fLab.setPreferredSize(new Dimension(labelW, 25));
		fLab.setHorizontalAlignment(JLabel.CENTER);
		
		gun = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		gun.setVisible(true);
		gun.setOpaque(false);
		gun.setMajorTickSpacing(10);
		gun.setMinorTickSpacing(5);
		gun.setPaintTicks(true);
		gun.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pG = ((JSlider) e.getSource()).getValue();
					game.powerGuns(pG);
				}					
			});
		
		gLab = new JLabel("Gun");
		gLab.setPreferredSize(new Dimension(labelW, 25));
		gLab.setHorizontalAlignment(JLabel.CENTER);
		
		labels.setLayout(new GridLayout(1, 4));
		labels.setBorder(border);
		
		labels.add(cLab);
		labels.add(sLab);
		labels.add(fLab);
		labels.add(gLab);
		
		slides.add(com);
		slides.add(shi);
		slides.add(fue);
		slides.add(gun);
		
		cont.add(labels, BorderLayout.NORTH);
		cont.add(slides, BorderLayout.SOUTH);
		add(cont);
	}
	
	public void disableEnable(boolean set)
	{
		com.setEnabled(set);
		fue.setEnabled(set);
		shi.setEnabled(set);
		gun.setEnabled(set);
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
