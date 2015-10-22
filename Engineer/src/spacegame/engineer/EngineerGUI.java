package spacegame.engineer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import spacegame.client.Client;
import spacegame.client.ProtocolAggregator;
import spacegame.client.chat.ChatPanel;
import spacegame.client.chat.ChatProtocol;
import spacegame.gui.HeadingDial;
import spacegame.map.GameMap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EngineerGUI extends Thread
{
	private boolean running = false;
	EngineerGame game;
	Client client;
	GameMap map;
	JFrame frame;
	JPanel panel;
	
	
	private JSlider throttle;
	
	private int power = 100;
	private int pC = 25;
	private int pS = 25;
	private int pF = 25;
	private int pG = 25;
	public JSlider pFuel;
	public JSlider pComms;
	public JSlider pShield;
	public JSlider pGuns;
	public JSlider pReserve;
	private JPanel powerPanel, powerBG, powerBG2, powerBGL, powerBGL2;
	
	private ProtocolAggregator aggregator;
	private ChatPanel chat;
	
	private JLabel fuel;
	private JLabel comms;
	private JLabel shield;
	private JLabel guns;
	private JLabel powerL;
	private JLabel reserve;
	
	private JLabel thro;
		
	private GridBagLayout bag;
	private GridBagConstraints bagC;

	private int WIN_HEIGHT;
	private int WIN_WIDTH;
	private int throt = 0;
	private Dimension size;	
	
	public EngineerGUI(EngineerGame g, Client eClient, ProtocolAggregator pa, GameMap m)
	{
		this.map = m;
		this.game = g;
		this.client = eClient;
		WIN_HEIGHT = 700;
		WIN_WIDTH  = 700;
		size = new Dimension(WIN_HEIGHT, WIN_WIDTH);
		frame = new JFrame();
		panel = new JPanel();
		powerPanel = new JPanel();
		bag = new GridBagLayout();
		bagC = new GridBagConstraints();
		chat = new ChatPanel(325, 200);
		
		aggregator = pa;
		
		ChatProtocol protocol = new ChatProtocol(this.client,chat);
		chat.addChatListener(protocol);
		aggregator.addProtocol(protocol);
		
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1,2));
		panel.add(powerPanel);
		panel.add(chat);
		
		powerBG = new JPanel();
		powerBG2 = new JPanel();
		powerBGL = new JPanel();
		powerBGL2 = new JPanel();

		thro = new JLabel(Integer.toString(throt));
		
		throttle = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		throttle.setMajorTickSpacing(10);
		throttle.setMinorTickSpacing(5);
		throttle.setPaintTicks(true);
		throttle.setPaintLabels(true);
		throttle.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				JSlider source = (JSlider)e.getSource();
				throt = source.getValue();
				thro.setText(Integer.toString(throt));
				Throt(throt);
			}
		});
		
		powerCreate();
		
	/*	pFuel = new JSlider(JSlider.VERTICAL, 0, 100, 20);
		pFuel.setMajorTickSpacing(10);
		pFuel.setMinorTickSpacing(5);
		pFuel.setPaintTicks(true);
		pFuel.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pF = ((JSlider) e.getSource()).getValue();
					game.powerFuel(pF);
				}					
			});
		
		pComms  = new JSlider(JSlider.VERTICAL, 0, 100, 20);
		pComms.setMajorTickSpacing(10);
		pComms.setMinorTickSpacing(5);
		pComms.setPaintTicks(true);
		pComms.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pC = ((JSlider) e.getSource()).getValue();
					game.powerComms(pC);
				}					
			});
		
		pShield = new JSlider(JSlider.VERTICAL, 0, 100, 20);
		pShield.setMajorTickSpacing(10);
		pShield.setMinorTickSpacing(5);
		pShield.setPaintTicks(true);
		pShield.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pS = ((JSlider) e.getSource()).getValue();
					game.powerShield(pS);
				}					
			});
		
		pGuns   = new JSlider(JSlider.VERTICAL, 0, 100, 20);
		pGuns.setMajorTickSpacing(10);
		pGuns.setMinorTickSpacing(5);
		pGuns.setPaintTicks(true);
		pGuns.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pG = ((JSlider) e.getSource()).getValue();
					System.out.println(pG);
					game.powerGuns(pG);
				}					
			});
		
		powerPanel.setLayout(null);
		powerBG.setBackground(Color.GREEN);
		powerBG.setVisible(true);
		powerBG2.setBackground(Color.WHITE);
		powerBG2.setVisible(true);
		powerBGL.setBackground(Color.WHITE);
		powerBGL.setVisible(true);
		powerBGL2.setBackground(Color.GREEN);
		powerBGL2.setVisible(true);
		
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
		powerBG.setBounds(15, 15, 260, 187);
		powerBG2.setBounds(17, 17, 256, 183);
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
		powerPanel.add(powerBG);*/

		panel.setVisible(true);
		frame.setSize(size);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{
			    client.sendMessage("exit");
			}
		});
	}
	
	public void chatCreate()
	{
		
	}
	
	public void powerCreate()
	{
		pFuel = new JSlider(JSlider.VERTICAL, 0, 100, 20);
		pFuel.setMajorTickSpacing(10);
		pFuel.setMinorTickSpacing(5);
		pFuel.setPaintTicks(true);
		pFuel.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pF = ((JSlider) e.getSource()).getValue();
					game.powerFuel(pF);
				}					
			});
		
		pComms  = new JSlider(JSlider.VERTICAL, 0, 100, 20);
		pComms.setMajorTickSpacing(10);
		pComms.setMinorTickSpacing(5);
		pComms.setPaintTicks(true);
		pComms.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pC = ((JSlider) e.getSource()).getValue();
					game.powerComms(pC);
				}					
			});
		
		pShield = new JSlider(JSlider.VERTICAL, 0, 100, 20);
		pShield.setMajorTickSpacing(10);
		pShield.setMinorTickSpacing(5);
		pShield.setPaintTicks(true);
		pShield.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pS = ((JSlider) e.getSource()).getValue();
					game.powerShield(pS);
				}					
			});
		
		pGuns   = new JSlider(JSlider.VERTICAL, 0, 100, 20);
		pGuns.setMajorTickSpacing(10);
		pGuns.setMinorTickSpacing(5);
		pGuns.setPaintTicks(true);
		pGuns.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pG = ((JSlider) e.getSource()).getValue();
					System.out.println(pG);
					game.powerGuns(pG);
				}					
			});
		
		powerPanel.setLayout(null);
		powerBG.setBackground(Color.GREEN);
		powerBG.setVisible(true);
		powerBG2.setBackground(Color.WHITE);
		powerBG2.setVisible(true);
		powerBGL.setBackground(Color.WHITE);
		powerBGL.setVisible(true);
		powerBGL2.setBackground(Color.GREEN);
		powerBGL2.setVisible(true);
		
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
		powerBG.setBounds(15, 15, 260, 187);
		powerBG2.setBounds(17, 17, 256, 183);
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
	}
	
	public void Throt(int thrott)
	{
		game.Throttle(thrott);
	}
	
	public void run()
	{
		running = true;
		while(running)
		{
			
		}
	}	
}