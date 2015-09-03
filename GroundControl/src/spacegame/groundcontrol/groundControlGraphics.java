package spacegame.groundcontrol;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextArea;

class groundControlGraphics extends Thread 
{
	JFrame windowFrame;    
    public Container cont = new Container();
	private groundControlGame gcGame;
	private JTextArea textBox = new JTextArea();
	
	public groundControlGraphics(groundControlGame groundControlGame) 
	{
		gcGame = groundControlGame;
		windowFrame = new JFrame();
		textBox.setVisible(true);
		textBox.setText("No message Recieved yet");
		cont.add(textBox);
		
		
		windowFrame.setResizable(false);
		windowFrame.setTitle("SpaceGame Ground Controller");
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
		}
	}
	void writeText(String textIn)
	{
		textBox.setText(textIn);
	}
}
