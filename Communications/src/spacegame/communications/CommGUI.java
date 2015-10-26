package spacegame.communications;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import java.awt.Graphics;



import spacegame.client.*;
import spacegame.gui.*;

public class CommGUI extends JPanel implements Runnable {


    protected JButton button;
    private static CommGame game;
    private static Client client;
    private MouseListener m;
  //  public JPanel panel = new JPanel();
    private boolean buttonStatus = false;
    private boolean buttonUpdated = true;
    public JFrame windowFrame;
    private HeadingDial headingDial = new HeadingDial();
    public Container contentPane;

    public CommGUI(CommGame game, Client c){
        this.game=game;
        client=c;


    
        
        windowFrame = new JFrame();
        this.client = c;


        //gui test
//        JButton cancelButton = new JButton("cancel");
//        JButton setButton = new JButton("set");        		
//        JScrollPane listScroller = new JScrollPane();
//        listScroller.setPreferredSize(new Dimension(250, 80));
//        listScroller.setAlignmentX(LEFT_ALIGNMENT);
//        JPanel listPane = new JPanel();
//        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
//        JLabel label = new JLabel("sup bruh");
//        listPane.add(label);
//        listPane.add(Box.createRigidArea(new Dimension(0,5)));
//        listPane.add(listScroller);
//        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        	JPanel buttonPane = new JPanel();
        	buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        	buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        	buttonPane.add(Box.createHorizontalGlue());
//        buttonPane.add(cancelButton);
//        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
//        buttonPane.add(setButton);
        
       

       
        headingDial.setRadius(100);
        
        
        contentPane = windowFrame.getContentPane();
  //      contentPane.add(listPane, BorderLayout.CENTER);
        
        contentPane.add(headingDial, BorderLayout.EAST);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);
        contentPane.addMouseListener(mouse);
        
        
        
        
        windowFrame.setVisible(true);
		windowFrame.setPreferredSize(new Dimension(1000,700));
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
        windowFrame.pack();
     
   
      
}		
    private boolean mouseClick = false;
	private MouseAdapter mouse = new MouseAdapter()
    {
        public void mousePressed(MouseEvent e)
        {
        	System.out.println("click");
        	mouseClick = true;
        }
        public void mouseReleased(MouseEvent e)
        {
        	System.out.println("unclick");
        	mouseClick = false;
        }
    };
    
    public void run()
    {
    	System.out.println("game running1");
    	for(double i=0;true;)
    	{
    		System.out.println("game running2");
    		if(mouseClick)
    		{
    			i+=.0001;
    			headingDial.setRadius((int)i+1);
    			System.out.println("test");
            	headingDial.setHeading(i);
            	windowFrame.repaint();
            	
    		}
    		
    		windowFrame.repaint();
    	}
    	
    }
}
