import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JViewport;

public class ShipPanel extends JPanel{
	public static int xCord, yCord;
	
	public static JComponent comp;
	public static JFrame frame = new JFrame();
	public static JPanel mapPanel;
	public static JViewport view;
	public static JLabel ship;
	public static Point point;
	public static void mapPanel()
	{
		mapPanel = new JPanel(null);
		mapPanel.setSize(800,800);
		
		point = new Point();
		point.setLocation(700,700);
		
		Dimension dimension = new Dimension();
		dimension.setSize(100, 100);
		Dimension dimension1 = new Dimension();
		dimension1.setSize(800,800);
		
		JLabel label = new JLabel("test");
		label.setBounds(100,100,50,50);
		mapPanel.add(label);
		
		
	
		view = new JViewport();
		view.setViewSize(dimension1);
		view.setExtentSize(dimension);
		view.setOpaque(false);
		view.setView(mapPanel);
		view.setViewPosition(point);
		view.setVisible(true);
	
		frame.add(view);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
	
		
	}
	public static void main(String[] args)
	{
		mapPanel();
	}
	public void move(int xCord, int yCord)
	{
		point.setLocation(xCord,yCord);
		view.setViewPosition(point);
	}
	

}
