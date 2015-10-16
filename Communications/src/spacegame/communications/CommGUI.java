package spacegame.communications;
import java.awt.*;
//import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
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
        
        
        windowFrame.setVisible(true);
        windowFrame.setSize(600, 600);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        repaint();
//       button = new JButton();
//      button.setText("Test Button");
//       button.addMouseListener(mouse);
//       button.setVisible(true);
//       panel.add(button);
//       windowwindowFrame.setResizable(false);
//       windowwindowFrame.setTitle("Communcations");
//       windowwindowFrame.setDefaultCloseOperation(JwindowFrame.EXIT_ON_CLOSE);
//       windowwindowFrame.setResizable(true);
//       windowwindowFrame.setVisible(true);
//	    windowwindowFrame.setSize(600,600);
//	    JPanel panel = new JPanel(new BorderLayout());
//	    JPanel panel2 = new JPanel(new BorderLayout());
//	    windowwindowFrame.add(panel);
//	    //windowwindowFrame.pack();
//	    
//	    
//	    
//	    
	/*	windowwindowFrame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    c.sendMessage("exit");
			  }
			});
		windowwindowFrame.add(windowPanel);
		windowwindowFrame.pack();
    	*/
}		
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);

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
