package spacegame.groundcontrol;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	private groundControlGame gcGame;
	private GridBagLayout gridBag;
	Client c;
	
	private JSlider throttle;
	private JPanel displayPanel;
	
	public groundControlGraphics(groundControlGame groundControlGame, final Client c) 
	{
		gridBag = new GridBagLayout();		
		gcGame = groundControlGame;
		windowFrame = new JFrame();
		windowPanel = new JPanel(gridBag);
		windowPanel.setVisible(true);
		this.c = c;
		
		throttle = new JSlider();
		throttle.setMaximum(100);
		throttle.setMinimum(0);
		throttle.setMajorTickSpacing(10);
		throttle.setOrientation(JSlider.VERTICAL);
		throttle.setName("Throttle Position");
		throttle.setEnabled(false);
		GridBagConstraints throttleConst = new GridBagConstraints();
		throttleConst.gridheight = 5;
		throttleConst.gridwidth = 1;
		throttleConst.gridx = 10;
		throttleConst.gridy = 10;
		
		
		displayPanel = new JPanel();
		displayPanel.setBackground(Color.black);
		displayPanel.setPreferredSize(new Dimension(1600,1000));
		GridBagConstraints displayPanelConst = new GridBagConstraints();
		displayPanelConst.gridheight = 10;
		displayPanelConst.gridwidth = 16;
		displayPanelConst.gridx = 0;
		displayPanelConst.gridy = 0;
		
		gridBag.addLayoutComponent(displayPanel, displayPanelConst);
		gridBag.addLayoutComponent(throttle, throttleConst);
		windowPanel.add(displayPanel);
		windowPanel.add(throttle);
		
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
