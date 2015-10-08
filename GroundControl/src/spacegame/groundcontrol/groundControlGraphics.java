package spacegame.groundcontrol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import spacegame.client.Client;
import spacegame.map.GameMap;
import spacegame.panel.DisplayPanel;

/**
 * Runs on the Graphics thread started by groundControlGame, opens up a JFrame and displays some basic info for now.
 * 
 * @author Justin Pierre
 */

class groundControlGraphics extends Thread 
{
	JFrame windowFrame;    
    public JPanel windowPanel;
    private JPanel dataPanel;
	private groundControlGame gcGame;
	private GridBagLayout gridBag;
	Client c;
	private GameMap map;
	
	private JSlider throttle;
	private JSlider heading;
	private DisplayPanel displayPanel;
	
	private JSlider pFuel;
	private JSlider pComms;
	private JSlider pShield;
	private JSlider pGuns;
	
	public groundControlGraphics(groundControlGame groundControlGame, final Client c, GameMap map) 
	{		
		this.map = map;
		gcGame = groundControlGame;
		windowFrame = new JFrame();
		windowPanel = new JPanel(new BorderLayout());
		windowPanel.setVisible(true);
		dataPanel = new JPanel(new GridLayout());
		this.c = c;
		
		
		pFuel = new JSlider(JSlider.VERTICAL,0,100,25);
		pFuel.setMinorTickSpacing(5);
		pFuel.setMajorTickSpacing(10);
		pFuel.setEnabled(false);
		pComms = new JSlider(JSlider.VERTICAL,0,100,25);
		pComms.setMinorTickSpacing(5);
		pComms.setMajorTickSpacing(10);
		pComms.setEnabled(false);
		pShield = new JSlider(JSlider.VERTICAL,0,100,25);
		pShield.setMinorTickSpacing(5);
		pShield.setMajorTickSpacing(10);
		pShield.setEnabled(false);
		pGuns = new JSlider(JSlider.VERTICAL,0,100,25);
		pGuns.setMinorTickSpacing(5);
		pGuns.setMajorTickSpacing(10);
		pGuns.setEnabled(false);
		
		GridBagConstraints pFuelConst = new GridBagConstraints();
		pFuelConst.fill = pFuelConst.BOTH;
		pFuelConst.gridx = 6;
		pFuelConst.gridy = 0;
		pFuelConst.gridheight = 5;
		pFuelConst.gridwidth = 1;
		GridBagConstraints pCommsConst = new GridBagConstraints();
		pCommsConst.fill = pCommsConst.BOTH;
		pCommsConst.gridx = 7;
		pCommsConst.gridy = 0;
		pCommsConst.gridheight = 5;
		pCommsConst.gridwidth = 1;
		GridBagConstraints pShieldConst = new GridBagConstraints();
		pShieldConst.fill = pShieldConst.BOTH;
		pShieldConst.gridx = 8;
		pShieldConst.gridy = 0;
		pShieldConst.gridheight = 5;
		pShieldConst.gridwidth = 1;
		GridBagConstraints pGunsConst = new GridBagConstraints();
		pGunsConst.fill = pGunsConst.BOTH;
		pGunsConst.gridx = 9;
		pGunsConst.gridy = 0;
		pGunsConst.gridheight = 5;
		pGunsConst.gridwidth = 1;
		
		
		
		
		
		throttle = new JSlider();
		throttle.setMaximum(100);
		throttle.setMinimum(0);
		throttle.setMajorTickSpacing(10);
		throttle.setOrientation(JSlider.VERTICAL);
		throttle.setName("Throttle Position");
		throttle.setEnabled(false);	
		GridBagConstraints throttleConst = new GridBagConstraints();
		throttleConst.fill = throttleConst.BOTH;
		throttleConst.gridx = 0;
		throttleConst.gridy = 0;
		throttleConst.gridwidth = 1;
		throttleConst.gridheight = 5;
		
		
		heading = new JSlider();
		heading.setMaximum(180);
		heading.setMinimum(-180);
		heading.setMajorTickSpacing(30);
		heading.setEnabled(false);
		GridBagConstraints headingConst = new GridBagConstraints();
		headingConst.fill = headingConst.BOTH;
		headingConst.gridx = 1;
		headingConst.gridy = 0;
		headingConst.gridwidth = 5;
		headingConst.gridheight = 1;
		
		
		dataPanel.add(throttle,throttleConst);
		dataPanel.add(heading,headingConst);
		
		displayPanel = new DisplayPanel(map);
		displayPanel.setBackground(Color.black);
		displayPanel.setPreferredSize(new Dimension(1600,1000));
		
		windowPanel.add(displayPanel,BorderLayout.CENTER);
		windowPanel.add(dataPanel,BorderLayout.SOUTH);
		
		windowFrame.add(windowPanel);
		windowFrame.setResizable(false);
		windowFrame.setTitle("SpaceGame Ground Controller");
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setResizable(true);
		windowFrame.setVisible(true);
		windowFrame.setPreferredSize(new Dimension(1000,700));
		windowFrame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    c.sendMessage("exit");
			  }
			});
		
		windowFrame.pack();
		
		
	}
	public void run()
	{
		while(gcGame.running)
		{
			try
			{
				Thread.sleep(50);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			windowPanel.repaint();
		}
	}
}
