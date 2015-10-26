package spacegame.groundcontrol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import spacegame.client.Client;
import spacegame.client.ProtocolAggregator;
import spacegame.client.chat.ChatPanel;
import spacegame.map.GameMap;
import spacegame.gui.HeadingDial;
import spacegame.mapgui.MapComponent;
import spacegame.client.chat.*;

/**
 * Runs on the Graphics thread started by groundControlGame, opens up a JFrame and displays some basic info for now.
 * 
 * @author Justin Pierre
 */

class groundControlGraphics extends Thread 
{
	private String SHIP_NAME;
	JFrame windowFrame;    
    public JPanel windowPanel;
    private JPanel dataPanel;
	private groundControlGame gcGame;
	private GridBagLayout gridBag;
	Client c;
	private GameMap map;
	
	private JSlider throttle;
	
	private MapComponent mapPanel;
	
	private JPanel powerPanel, powerBG, powerBG2, powerBGL, powerBGL2;
	private JSlider pFuel;
	private JSlider pComms;
	private JSlider pShield;
	private JSlider pGuns;
	private JLabel fuel;
	private JLabel comms;
	private JLabel shield;
	private JLabel guns;
	private JLabel powerL;
	
	private ChatProtocol chatProtocol;
	private static ProtocolAggregator aggregator;
	
	private HeadingDial headingDial;
	private ChatPanel chatPanel;
	boolean right = false;
	boolean left = false;
	
	public groundControlGraphics(groundControlGame groundControlGame, final Client c, GameMap map, String shipName, ProtocolAggregator aggregator) 
	{		
		headingDial = new HeadingDial();
		SHIP_NAME = shipName;
		this.map = map;
		gcGame = groundControlGame;
		chatPanel = new ChatPanel(300,225);
		windowFrame = new JFrame();
		windowPanel = new JPanel(new BorderLayout());
		windowPanel.setVisible(true);
		dataPanel = new JPanel(new GridLayout());
		powerPanel = new JPanel(null);
		mapPanel = new MapComponent();
		this.c = c;
		
		
		try
		{
			this.aggregator = aggregator;
			chatProtocol = new ChatProtocol(c, chatPanel);
			chatPanel.addChatListener(chatProtocol);
			aggregator.addProtocol(chatProtocol);
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
		
		powerBG = new JPanel();
		powerBG2 = new JPanel();
		powerBGL = new JPanel();
		powerBGL2 = new JPanel();
		powerBG.setBackground(Color.GREEN);
		powerBG.setVisible(true);
		powerBG2.setBackground(Color.WHITE);
		powerBG2.setVisible(true);
		powerBGL.setBackground(Color.WHITE);
		powerBGL.setVisible(true);
		powerBGL2.setBackground(Color.GREEN);
		powerBGL2.setVisible(true);
		
		
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
		
		throttle = new JSlider();
		throttle.setMaximum(100);
		throttle.setMinimum(0);
		throttle.setMajorTickSpacing(10);
		throttle.setOrientation(JSlider.VERTICAL);
		throttle.setName("Throttle Position");
		throttle.setEnabled(false);		
		
		fuel   = new JLabel("Fuel");
		fuel.setBounds(35, 30, 25, 10);
		comms  = new JLabel("Comms");
		comms.setBounds(87, 30, 50, 10);
		shield = new JLabel("Shield");
		shield.setBounds(150, 30, 50, 10);
		guns   = new JLabel("Guns");
		guns.setBounds(213, 30, 30, 10);
		powerL  = new JLabel("Power");
		powerL.setBounds(122, 10, 50, 10);
		
		pFuel.setOpaque(false);
		pComms.setOpaque(false);
		pShield.setOpaque(false);
		pGuns.setOpaque(false);
		
		pFuel.setBounds(25, 40, 60, 150);
		pComms.setBounds(85, 40, 60, 150);
		pShield.setBounds(145, 40, 60, 150);
		pGuns.setBounds(205, 40, 60, 150);
		powerBG.setBounds(15, 15, 260, 179);
		powerBG2.setBounds(17, 17, 256, 175);
		powerBGL.setBounds(120, 8, 42, 15);
		powerBGL2.setBounds(118, 6, 46, 19);
		
		powerPanel.add(pFuel);
		powerPanel.add(fuel);
		powerPanel.add(pComms);
		powerPanel.add(comms);
		powerPanel.add(pShield);
		powerPanel.add(shield);
		powerPanel.add(pGuns);
		powerPanel.add(guns);
		powerPanel.add(powerL);
		powerPanel.add(powerBGL);
		powerPanel.add(powerBGL2);
		powerPanel.add(powerBG2);
		powerPanel.add(powerBG);
		
		headingDial.setRadius(100);
		
		dataPanel.add(throttle);
		dataPanel.add(headingDial);
		dataPanel.add(powerPanel);
		dataPanel.add(chatPanel);
		
		windowPanel.add(mapPanel,BorderLayout.CENTER);
		windowPanel.add(dataPanel,BorderLayout.SOUTH);
		
		windowPanel.addKeyListener(new KeyControl());
		
		windowFrame.add(windowPanel);
		windowFrame.setResizable(false);
		windowFrame.setTitle("SpaceGame Ground Controller");
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setResizable(true);
		windowFrame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    c.sendMessage("exit");
			  }
			});
		windowPanel.requestFocus();
		windowFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		windowFrame.setUndecorated(true);
		windowFrame.pack();
		windowFrame.setVisible(true);
	}
	public void run()
	{
		while(gcGame.running)
		{
			if(right)
				map.getEntityByName(SHIP_NAME).getComponent("Heading").setVariable("heading", Double.toString(-0.01 + Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"))));
			if(left)
				map.getEntityByName(SHIP_NAME).getComponent("Heading").setVariable("heading", Double.toString(0.01 + Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"))));
			
			mapPanel.setHeading((Math.PI*Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"))/180));
			mapPanel.setPosition((int) Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Position").getVariable("posX")),(int)Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Position").getVariable("posY")));
			
			headingDial.setHeading(Math.PI*Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"))/180);
			pFuel.setValue(Integer.parseInt(map.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerFuel")));
			pGuns.setValue(Integer.parseInt(map.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerGuns")));
			pShield.setValue(Integer.parseInt(map.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerShield")));
			pComms.setValue(Integer.parseInt(map.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerComms")));
			windowPanel.repaint();
		}
	}
	
private class KeyControl implements KeyListener 
{
	    public void keyPressed(KeyEvent e) {
	        if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
	        {
	        	right = true;
	        }
	        if(e.getKeyCode() == KeyEvent.VK_LEFT) 
	        {
	        	left = true;
	        }
	    }

		@Override
		public void keyReleased(KeyEvent arg0) 
		{
			left = false;
			right = false;
		}

		@Override
		public void keyTyped(KeyEvent e) 
		{
			// TODO Auto-generated method stub
			
		}
	}
}
