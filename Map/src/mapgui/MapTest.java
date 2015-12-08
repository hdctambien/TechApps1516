package mapgui;

import java.awt.BorderLayout;

import javax.swing.*;

import spacegame.map.*;

public class MapTest {

	public static JFrame frame;
	public static MapViewPanel panel;
	public static void main(String[] args){
		frame = new JFrame("Map Tester");
		GameMap map = createTestMap();
		panel = new MapViewPanel(map,"Ship.1");
		frame.setSize(600,600);
		frame.add(panel,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private static GameMap createTestMap(){
		GameMap map = new GameMap();
		EntityFactory factory = new EntityFactory();
		map.addEntity(factory.createAsteroid());
		map.addEntity(factory.createShip());
		map.addEntity(factory.createOrb(0, 0));
		return map;
	}
}
