package spacegame.communications;
    
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
import java.util.ArrayList;

import javax.swing.JPanel;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.GameMap;
import spacegame.map.components.PositionComponent;
import spacegame.render.ImageLoader;

import javax.swing.JPanel;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.GameMap;
import spacegame.map.components.PhysicsComponent;
import spacegame.map.components.PositionComponent;
import spacegame.render.ImageLoader;

    public class CommViewPanel extends JPanel
    {
    	private GameMap map;
    	private BufferedImage shipIMG;
    	private BufferedImage gunIMG;
    	private final String SHIP_NAME;
    	private AffineTransform at;
    	private AffineTransform at2;
    	private ImageLoader loader;
    	
    	private int mouseX;
    	private int mouseY;
    	boolean marked = false;    	
    	private String scan="";
    	
    	ArrayList<int[]> highlights = new ArrayList<int[]>();
    
    	public CommViewPanel(GameMap m, String shipname)
    	{
    		addMouseListener(mouse);
    		addMouseMotionListener(mouse);
    		
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
    	}

    	//MouseEvent event;
    	private MouseAdapter mouse = new MouseAdapter()
        {
    		public void mousePressed(MouseEvent e)
    	    {
    	        	mouseX=e.getX();
    	        	mouseY=e.getY();
    	        	
    	        //	marked=true;
    	        //	System.out.println("click");
    	    }		
    	        
    		public void mouseMoved(MouseEvent e)
            {
            	mouseX=e.getX();
            	mouseY=e.getY();
            	for(Entity ent: map.getEntities()){//highlight something on the map
	        		PositionComponent pos = (PositionComponent)ent.getComponent(EntityFactory.POSITION);
					int x = (int)Math.round(pos.getDouble("posX"));
					int y = (int)Math.round(pos.getDouble("posY"));
					x+=getWidth()/2;
					y+=getHeight()/2;
					highlights.add(new int[]{x,y});
					System.out.println("x: "+x+" y: "+y);
					BufferedImage image;
					//image = loader.getImage(e.getComponent(EntityFactory.RENDER).getVariable("imagePath"));
					if(mouseX<=x+20&&mouseX>=x-20&&mouseY<=y+20&&mouseY>=y-20)//width, height
					{
						marked=true;
						System.out.println("marked");
						System.out.println(ent.getName());
						scan=ent.getName();
					}
					else
					{
						marked=false;
					}
	        	}
            }
         };
        
    	public void paintComponent(Graphics G) 
    	{
    		Graphics2D g = (Graphics2D) G;
    		g.setBackground(Color.BLACK);
    		g.clearRect(0,0, getWidth(), getHeight());
    		for(int x=0;x<getWidth();x+=20)//gridlines
    		{	g.drawLine(x, 0, x, getHeight());}
    		for(int y=0;y<getWidth();y+=20)
    		{	g.drawLine(0, y, getWidth(), y);}
    		

    		PhysicsComponent physShip = (PhysicsComponent)map.getEntityByName(SHIP_NAME).getComponent("Physics");
    		PositionComponent posShip = (PositionComponent)map.getEntityByName(SHIP_NAME).getComponent("Position");
    		int x,y;
    		
    		at = new AffineTransform();
    		at2 = new AffineTransform();

    		at.translate(getWidth()/2 - shipIMG.getWidth() / 2,getHeight()/2 - shipIMG.getHeight() / 2);
    		at.translate(shipIMG.getHeight() / 2,shipIMG.getWidth() / 2);
            at.rotate(Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading")));
            at.translate(-shipIMG.getHeight() / 2,-shipIMG.getWidth() / 2);
    		
            int cx = getWidth()/2 - shipIMG.getWidth() / 2, cy = getHeight()/2 - shipIMG.getHeight() / 2;
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
    		g.drawImage(gunIMG, at2, null);
    		
    		
    		g.fillOval(mouseX-5, mouseY-5, 10, 10);
			int centerX = getWidth()/2;
			int centerY = getHeight()/2;
			//int radiusSlope=(mouseY-centerY)/(mouseX-centerX);
			//int perpSlope=-1/radiusSlope;
			//int[] point = {mouseX+(1/perpSlope),mouseY+(perpSlope*radiusSlope)};
			//int[] point2 = {mouseX-(1/perpSlope),mouseY-(perpSlope*radiusSlope)};
			g.setColor(new Color(30,138,49,100));//transparency
			
			//g.fillRect(centerX, centerY, mouseX-centerX, mouseY-centerY);
			//	int radius = (int) Math.sqrt(Math.pow(mouseX-centerX,2)+Math.pow(mouseY-centerY,2)); 
				
			//g.fillPolygon(new int[]{centerX,point[0],point2[0]}, new int[]{centerY,point[1],point2[1]},3);
				
			g.fillPolygon(new int[]{centerX,(int)(mouseX-Math.tan(45)*mouseY),(int) (mouseX+Math.tan(45)*mouseY)}, new int[]{centerY,mouseY,mouseY},3);//x coordinates, y coordinates, number of coordinate pairs
    		
			
				g.setColor(Color.WHITE);
				g.drawString(scan, mouseX+20, mouseY+20);
			for(int i=0;i<highlights.size();i++)//highlight the entities
			{
				g.fillOval(highlights.get(i)[0],highlights.get(i)[1],10,10);
			}
			
    	}	
}
     

	
	

