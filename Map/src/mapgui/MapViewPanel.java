package mapgui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
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
	private Star[] backgroundStars;
	
	public MapViewPanel(GameMap m, String shipname)
	{
		starList = new Star[25];
		backgroundStars = new Star[500];
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
		
		int rInt = 0;
		int rHeight = 0;
		for(int x = 0; x < starList.length; x++)
		{
			rInt = rand.nextInt(100)+154;
			rHeight = (int)Math.floor((10+rand.nextInt(20))/2);
			starList[x] = new Star(rand.nextInt(1600),rand.nextInt(900),rHeight*5,rHeight,new Color(rInt,rInt,rand.nextInt(255),rand.nextInt(100)+154));
		}
		for(int x = 0; x < backgroundStars.length; x++)
		{
			rInt = rand.nextInt(100)+154;
			rHeight = (int)Math.floor((4+rand.nextInt(10))/2);
			backgroundStars[x] = new Star(rand.nextInt(1600),rand.nextInt(900),rHeight,rHeight,new Color(rInt,rInt,rand.nextInt(255),rand.nextInt(100)+154));
		}
		
	}
	
	public void paintComponent(Graphics G) 
	{
		
		Graphics2D g = (Graphics2D) G;
		g.setBackground(Color.BLACK);
		
		g.clearRect(0,0, getWidth(), getHeight());
		PhysicsComponent physShip = (PhysicsComponent)map.getEntityByName(SHIP_NAME).getComponent("Physics");
		PositionComponent posShip = (PositionComponent)map.getEntityByName(SHIP_NAME).getComponent("Position");
		
		int x, y;
		double velocity = Math.sqrt(Math.abs(physShip.getDouble("velocityX")) + Math.abs(physShip.getDouble("velocityY")));
		for(Star s: starList)
		{
			s.recalcStar(Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading")),velocity);
			g.drawImage(s.image, s.at, null);
		}
		for(Star s: backgroundStars)
		{
			g.drawImage(s.image, s.x,s.y, null);
		}
		
		at = new AffineTransform();
		int cx = getWidth()/2 - shipIMG.getWidth() / 2, cy = getHeight()/2 - shipIMG.getHeight() / 2;
		at.translate(getWidth()/2 - shipIMG.getWidth() / 2,getHeight()/2 - shipIMG.getHeight() / 2);
		at.translate(shipIMG.getHeight() / 2,shipIMG.getWidth() / 2);
        at.rotate(Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading")));
        at.translate(-shipIMG.getHeight() / 2,-shipIMG.getWidth() / 2);
        

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
	public BufferedImage image;
	public final int x,y,width,height;
	Color color;
	Graphics2D g;
	AffineTransform at;
	
	private Kernel kernel = new Kernel(3, 3, new float[] { 1 / 9f, 1 / 9f, 1 / 9f,
	        1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f });
	private BufferedImageOp op = new ConvolveOp(kernel);
	
	public Star(int xPos, int yPos, int w, int h, Color c)
	{
		x = xPos;
		y = yPos;
		width = w;
		height = h;
		color = c;;
		recalcStar(0,0);
	}
	public void recalcStar(double heading, double velocity)
	{
		velocity = Math.abs(velocity);
		image = new BufferedImage((int)(width/10*velocity)+height, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		g = image.createGraphics();
		g.setColor(color);
		g.fillOval(0, 0, height, height);		
		if(velocity != 0)
		{
			g.fillPolygon(new int[]{Math.round(height/2),(int) (width*velocity),Math.round(height/2)}, new int[]{0,Math.round(height/2),height}, 3);
		}
		Kernel kernel = new Kernel(3, 3, new float[] { 1 / 9f, 1 / 9f, 1 / 9f,
		        1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f });
		BufferedImageOp op = new ConvolveOp(kernel);
		image = op.filter(image, null);
		if(heading != 0)
		{
			at = new AffineTransform();
	        at.translate(x, y);
			at.translate(height/2,height/2);
	        at.rotate(heading);
	        at.translate(-height/2,-height/2);
		}
	}
}

