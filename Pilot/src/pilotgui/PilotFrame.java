package pilotgui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import spacegame.map.Entity;
import spacegame.map.GameMap;

public class PilotFrame{
	public JFrame frame;
	public MapComponent map;
	public PilotFrame()
	{
		GameMap game = new GameMap();
		Entity positionEntity = game.getEntityByName("Position");
		positionEntity.getComponent("PositionComponent")
		map = new MapComponoent();
		frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.add(map,BorderLayout.CENTER);
		
		
	}
	public void mapRepaint()
	{
		map.repaint();
	}

}
