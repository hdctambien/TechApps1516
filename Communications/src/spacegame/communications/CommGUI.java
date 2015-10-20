package spacegame.communications;
import java.awt.*;
//import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import java.awt.Graphics;


import spacegame.client.*;

public class CommGUI extends JPanel implements Runnable {


    protected JButton button;
    private static CommGame game;
    private static Client client;
    private MouseListener m;
   // public JPanel panel = new JPanel();
    private boolean buttonStatus = false;
    private boolean buttonUpdated = true;
    public JFrame windowFrame;
 

    public CommGUI(CommGame game, Client c){//, Graphics g)     {
        this.game=game;
        client=c;


    
        
        windowFrame = new JFrame();
        //panel.setVisible(true);
        this.client = c;

        JPanel sliderPanel = new JPanel(); // split the panel in 1 rows and 2 cols
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("h");
        sliderPanel.add(label);
        sliderPanel.add(Box.createRigidArea(new Dimension(1,5)));
        sliderPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        
        
        
        JTextArea userField = new JTextArea("User:");
        userField.setEditable(false);

        JTextArea user = new JTextArea("myuser");
        user.setEditable(true);

       // panel.add(userField);
       // panel.add(user);
     
       //panel.add(pComms, BorderLayout.CENTER);
       
        JSlider pComms = new JSlider();
  
        sliderPanel.add(pComms);
        windowFrame.getContentPane().add(sliderPanel);
        
        final Box b = Box.createHorizontalBox();
        
        windowFrame.setVisible(true);
     
        windowFrame.setLayout(null);
		windowFrame.setPreferredSize(new Dimension(1000,700));
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.add(b);
        windowFrame.pack();
      
}		
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.setColor(Color.BLACK);
    	g.fillRect(10, 10, 10, 10);
    }


    private MouseAdapter mouse = new MouseAdapter()
    {
        public void mousePressed(MouseEvent e)
        {
        	windowFrame.add(new JSlider());
        }
        public void mouseReleased(MouseEvent e)
        {
        	
        }
    };
    
    public void run()
    {
        while(game.running)
        {
            if(!buttonUpdated)
            {
                client.sendMessage("set buttonStatus " + buttonStatus);
                buttonUpdated = true;
            }
        }
    }
}
