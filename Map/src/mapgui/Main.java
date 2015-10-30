package mapgui;

import spacegame.map.GameMap;

public class Main {
	public static void main(String[] args)
	{
		GameMap game = new GameMap();
		new MapPanel(game);
	}
}
