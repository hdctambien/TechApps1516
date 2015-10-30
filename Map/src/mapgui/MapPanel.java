package mapgui;

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

import spacegame.map.Entity;
import spacegame.map.GameMap;
import spacegame.map.MapAction;
import spacegame.map.components.Component;

public class MapPanel
{
	public int xCord, yCord, width = 50;
	public double heading = 45;
	
	
	public JFrame frame = new JFrame();
	public MapComponent map;
	public GameMap game;
	public final String SHIP_NAME = "Ship.1";
	
	Entity ship;
	Component physComponent;
	Component posComponent;
	
	Entity headingEntity;
	Entity positionEntity;
	public MapPanel(GameMap g)
	{
	/*	final MapClient pilot = new MapClient();
		pilot.setup();
		pilot.subscribe();*/
		
		game = g;
		
		map = new MapComponent();
		map.setPreferredSize(new Dimension(1200,600));
		
		frame.add(map, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setVisible(true);
		
		System.out.println(game.getEntities());
		ship = game.getEntityByName(SHIP_NAME);
		physComponent = ship.getComponent("Physics");
		posComponent = ship.getComponent("Position");
		
		
		xCord = 100;
		yCord = 100;
		run();
	}
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException ex)
			{
				Thread.currentThread().interrupt();
			}
			
			posComponent.setVariable("posX", Integer.toString(xCord + 10));
			posComponent.setVariable("posY", Integer.toString(yCord + 10));
			
			xCord = Integer.parseInt(posComponent.getVariable("posX"));
			yCord = Integer.parseInt(posComponent.getVariable("posY"));
		
			
			map.setPosition(xCord, yCord);
			map.setHeading(heading);
			map.setWidth(width);
			
			frame.revalidate();
			map.repaint();
		
		}
	}

	public void setY(int posY)
	{
		yCord = posY;
		System.out.println("set y to: "+ posY);
	}
	public void setX(int posX)
	{
		xCord = posX;
		System.out.println("set x to: "+ posX);
	}
	public void setHeading(double h)
	{
		heading = h;
	}
	public void setWidth(int width)
	{
		map.setWidth(width);
	}
}
