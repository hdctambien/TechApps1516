package spacegame.engineer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import spacegame.client.Client;

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
	private JPanel powerPanel, powerBG, powerBG2, powerBGL, powerBGL2;
	
	private JLabel fuel;
	private JLabel comms;
	private JLabel shield;
	private JLabel guns;
	private JLabel powerL;
	
	private JLabel thro;
		
	private GridBagLayout bag;
	private GridBagConstraints bagC;

	private int WIN_HEIGHT;
	private int WIN_WIDTH;
	private int throt = 0;
	private Dimension size;	
	
	public EngineerGUI(EngineerGame g, Client eClient)
	{
		this.game = g;
		this.client = eClient;
		WIN_HEIGHT = 700;
		WIN_WIDTH  = 700;
		size = new Dimension(WIN_HEIGHT, WIN_WIDTH);
		frame = new JFrame();
		panel = new JPanel();
		bag = new GridBagLayout();
		bagC = new GridBagConstraints();
		
		powerBG = new JPanel();
		powerBG2 = new JPanel();
		powerBGL = new JPanel();
		powerBGL2 = new JPanel();

		thro = new JLabel(Integer.toString(throt));
		
		//frame.add(panel);
		
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
		
		pFuel = new JSlider(JSlider.VERTICAL, 0, 100, 25);
		pFuel.setMajorTickSpacing(10);
		pFuel.setMinorTickSpacing(5);
		pFuel.setPaintTicks(true);
		pFuel.setValue(25);
		pFuel.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pF = ((JSlider) e.getSource()).getValue();
					game.powerFuel(pF);
				}					
			});
		
		pComms  = new JSlider(JSlider.VERTICAL, 0, 100, 25);
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
		
		pShield = new JSlider(JSlider.VERTICAL, 0, 100, 25);
		pShield.setMajorTickSpacing(10);
		pShield.setMinorTickSpacing(5);
		pShield.setPaintTicks(true);
		pShield.setValue(25);
		pShield.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pS = ((JSlider) e.getSource()).getValue();
					game.powerShield(pS);
				}					
			});
		
		pGuns   = new JSlider(JSlider.VERTICAL, 0, 100, 25);
		pGuns.setMajorTickSpacing(10);
		pGuns.setMinorTickSpacing(5);
		pGuns.setPaintTicks(true);
		pGuns.setValue(25);
		pGuns.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pG = ((JSlider) e.getSource()).getValue();
					System.out.println(pG);
					game.powerGuns(pG);
				}					
			});
		
		panel.setLayout(null);
		frame.setLayout(null);
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
		
		frame.add(pFuel);
		frame.add(fuel);
		frame.add(pComms);
		frame.add(comms);
		frame.add(pShield);
		frame.add(shield);
		frame.add(pGuns);
		frame.add(guns);
		frame.add(powerL);
		frame.add(powerBGL);
		frame.add(powerBGL2);
		frame.add(powerBG2);
		frame.add(powerBG);
		
		
		//panel.add(pFuel, bagC);
		
		//powerPanel.add(pFuel);
		//powerPanel.add(pComms);
		//powerPanel.add(pShield);
		//powerPanel.add(pGuns);
		
		//tp.setBackground(Color.BLACK);
		//panel.add(tp, BorderLayout.CENTER);
		//panel.add(powerPanel, BorderLayout.NORTH);

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