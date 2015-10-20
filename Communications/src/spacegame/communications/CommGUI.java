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

import spacegame.client.*;

public class CommGUI extends Thread implements Runnable {


    protected JButton button;
    private static CommGame game;
    private static Client client;
    private MouseListener m;
    public JPanel panel = new JPanel();
    private boolean buttonStatus = false;
    private boolean buttonUpdated = true;
    public JFrame windowFrame;

    public CommGUI(CommGame g, Client c)     {
        game=g;
        client=c;


    
        
        windowFrame = new JFrame();
        panel.setVisible(true);
        this.client = c;

        JPanel panel = new JPanel(new GridLayout(1,2)); // split the panel in 1 rows and 2 cols


        JTextArea userField = new JTextArea("User:");
        userField.setEditable(false);

        JTextArea user = new JTextArea("myuser");
        user.setEditable(true);

        panel.add(userField);
        panel.add(user);

        windowFrame.getContentPane().add(panel);

        windowFrame.setVisible(true);
        windowFrame.setSize(600, 600);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        
        
        
        
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
//		JSlider pComms = new JSlider();
//		JSlider pComms2 = new JSlider();
//		panel.add(pComms, BorderLayout.CENTER);
//		panel.add(pComms2, BorderLayout.LINE_START);
		
	    
	    
	    
	    
	    
	/*	windowwindowFrame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    c.sendMessage("exit");
			  }
			});
		windowwindowFrame.add(windowPanel);
		windowwindowFrame.pack();
    	*/
		


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
   /* private static void createAndShowGUI() {

        //Create and set up the window.
        JwindowFrame windowFrame = new JwindowFrame("ButtonDemo");
        windowFrame.setDefaultCloseOperation(JwindowFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        CommGUI newContentPane = new CommGUI(game, client);
       /* newContentPane.setOpaque(true); //content panes must be opaque
        windowFrame.setContentPane(newContentPane);//

        //Display the window.
        windowFrame.pack();
        windowFrame.setSize(600,600);
        windowFrame.setVisible(true);

    }*/

}
