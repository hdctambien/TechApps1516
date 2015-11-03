package spacegame.engineer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import spacegame.client.Client;
import spacegame.client.ProtocolAggregator;
import spacegame.client.chat.ChatPanel;
import spacegame.client.chat.ChatProtocol;
import spacegame.gui.HeadingDial;
import spacegame.map.GameMap;
import spacegame.render.ImageLoader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EngineerGUI extends Thread
{
	private boolean running = false;
	EngineerGame game;
	Client client;
	GameMap map;
	JFrame frame;
	JPanel panel;
	JPanel t1, t2;
	
	ImageLoader imgLoad;
	
	private JPanel reactor;
	private Graphics r;
	private Image water;
	private Image waterScale;
	
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
		
		t1 = new JPanel();
		t2 = new JPanel();
		
		imgLoad = new ImageLoader();
		try {
			imgLoad.loadImages();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		WIN_HEIGHT = 800;
		WIN_WIDTH  = 1200;
		size = new Dimension(WIN_WIDTH, WIN_HEIGHT);
		frame = new JFrame("Engineer");
		panel = new JPanel();
		powerPanel = new JPanel();
		bag = new GridBagLayout();
		bagC = new GridBagConstraints();
		
		chatCreate(pa);		
		powerCreate();
		
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.PAGE_END);
		panel.setLayout(new GridLayout(1, 4));
		panel.add(powerPanel);
		panel.add(chat);
		panel.setBackground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		
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
		
		Reactor();
	}
	
	public void chatCreate(ProtocolAggregator pa)
	{
		chat = new ChatPanel(250, 250);
		
		aggregator = pa;
		
		ChatProtocol protocol = new ChatProtocol(this.client,chat);
		chat.addChatListener(protocol);
		aggregator.addProtocol(protocol);
	}
	
	public void powerCreate()
	{
		powerBG = new JPanel();
		powerBG2 = new JPanel();
		powerBGL = new JPanel();
		powerBGL2 = new JPanel();
		
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
		fuel.setBounds(30, 30, 25, 10);
		comms  = new JLabel("Comms");
		comms.setBounds(82, 30, 50, 10);
		shield = new JLabel("Shield");
		shield.setBounds(145, 30, 50, 10);
		guns   = new JLabel("Guns");
		guns.setBounds(208, 30, 30, 10);
		powerL  = new JLabel("Power");
		powerL.setBounds(117, 10, 50, 10);
		
		pFuel.setOpaque(false);
		pComms.setOpaque(false);
		pShield.setOpaque(false);
		pGuns.setOpaque(false);
		
		pFuel.setBounds(20, 40, 60, 150);
		pComms.setBounds(80, 40, 60, 150);
		pShield.setBounds(140, 40, 60, 150);
		pGuns.setBounds(200, 40, 60, 150);
		powerBG.setBounds(10, 15, 260, 187);
		powerBG2.setBounds(12, 17, 256, 183);
		powerBGL.setBounds(115, 8, 42, 15);
		powerBGL2.setBounds(113, 6, 46, 19);
		
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
		
		powerPanel.setPreferredSize(new Dimension(275, 210));
		
		fuel   = new JLabel("Fuel");
		comms  = new JLabel("Comms");
		shield = new JLabel("Shield");
		guns   = new JLabel("Guns");
		powerL  = new JLabel("Power");
		
		powerPanel.setLayout(null);
	}
	
	public void Reactor()
	{
		water = (Image)imgLoad.getImage("Water.png");
		waterScale = water.getScaledInstance(1200, 500, Image.SCALE_SMOOTH);		
		
		reactor = new JPanel()
		{
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(waterScale, 0, 0, null);
            }
        };
		reactor.setPreferredSize(new Dimension(150, 150));
		panel.add(reactor);
		reactor.setVisible(true);
	}
	
	public void run()
	{
		running = true;
		while(running)
		{
			
		}
	}	
}