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
	private JPanel powerPanel;
	
	private JPanel tp, tp2, tp3;
	
	private JLabel fuel;
	private JLabel comms;
	private JLabel shield;
	private JLabel guns;
	
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
		
		tp = new JPanel();
		tp2 = new JPanel();
		tp3 = new JPanel();
		
		//panel.setLayout(new GridLayout(2, 2));
		frame.setLayout(new GridLayout(2, 2));
		frame.add(panel);
		frame.add(tp);
		frame.add(tp2);
		frame.add(tp3);
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
		
		pFuel   = new JSlider(JSlider.VERTICAL, 0, 100, 25);
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
		
		powerPanel = new JPanel();
		
		powerPanel.add(pFuel);
		powerPanel.add(pComms);
		powerPanel.add(pShield);
		powerPanel.add(pGuns);
		
		panel.add(powerPanel, JFrame.LEFT_ALIGNMENT);
		
	/*	fuel = new JLabel("Fuel");
		powerPanel.add(fuel);
		comms = new JLabel("Comms");
		powerPanel.add(comms);
		shield = new JLabel("Shield");
		powerPanel.add(shield);
		guns = new JLabel("Guns");
		powerPanel.add(guns);*/
		
	/*	bagC.gridy = 1;
		bagC.ipadx = 150;
		panel.add(throttle);
		bagC.ipadx = 0;
		bagC.gridy = 2;
		bagC.ipady = 50;
		panel.add(thro);
		thro.setHorizontalAlignment(JLabel.CENTER);
		throttle.setSize(500, 25);*/

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