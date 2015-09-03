package spacegame.engineer;

import java.awt.*;
import javax.swing.*;

public class EngineerGame
{
	private JFrame frame;
	
	private int WIN_HEIGHT;
	private int WIN_WIDTH;
	private Dimension size;
	
	public EngineerGame()
	{
		size = new Dimension(800, 600);
		frame = new JFrame();
		frame.setSize(size);
		frame.setVisible(true);
	}	
}
