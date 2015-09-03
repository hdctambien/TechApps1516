
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import java.util.Scanner;
public class process extends JFrame
{
	public int xcord,ycord,zcord,angle;
	public static void main(String[] args)
	{
		
		Scanner input = new Scanner( System.in );
		boolean done = false, finished = false;
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(), buttons = new JPanel(); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setVisible(true);
		
		JButton up = new JButton();
		JButton down = new JButton();
		JButton left = new JButton();
		JButton right = new JButton();
		
		buttons.add(left);
		buttons.add(up);
		buttons.add(down);
		buttons.add(right);
		
		frame.add(buttons);
	
	//	panel.setLayout(new GridLayout(7,7));
	/*	while(done == false)
		{
			System.out.println("What would you like to do?");
			
			String in = input.nextLine();
			if(in.equals("Change"))
			{
				
			}
			if(in.equals("Display"))
			{
				while(finished == false)
				{
					System.out.println("What would you like to do?");
					String choice = input.nextLine();				
					if(choice.equals("exit"))
					{
						break;
					}
				}
			}
			if(in.equals("exit"))
			{
				done = true;
			}
		}*/
	/*	while(done == false)
		{
			
			
		}*/
		double rand = Math.random();
		
	//	createCircle(rand,panel);
	}
	public void createCircle(double	 cord, Graphics g)
	{
		
	}
}
class Change
{
	private int xcord=0,ycord=0;
	public void changeAngle()
	{
		//send to server
	}
	public void goRight()
	{
		xcord++;
	}
	public void goLeft()
	{
		xcord--;
	}
	public void goUp()
	{
		ycord++;
	}
	public void goDown()
	{
		ycord--;
	}
	
}
