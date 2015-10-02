import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class ShipPanel{
	public static int xCord, yCord;
	
	
	public static JFrame frame = new JFrame();
	public static JPanel mapPanel;
	public static JViewport view;
	public static JLabel ship;
	public static Point point;
	public static void mapPanel()
	{
		Scanner input = new Scanner( System.in );
		boolean running = true;
	//	final PilotClient pilot = new PilotClient();
	//	pilot.setup();
	//	pilot.subscribe();
	
		mapPanel = new JPanel(null);
		mapPanel.setSize(1000,1000);
		
/*		JScrollPane pane = new JScrollPane();
		pane.setViewport(view);
		pane.setVisible(true);*/
		
		
	/*	point = new Point();
		point.setLocation(100,100);
		
		Dimension dimension = new Dimension();
		dimension.setSize(800, 800);
		Dimension dimension1 = new Dimension();
		dimension1.setSize(100,100);
		
		pane.setPreferredSize(dimension);*/
		
		ship = new JLabel("test");
		ship.setBounds(400,400,50,50);
		mapPanel.add(ship);
		

/*		view = new JViewport();
		view.setView(mapPanel);
		view.setPreferredSize(new Dimension(100,100));
		view.setExtentSize(dimension);
		view.setOpaque(false);
		view.setViewSize(dimension1);
		view.setViewPosition(point);
		view.setVisible(true); */
		DrawRect rect = new DrawRect();
		mapPanel.add(rect);
		rect.setVisible(true);
		
		frame.setLayout(null);
		frame.add(mapPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setVisible(true);
		
		int x=100,y=100;
		String choice;
		
		
	//	choice = input.nextLine();
	//	ship.setBounds(500, 500, 50, 50);
	//	frame.revalidate();
		
	}
	public static void main(String[] args)
	{
		mapPanel();
	}
	public static void moveShip(int xCord, int yCord)
	{
		ship.setBounds(xCord, yCord, 50, 50);
		frame.revalidate();
	}
	
}
class DrawRect extends JPanel
{
	public void paintComponent(Graphics2D g2d)
	{
		super.paintComponent(g2d);
		g2d.drawRect(100, 100, 50, 50);
	}
}