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
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import mapgui.MapViewPanel;
import spacegame.client.Client;
import spacegame.client.ClientUpdater;
import spacegame.client.ProtocolAggregator;
import spacegame.client.chat.ChatPanel;
import spacegame.map.GameMap;
import spacegame.gui.HeadingDial;
import spacegame.client.chat.*;

/**
 * Runs on the Graphics thread started by groundControlGame, opens up a JFrame and displays some basic info for now.
 * 
 * @author Justin Pierre
 */

class GroundControlGraphics extends Thread 
{
	private String SHIP_NAME;
	JFrame windowFrame;    
    public JPanel windowPanel;
    private JPanel dataPanel;
	private GroundControlGame gcGame;
	private GridBagLayout gridBag;
	Client c;
	private GameMap map;
	private GameMap renderMap;
	private ClientUpdater clientUpdater;
	
	private MapViewPanel mapPanel;
	
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
	
	private double heading = 0;
	private int x = 100;
	private int y = 100;
	
	private double xD = 100;
	private double yD = 100;
	
	
	private ChatProtocol chatProtocol;
	private static ProtocolAggregator aggregator;
	
	private InfoPanel infoPanel;
	private HeadingDial headingDial;
	private ChatPanel chatPanel;
	boolean right = false;
	boolean left = false;
	boolean move = false;
	
	private int frameCounter = 0;
	private long lastNanoTime = 0;
	
	public GroundControlGraphics(GroundControlGame groundControlGame, final Client c, String shipName, ProtocolAggregator aggregator, ClientUpdater cU) 
	{		
		headingDial = new HeadingDial();
		SHIP_NAME = shipName;
		map = cU.getMap();
		renderMap = cU.getRenderMap();
		clientUpdater = cU;
		gcGame = groundControlGame;
		chatPanel = new ChatPanel(300,225);
		windowFrame = new JFrame();
		windowPanel = new JPanel(new BorderLayout());
		windowPanel.setVisible(true);
		dataPanel = new JPanel(new GridLayout());
		powerPanel = new JPanel(null);
		mapPanel = new mapgui.MapViewPanel(renderMap, SHIP_NAME);
		infoPanel = new InfoPanel(renderMap);
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
		
		dataPanel.add(infoPanel);
		dataPanel.add(headingDial);
		dataPanel.add(powerPanel);
		dataPanel.add(chatPanel);
		
		windowPanel.add(mapPanel,BorderLayout.CENTER);
		windowPanel.add(dataPanel,BorderLayout.SOUTH);
		
		windowPanel.addKeyListener(new KeyControl());
		
		windowFrame.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				windowPanel.requestFocusInWindow();
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				windowPanel.requestFocusInWindow();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				windowPanel.requestFocusInWindow();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				windowPanel.requestFocusInWindow();
			}
			
		});
		
		windowFrame.add(windowPanel);
		windowFrame.setTitle("SpaceGame Ground Controller");
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setResizable(true);
		windowFrame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    c.sendMessage("exit");
			  }
			});
		windowFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		windowFrame.setUndecorated(true);
		windowFrame.pack();
		windowFrame.setVisible(true);
		windowPanel.requestFocus();
	}
	public void run()
	{
		lastNanoTime = System.nanoTime();
		while(gcGame.running)
		{
			if(right)
			{
				heading -= 0.05;
			}
			if(left)
			{
				heading += 0.05;
			}
			
			if(right || left)
			{
				System.out.println(heading);
				clientUpdater.addUserAction(SHIP_NAME, "heading", Double.toString(heading), c);
			}
			if(move)
			{
				if(Double.parseDouble(renderMap.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle")) < 0.1)
					clientUpdater.addUserAction(SHIP_NAME, "throttle", Double.toString(100), c);
			}
			else
			{
				if(Double.parseDouble(renderMap.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle")) > 99.9)
					clientUpdater.addUserAction(SHIP_NAME, "throttle", Double.toString(0), c);
			}
			try
			{
				Thread.sleep(10);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			if(!(clientUpdater.isDirty() || clientUpdater.isRenderLocked()) && clientUpdater.isDrawDirty())
			{
				continue;
			}
			else
			{
				clientUpdater.setRenderLock(true); 
				headingDial.setHeading(heading);
				pFuel.setValue(Integer.parseInt(renderMap.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerFuel")));
				pGuns.setValue(Integer.parseInt(renderMap.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerGuns")));
				pShield.setValue(Integer.parseInt(renderMap.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerShield")));
				pComms.setValue(Integer.parseInt(renderMap.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerComms")));
				windowPanel.repaint();
				infoPanel.updateInfo();
				clientUpdater.setRenderLock(false);
				clientUpdater.setDrawDirty(false);
			}
			if((lastNanoTime + 1000000000) > System.nanoTime())
			{
				frameCounter++;
			}
			else
			{
				System.out.println("FPS: " + frameCounter);
				lastNanoTime = System.nanoTime();
				frameCounter = 0;
			}
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
	        if(e.getKeyCode() == KeyEvent.VK_UP)
	        {
	        	move = true;
	        }
	    }

		@Override
		public void keyReleased(KeyEvent arg0) 
		{
			left = false;
			right = false;
			move = false;
		}

		@Override
		public void keyTyped(KeyEvent e) 
		{
			// TODO Auto-generated method stub
			
		}
	}
}
