package mapgui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;
import spacegame.map.GameMap;
import spacegame.render.ImageLoader;

public class MapViewPanel extends JPanel
{
	private GameMap map;
	private BufferedImage shipIMG;
	private final String SHIP_NAME;
	private AffineTransform at;
	private ImageLoader loader;
	
	public MapViewPanel(GameMap m, String shipname)
	{
		map = m;
		SHIP_NAME = shipname;
		loader = new ImageLoader();
		try {
			loader.loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		shipIMG = loader.getImage(map.getEntityByName(SHIP_NAME).getComponent("Render").getVariable("imagePath"));
		at = new AffineTransform();
	}
	
	public void paintComponent(Graphics G) 
	{
		at.translate(-shipIMG.getWidth()/2, -shipIMG.getHeight()/2);
        at.rotate(1.5);
        at.translate(getWidth() / 2, getHeight() / 2);
        
		Graphics2D g = (Graphics2D) G;
		g.drawImage(shipIMG,at,null);
	}
	
}
