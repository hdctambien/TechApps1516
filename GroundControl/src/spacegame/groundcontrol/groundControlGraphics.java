package spacegame.groundcontrol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
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

/**
 * Runs on the Graphics thread started by groundControlGame, opens up a JFrame and displays some basic info for now.
 * 
 * @author Justin Pierre
 */

class groundControlGraphics extends Thread 
{
	JFrame windowFrame;    
    public JPanel windowPanel;
    private JPanel dataPanel;
	private groundControlGame gcGame;
	private GridBagLayout gridBag;
	Client c;
	
	private JSlider throttle;
	private JSlider heading;
	private JPanel displayPanel;
	
	public groundControlGraphics(groundControlGame groundControlGame, final Client c) 
	{		
		gcGame = groundControlGame;
		windowFrame = new JFrame();
		windowPanel = new JPanel(new BorderLayout());
		windowPanel.setVisible(true);
		dataPanel = new JPanel(new GridLayout());
		this.c = c;
		
		
		throttle = new JSlider();
		throttle.setMaximum(100);
		throttle.setMinimum(0);
		throttle.setMajorTickSpacing(10);
		throttle.setOrientation(JSlider.VERTICAL);
		throttle.setName("Throttle Position");
		throttle.setEnabled(false);	
		GridBagConstraints throttleConst = new GridBagConstraints();
		throttleConst.fill = throttleConst.BOTH;
		throttleConst.gridx = 0;
		throttleConst.gridy = 0;
		throttleConst.gridwidth = 1;
		throttleConst.gridheight = 5;
		
		
		heading = new JSlider();
		heading.setMaximum(180);
		heading.setMinimum(-180);
		heading.setMajorTickSpacing(30);
		heading.setEnabled(false);
		GridBagConstraints headingConst = new GridBagConstraints();
		headingConst.fill = headingConst.BOTH;
		headingConst.gridx = 1;
		headingConst.gridy = 0;
		headingConst.gridwidth = 5;
		headingConst.gridheight = 1;
		
		
		dataPanel.add(throttle,throttleConst);
		dataPanel.add(heading,headingConst);
		
		displayPanel = new JPanel();
		displayPanel.setBackground(Color.black);
		displayPanel.setPreferredSize(new Dimension(1600,1000));
		
		windowPanel.add(displayPanel,BorderLayout.CENTER);
		windowPanel.add(dataPanel,BorderLayout.SOUTH);
		
		windowFrame.add(windowPanel);
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
		
		windowFrame.pack();
		
		
		
	}
	public void run()
	{
		while(gcGame.running)
		{
			try
			{
				Thread.sleep(50);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			windowPanel.repaint();
		}
	}
}
