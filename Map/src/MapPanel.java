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

import spacegame.map.MapAction;

public class MapPanel{
	public static int xCord, yCord;
	
	
	public static JFrame frame = new JFrame();
	public static JPanel mapPanel;
	public static JViewport view;
	public static JLabel ship;
	public static Point point;
	public static MapComponent map;
	public static void mapPanel()
	{
		Scanner input = new Scanner( System.in );
		boolean running = true;
		
		final MapClient pilot = new MapClient();
		pilot.setup();
		pilot.subscribe();
		
	
		mapPanel = new JPanel();
		mapPanel.setSize(1000,1000);
		
		map = new MapComponent();
		map.setPreferredSize(new Dimension(1200,600));
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
	

/*		view = new JViewport();
		view.setView(mapPanel);
		view.setPreferredSize(new Dimension(100,100));
		view.setExtentSize(dimension);
		view.setOpaque(false);
		view.setViewSize(dimension1);
		view.setViewPosition(point);
		view.setVisible(true); */
		
		//frame.setLayout(null);
		frame.add(mapPanel, BorderLayout.NORTH);
		frame.add(map, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setVisible(true);
		
		
		run();
		
	//	choice = input.nextLine();
	//	ship.setBounds(500, 500, 50, 50);
	//	frame.revalidate();
		
	}
	public static void main(String[] args)
	{
		mapPanel();
	}
	public static void run()
	{
		while(true)
		{
			map.setPosition(xCord, yCord);
			map.repaint();
			frame.revalidate();
		}
	}

	public static void setY(int posY)
	{
		yCord = posY;
		System.out.println("set y to: "+ posY);
	}
	public static void setX(int posX)
	{
		xCord = posX;
		System.out.println("set x to: "+ posX);
	}
}
