package spacegame.groundcontrol;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import spacegame.client.Client;

class groundControlGraphics extends Thread 
{
	JFrame windowFrame;    
    public JPanel windowPanel = new JPanel();
	private groundControlGame gcGame;
	private JButton testButton1;
	Client c;
	private boolean buttonStatus = false;
	private boolean buttonUpdated = true;
	
	private MouseAdapter mouse = new MouseAdapter()
	{ 
		public void mousePressed(MouseEvent e)
		{
			buttonStatus = true;
			buttonUpdated = false;
			System.out.println("Button pressed");
		}
		public void mouseReleased(MouseEvent e)
		{
			buttonStatus = false;
			buttonUpdated = false;
			System.out.println("Button released");
		}
	};
	
	public groundControlGraphics(groundControlGame groundControlGame, final Client c) 
	{
		gcGame = groundControlGame;
		windowFrame = new JFrame();
		windowPanel.setVisible(true);
		this.c = c;
		
		testButton1 = new JButton();
		testButton1.setText("Test Button");
		testButton1.addMouseListener(mouse);
		testButton1.setVisible(true);
		windowPanel.add(testButton1);
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
		windowFrame.add(windowPanel);
		windowFrame.pack();
		
	}
	public void run()
	{
		while(gcGame.running)
		{
			if(!buttonUpdated)
			{
				c.sendMessage("set buttonStatus " + buttonStatus);
				buttonUpdated = true;
				System.out.println(buttonStatus);
			}
		}
	}
}
