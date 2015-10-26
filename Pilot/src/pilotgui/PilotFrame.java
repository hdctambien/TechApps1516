package pilotgui;

import javax.swing.JFrame;

public class PilotFrame{
	public JFrame frame;
	public MapComponent map;
	public PilotFrame()
	{
		map = new MapComponoent();
		frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
	}

}
