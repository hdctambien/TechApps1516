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
	private JButton stop;
	private JButton start;
	private JButton exit;
	private JButton get;
	private JSlider throttle;
	private JLabel thro;
	private JPanel testColor;
	private GridBagLayout bag;
	private GridBagConstraints bagC;

	private int WIN_HEIGHT;
	private int WIN_WIDTH;
	private int throt = 0;
	private Dimension size;
	
	
	public EngineerGUI(EngineerGame game, Client eClient)
	{
		this.game = game;
		this.client = eClient;
		WIN_HEIGHT = 1366;
		WIN_WIDTH  = 768;
		size = new Dimension(WIN_HEIGHT, WIN_WIDTH);
		frame = new JFrame();
		panel = new JPanel();
		testColor = new JPanel();
		bag = new GridBagLayout();
		bagC = new GridBagConstraints();
	
		
		panel.setLayout(bag);
		frame.add(panel);
		thro = new JLabel(Integer.toString(throt));
			
		stop = new JButton("Stop");
		stop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				running = false;
			}
		});
		exit = new JButton("Exit Program");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				client.sendMessage("exit");
				System.exit(8080);
			}
		});
		
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
		bagC.gridy = 1;
		bagC.ipadx = 150;
		panel.add(throttle, bagC);
		bagC.ipadx = 0;
		bagC.gridy = 2;
		bagC.ipady = 50;
		panel.add(thro, bagC);
		bagC.gridy = 3;
		bagC.ipady = 50;
		bagC.ipadx = 50;
		panel.add(testColor, bagC);
		testColor.setBackground(Color.BLUE);
		thro.setHorizontalAlignment(JLabel.CENTER);
		throttle.setSize(500, 25);
		
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
	
	public void setColor(String color)
	{
		if(color.equals("red"))
			testColor.setBackground(Color.RED);
		else
			if(color.equals("green"))
				testColor.setBackground(Color.GREEN);
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