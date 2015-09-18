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
import javax.swing.JSlider;
import javax.swing.JTextArea;

import spacegame.client.Client;

class groundControlGraphics extends Thread 
{
	JFrame windowFrame;    
    public JPanel windowPanel = new JPanel();
	private groundControlGame gcGame;
	private JButton testButton1;
	private JSlider throttle;
	private JSlider rocketHeading;
	Client c;
	private boolean buttonStatus = false;
	private boolean buttonUpdated = true;
	
	
	private MouseAdapter mouse = new MouseAdapter()
	{ 
		public void mousePressed(MouseEvent e)
		{
			buttonStatus = true;
			buttonUpdated = false;
		}
		public void mouseReleased(MouseEvent e)
		{
			buttonStatus = false;
			buttonUpdated = false;
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
		
		throttle = new JSlider();
		rocketHeading = new JSlider();
		
		throttle.setMaximum(100);
		throttle.setMinimum(0);
		throttle.setMajorTickSpacing(10);
		throttle.setOrientation(JSlider.VERTICAL);
		throttle.setName("Throttle Position");
		throttle.setEnabled(false);
		
		rocketHeading.setMaximum(180);
		rocketHeading.setMinimum(-180);
		rocketHeading.setMajorTickSpacing(15);
		rocketHeading.setName("Rocket Heading (Degrees from Vertical)");
		rocketHeading.setEnabled(false);
		
		
		windowPanel.add(testButton1);
		windowPanel.add(rocketHeading);
		windowPanel.add(throttle);
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
			}
		}
	}
	public void updateRocketVelocity(double rocketVelocity) {
		// TODO Auto-generated method stub
		
	}
	public void updateRocketHeading(double rH) 
	{
		rocketHeading.setValue((int) Math.round(rH));
		System.out.println("Setting value of Rocket Heading to " + rH);
	}
	public void updateRocketPosX(double rPX) 
	{
		// TODO Auto-generated method stub
		
	}
	public void updateRocketPosY(double rPY) 
	{
		// TODO Auto-generated method stub
		
	}
	public void updateHasLink(boolean hL) 
	{
		// TODO Auto-generated method stub
		
	}
	public void updateThrottle(double t) 
	{
		throttle.setValue((int) Math.round(t));
	}
}
