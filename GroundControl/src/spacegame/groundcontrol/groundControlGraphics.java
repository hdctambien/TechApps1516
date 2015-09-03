package spacegame.groundcontrol;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

class groundControlGraphics extends Thread 
{
	JFrame windowFrame;    
    public Container cont = new Container();
	private groundControlGame gcGame;
	public groundControlGraphics(groundControlGame groundControlGame) 
	{
		gcGame = groundControlGame;
		windowFrame = new JFrame();
		
		windowFrame.setResizable(false);
		windowFrame.setTitle("SpaceGame Ground Controller");
		windowFrame.pack();
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setResizable(true);
		windowFrame.setVisible(true);
		windowFrame.setPreferredSize(new Dimension(1000,700));
		windowFrame.add(cont);
		windowFrame.pack();
	}
	public void run()
	{
		while(gcGame.running)
		{
			if(!windowFrame.hasFocus())
			{
				try
				{
					this.sleep(50);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				continue;
			}
			System.out.println("Graphics Thread");
			
		}
	}
}
