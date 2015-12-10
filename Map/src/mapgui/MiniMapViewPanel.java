package mapgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.GameMap;
import spacegame.map.components.PositionComponent;
import spacegame.render.ImageLoader;
//import spacegame.client.*;//

public class MiniMapViewPanel extends JPanel
{
	private GameMap map;
	private BufferedImage shipIMG;
	private final String SHIP_NAME;
	private AffineTransform at;
	private AffineTransform scaled;
	private ImageLoader loader;
	private double scaleFactor = .5;
	private int mouseX;
	private int mouseY;
	//int width;
	//int height;
	
	
	public  MiniMapViewPanel(GameMap m, String shipname)
	{
		addMouseListener(mouse);///
		addMouseMotionListener(mouse);///
		map = m;
		SHIP_NAME = shipname;
		loader = new ImageLoader();
		try {
			loader.loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		shipIMG = loader.getImage(map.getEntityByName(SHIP_NAME).getComponent("Render").getVariable("imagePath"));
		
	}

	private boolean marked=false;
	MouseEvent event;
	private MouseAdapter mouse = new MouseAdapter()
    {
        public void mousePressed(MouseEvent e)
        {
        	event=e;
        	mouseX=e.getX();
        	mouseY=e.getY();
        	/*for(Entity ent: map.getEntities()){//highlight something on the map
        		PositionComponent pos = (PositionComponent)ent.getComponent(EntityFactory.POSITION);
				int x = (int)Math.round(pos.getDouble("posX"));
				int y = (int)Math.round(pos.getDouble("posY"));
				System.out.println("x: "+x+" y: "+y);
				BufferedImage image;
			//	image = loader.getImage(e.getComponent(EntityFactory.RENDER).getVariable("imagePath"));
				if(mouseX<=x+50&&mouseX>=x-50&&mouseY<=y+50&&mouseY>=y-50)//width, height
				{
					marked=true;
				}}*/
        	marked=true;
        	System.out.println("click");
        }		
        	
        public void mouseMoved(MouseEvent e)
        {
        	System.out.println("move");
        	marked=true;
        	event =e;
        	mouseX=event.getX();
        	mouseY=event.getY();
        	
        
        }
        public void mouseReleased(MouseEvent e)
        {
        	marked=false;
        }
    };
   
	public void paintComponent(Graphics G) 
	{
		Graphics2D g = (Graphics2D) G;
		at = new AffineTransform();
		//at = AffineTransform.getScaleInstance(scaleFactor,scaleFactor);
		//scaled = new AffineTransform();
		//scaled = AffineTransform.getScaleInstance(scaleFactor,scaleFactor);
	
		g.setBackground(Color.BLACK);
		
		g.clearRect(0,0, getWidth(), getHeight());
		g.setColor(new Color(30,138,49));//green 
	
		for(int x=0;x<getWidth();x+=20)//gridlines
		{	g.drawLine(x, 0, x, getHeight());}
		for(int y=0;y<getWidth();y+=20)
		{	g.drawLine(0, y, getWidth(), y);}

		//int cx = (int) ((int)(getWidth()/2 - shipIMG.getWidth() / 2)*scaleFactor), cy = (int) ((int) (getHeight()/2 - shipIMG.getHeight() / 2)*scaleFactor);
		int cx = getWidth()/2 - shipIMG.getWidth() / 2, cy = getHeight()/2 - shipIMG.getHeight() / 2;
		
		//at.translate(cx , cy);
		at.translate((getWidth()/2 - shipIMG.getWidth() / 2)/scaleFactor , (getHeight()/2 - shipIMG.getHeight() / 2)/scaleFactor);
		at.translate((shipIMG.getHeight() / 2)/scaleFactor , (shipIMG.getWidth() / 2)/scaleFactor);
        at.rotate(Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading")));
        at.translate(-shipIMG.getHeight() / 2,-shipIMG.getWidth() / 2);
        
        PositionComponent posShip = (PositionComponent)map.getEntityByName(SHIP_NAME).getComponent("Position");
        int sx = (int)Math.round(posShip.getDouble("posX")), sy = (int)Math.round(posShip.getDouble("posY"));
        
		BufferedImage image;
		int x, y,scaledx,scaledy;
		int distanceFromCenterx;
		int distanceFromCentery;
		for(Entity e: map.getEntities()){
			if(e.hasComponent(EntityFactory.POSITION)&&e.hasComponent(EntityFactory.RENDER)&&!e.getName().equals(SHIP_NAME)){
				image = loader.getImage(e.getComponent(EntityFactory.RENDER).getVariable("imagePath"));
				PositionComponent pos = (PositionComponent)e.getComponent(EntityFactory.POSITION);
				x = (int)Math.round(pos.getDouble("posX"))-sx+cx;
				y = (int)Math.round(pos.getDouble("posY"))-sy+cy;
				distanceFromCenterx= (int) (x-(getWidth()/2)*scaleFactor);
				distanceFromCentery= (int) (y-(getWidth()/2)*scaleFactor);
				scaledx=distanceFromCenterx+x;
				scaledy=distanceFromCentery+y;
				
				if(x>0 && y>0 && x<getWidth() && y<getHeight())
				{
					if(e.hasComponent(EntityFactory.HEADING))
					{
						AffineTransform transformer = new AffineTransform();
						transformer=AffineTransform.getScaleInstance(scaleFactor,scaleFactor);
						transformer.translate(x,y);
						transformer.translate(image.getWidth()/2, image.getHeight()/2);
						transformer.rotate(e.getComponent("Heading").getDouble("heading"));
						transformer.translate(-image.getWidth()/2, -image.getHeight()/2);
						g.drawImage(image,transformer,null);
						
					}
					else
					{
						scaled.translate(scaledx, scaledy);
						//g.drawImage(image, x, y, null);
						g.drawImage(image, scaled, null);     
					}
				}
				else if(!e.hasComponent(EntityFactory.HEADING))////////////**********~~
				{
					if(x>getWidth())
						scaledx=getWidth();
					if(y>getHeight())
						scaledy=getHeight();
					scaled.translate(scaledx, scaledy);
					
					g.drawImage(image, scaled, null); 
				}/////*****~~~~~
			}
		}
		//g.drawImage(image, x, y, null);
		g.drawImage(shipIMG, at, null);
		if(marked)
		{
		//	g.setColor(Color.BLUE);
			g.fillOval(mouseX-5, mouseY-5, 10, 10);
			int centerX = getWidth()/2;
			int centerY = getHeight()/2;
			int radiusSlope=(mouseY-centerY)/(mouseX-centerX);
			int perpSlope=-1/radiusSlope;
			int[] point = {mouseX+(1/perpSlope),mouseY+(perpSlope*radiusSlope)};
			int[] point2 = {mouseX-(1/perpSlope),mouseY-(perpSlope*radiusSlope)};
			g.setColor(new Color(30,138,49,100));//transparency
		
			//g.fillRect(centerX, centerY, mouseX-centerX, mouseY-centerY);
		//	int radius = (int) Math.sqrt(Math.pow(mouseX-centerX,2)+Math.pow(mouseY-centerY,2)); 
			
			g.fillPolygon(new int[]{centerX,point[0],point2[0]}, new int[]{centerY,point[1],point2[1]},3);
			
			//g.fillPolygon(new int[]{centerX,(int)(mouseX-Math.tan(45)*mouseY),(int) (mouseX+Math.tan(45)*mouseY)}, new int[]{centerY,mouseY,mouseY},3);//x coordinates, y coordinates, number of coordinate pairs
			
			
		}

	}
	
}