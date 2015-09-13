package spacegame.engineer;

import javax.swing.*;

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

	private int WIN_HEIGHT;
	private int WIN_WIDTH;
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
		frame.add(panel);
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
		panel.add(exit);
		panel.add(stop);
		panel.setVisible(true);
		frame.setSize(size);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				client.sendMessage("exit");
				System.exit(8080);
			}
		});
		setColor("red");
	}
	
	public void setColor(String color)
	{
		if(color.equals("red"))
			panel.setBackground(Color.RED);
		else
			if(color.equals("green"))
				panel.setBackground(Color.GREEN);
	}
	
	public void run()
	{
		running = true;
		while(running)
		{
			
		}
	}	
}