package spacegame.groundcontrol;

import java.awt.Canvas;

public class RunGroundControl
{
	private static groundControlGame game;
	public static void main(String[] args)
	{
		game = new groundControlGame();
		game.run();
		System.out.println("Something should be happening now");
	}
	
}
