import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class MapComponent extends JComponent {

	private int x, y;
	
	public MapComponent(){
		
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.fillRect(x, y, 20, 20);
	}
	
}
