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
	private BufferedImage gunIMG;
	private BufferedImage laserIMG;
	private final String SHIP_NAME;
	private AffineTransform at;
	private AffineTransform at2;
	private AffineTransform at3;
	private ImageLoader loader;
	private Random rand;
	private Star[] starList;
	private Star[] backgroundStars;
	
	private final boolean DYNAMIC_STARS_ENABLED = false;
	
	public MapViewPanel(GameMap m, String shipname)
	{
		starList = new Star[1];
		backgroundStars = new Star[5000];
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
		gunIMG = loader.getImage(map.getEntityByName(SHIP_NAME).getComponent("Gun").getVariable("imagePath"));
		laserIMG = loader.getImage("Laser.png");
		if(DYNAMIC_STARS_ENABLED)
			starList = new Star[250];
		
		initStars();
	}
	public void initStars()
	{
		int rInt = 0;
		int rHeight = 0;
		if(DYNAMIC_STARS_ENABLED)
			for(int x = 0; x < starList.length; x++)
			{
				rInt = rand.nextInt(100)+154;
				rHeight = (int)Math.floor((2+rand.nextInt(20))/2);
				starList[x] = new Star(rand.nextInt(1600), rand.nextInt(900),rHeight*5,rHeight,new Color(rInt,rInt,rand.nextInt(255),rand.nextInt(100)));
			}
		for(int x = 0; x < backgroundStars.length; x++)
		{
			rInt = rand.nextInt(100)+154;
			rHeight = (int)Math.floor((2+rand.nextInt(10))/2);
			backgroundStars[x] = new Star(rand.nextInt(1600), rand.nextInt(900),rHeight,rHeight,new Color(rInt,rInt,rand.nextInt(255),rand.nextInt(100)+154));
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
		if(DYNAMIC_STARS_ENABLED)
			for(Star s: starList)
			{
				s.redrawStar(physShip.getDouble("velocityX"),physShip.getDouble("velocityY"));
				g.drawImage(s.image, s.at, null);
			}
		for(Star s: backgroundStars)
		{
			s.moveStar(physShip.getDouble("velocityX"),physShip.getDouble("velocityY"));
			g.drawImage(s.image, s.at, null);
		}
		
		at = new AffineTransform();
		at2 = new AffineTransform();
		int cx = getWidth()/2 - shipIMG.getWidth() / 2, cy = getHeight()/2 - shipIMG.getHeight() / 2;
		at.translate(getWidth()/2 - shipIMG.getWidth() / 2,getHeight()/2 - shipIMG.getHeight() / 2);
		at.translate(shipIMG.getHeight() / 2,shipIMG.getWidth() / 2);
        at.rotate(Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"))- Math.PI/2);
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
		at2.translate(getWidth()/2 - shipIMG.getWidth() / 2,getHeight()/2 - shipIMG.getHeight() / 2);
		at2.translate(shipIMG.getHeight() / 2,shipIMG.getWidth() / 2);
        at2.rotate(map.getEntityByName(SHIP_NAME).getComponent("Gun").getDouble("gunHeading"));
        at2.translate(-shipIMG.getHeight() / 2,-shipIMG.getWidth() / 2);
        
        if(Boolean.parseBoolean(map.getEntityByName(SHIP_NAME).getComponent("Gun").getVariable("shoot")) == true)
		{
			at3 = new AffineTransform();
			at3.translate(getWidth()/2 - laserIMG.getWidth()/ 2,getHeight()/2 - laserIMG.getHeight() / 2);
			at3.translate(laserIMG.getWidth() / 2,laserIMG.getHeight() / 2);
	        at3.rotate(map.getEntityByName(SHIP_NAME).getComponent("Gun").getDouble("gunHeading") + Math.PI);
	        at3.translate(-laserIMG.getWidth() / 2,-laserIMG.getHeight() / 2);
	        g.drawImage(laserIMG, at3, null);
		}
        
        g.drawImage(gunIMG, at2, null);
	}	
}
class Star
{
	public BufferedImage image;
	public double x;
	public double y;
	public final int width;
	public final int height;
	public final double moveCoef;
	Color color;
	Graphics2D g;
	AffineTransform at;
	private double lastMillis;
	private volatile double deltaMillis;
	
	private Kernel kernel = new Kernel(3, 3, new float[] { 1 / 9f, 1 / 9f, 1 / 9f,
	        1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f });
	private BufferedImageOp op = new ConvolveOp(kernel);
	
	public Star(int xPos, int yPos, int w, int h, Color c)
	{
		x = xPos;
		y = yPos;
		width = w;
		height = h;
		moveCoef = 0;
		color = c;;
		redrawStar(0,0);
		lastMillis = System.currentTimeMillis();
	}
	public void redrawStar(double xVel, double yVel)
	{
		int velocity = (int) Math.round(Math.abs(Math.sqrt(Math.pow(xVel,2)+Math.pow(yVel,2))));
		image = new BufferedImage((int)(width/50*velocity)+height, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		g = image.createGraphics();
		g.setColor(color);
		g.fillPolygon(new int[]{Math.round(height/2),(int) (width/50*velocity)+height,Math.round(height/2),0}, new int[]{0,Math.round(height/2),height,Math.round(height/2)}, 4);
		Kernel kernel = new Kernel(3, 3, new float[] { 1 / 9f, 1 / 9f, 1 / 9f,
		        1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f });
		BufferedImageOp op = new ConvolveOp(kernel);
		image = op.filter(image, null);
		rotateStar(xVel,yVel);
	}
	public void rotateStar(double xVel, double yVel)
	{
		moveStar(xVel,yVel);
		at.translate(height/2,height/2);
        at.rotate(Math.atan2(yVel,xVel)-Math.PI);
        at.translate(-height/2,-height/2);
	}
	public void moveStar(double xVel, double yVel)
	{
        if(x < -15)
        	x += 1615;
        if(x > 1600)
        	x -= 1600;
        if(y < -15)
        	y += 915;
        if(y > 900)
        	y -= 900;
        deltaMillis = (System.currentTimeMillis() - lastMillis)/1000;
        x-=(xVel*deltaMillis)/height;
		y-=(yVel*deltaMillis)/height;
		lastMillis = System.currentTimeMillis();
        at = new AffineTransform();
        at.translate(x, y);
	}
}

