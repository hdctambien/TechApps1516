
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import java.util.Scanner;
public class process 
{
	
	private static final Graphics Graphics = null;
	public JPanel testpanel;
	public JFrame frame= new JFrame();
	
	public int xcord,ycord,zcord,angle;
	public process()
	{
		test();
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,800);
		frame.setVisible(true);
			

		
	}
	public static void main(String[] args)
	{
		
		Scanner input = new Scanner( System.in );
		boolean done = false, finished = false;
		
		
		
		
		
		JPanel panel = new JPanel(), buttons = new JPanel(); 
		panel.setSize(1000,600);
		
				
		JButton up = new JButton();
		JButton down = new JButton();
		JButton left = new JButton();
		JButton right = new JButton();
		
		buttons.add(left);
		buttons.add(up);
		buttons.add(down);
		buttons.add(right);
		
	//	frame.add(panel);
	//	frame.add(buttons);
	
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
		new process();
		
	//	createCircle(rand,panel);
		
	}
	public void test()
	{
		
		final Draw dc = new Draw();
		testpanel = new JPanel()
		{
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				xcord = 400;
				ycord = 400;
			//	g2d = dc.draw(g2d, xcord, ycord);
			//	g = g2d;
				g2d.draw(new Ellipse2D.Double(xcord, ycord, 100, 100));
			}
		};
		frame.add(testpanel);
	}
	
}
class Draw
{
	public Graphics2D draw(Graphics2D g2d, int x, int y)
	{
		System.out.println("entered");
		g2d.draw(new Ellipse2D.Double(x, y, 100, 100));
		return g2d;
	}
}
	

