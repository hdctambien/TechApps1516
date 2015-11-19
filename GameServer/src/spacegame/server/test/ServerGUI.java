package spacegame.server.test;

import javax.swing.JFrame;

import mapgui.MapViewPanel;

public class ServerGUI implements Runnable {

	private JFrame frame;
	private MapViewPanel panel;
	public ServerGUI() {
		
	}
	
	@Override
	public void run(){
		frame = new JFrame("Server Map GUI");
		//panel = new MapViewPanel();
	}

}
