package mapgui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.swing.JPanel;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.GameMap;
import spacegame.map.components.PhysicsComponent;
import spacegame.map.components.PositionComponent;
import spacegame.render.ImageLoader;

public class MapViewPanel extends JPanel
{
	private GameMap map;
	private BufferedImage shipIMG;
	private final String SHIP_NAME;
	private AffineTransform at;
	private ImageLoader loader;
	private Random rand;
	private Star[] starList;
	
	public MapViewPanel(GameMap m, String shipname)
	{
		starList = new Star[1000];
		rand = new Random();
		map = m;
		SHIP_NAME = shipname;
		loader = new ImageLoader();
		try {
			loader.loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		shipIMG = loader.getImage(map.getEntityByName(SHIP_NAME).getComponent("Render").getVariable("imagePath"));
		
		for(int x = 0; x < starList.length; x++)
		{
			starList[x] = new Star(rand.nextInt(1600),rand.nextInt(900),2+rand.nextInt(5),new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
			System.out.println("Star " + (x+1));
		}
	}
	
	public void paintComponent(Graphics G) 
	{
		Graphics2D g = (Graphics2D) G;
		g.setBackground(Color.BLACK);
		g.clearRect(0,0, getWidth(), getHeight());
		PhysicsComponent physShip = (PhysicsComponent)map.getEntityByName(SHIP_NAME).getComponent("Physics");
		
		int x, y;
		for(Star s: starList)
		{
			g.setColor(s.color);
			g.fillOval(s.x , s.y,s.rad,s.rad);
			g.fillOval((int)Math.round(s.x + (physShip.getDouble("velocityX")/10)),(int)Math.round(s.y + (physShip.getDouble("velocityY")/10)), s.rad, s.rad);
		}
		
		at = new AffineTransform();
		int cx = getWidth()/2 - shipIMG.getWidth() / 2, cy = getHeight()/2 - shipIMG.getHeight() / 2;
		at.translate(getWidth()/2 - shipIMG.getWidth() / 2,getHeight()/2 - shipIMG.getHeight() / 2);
		at.translate(shipIMG.getHeight() / 2,shipIMG.getWidth() / 2);
        at.rotate(Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading")));
        at.translate(-shipIMG.getHeight() / 2,-shipIMG.getWidth() / 2);
        
        PositionComponent posShip = (PositionComponent)map.getEntityByName(SHIP_NAME).getComponent("Position");
        int sx = (int)Math.round(posShip.getDouble("posX")), sy = (int)Math.round(posShip.getDouble("posY"));
		BufferedImage image;
		
		for(Entity e: map.getEntities()){
			if(e.hasComponent(EntityFactory.POSITION)&&e.hasComponent(EntityFactory.RENDER)&&!e.getName().equals(SHIP_NAME)){
				image = loader.getImage(e.getComponent(EntityFactory.RENDER).getVariable("imagePath"));
				PositionComponent pos = (PositionComponent)e.getComponent(EntityFactory.POSITION);
				x = (int)Math.round(pos.getDouble("posX"))-sx+cx;
				y = (int)Math.round(pos.getDouble("posY"))-sy+cy;
				if(x>(0-image.getWidth())&&y>(0-image.getHeight())&&x<getWidth()&&y<getHeight())
				{
					if(e.hasComponent(EntityFactory.HEADING))
					{
						AffineTransform transformer = new AffineTransform();
						transformer.translate(x,y);
						transformer.translate(image.getWidth()/2, image.getHeight()/2);
						transformer.rotate(e.getComponent("Heading").getDouble("heading"));
						transformer.translate(-image.getWidth()/2, -image.getHeight()/2);
						g.drawImage(image,transformer,null);
					}
					else
					{
						g.drawImage(image, x, y, null);
					}
				}				
			}
		}
		g.drawImage(shipIMG, at, null);
	}	
}
class Star
{
	public final int x,y,rad;
	Color color;
	public Star(int xPos, int yPos, int Radius, Color c)
	{
		x = xPos;
		y = yPos;
		rad = Radius;
		color = c;
	}
}
