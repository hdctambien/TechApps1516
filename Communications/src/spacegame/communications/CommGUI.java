package spacegame.communications;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import spacegame.client.*;
/* 
 * ButtonDemo.java requires the following files:
 *   images/right.gif
 *   images/middle.gif
 *   images/left.gif
 */
public class CommGUI extends Thread implements Runnable {

    protected JButton button;
    private static CommGame game;
    private static Client client;
    private MouseListener m;
    public JPanel panel = new JPanel();
    private boolean buttonStatus = false;
    private boolean buttonUpdated = true;

    public CommGUI(CommGame g, Client c)     {
        game=g;
        client=c;

        JFrame windowFrame;
//        createAndShowGUI();

        windowFrame = new JFrame();
        panel.setVisible(true);
        this.client = c;




        button = new JButton();
        button.setText("Test Button");
        button.addMouseListener(mouse);
        button.setVisible(true);
        panel.add(button);
        windowFrame.setResizable(false);
        windowFrame.setTitle("Communcations");
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(true);
        windowFrame.setVisible(true);
		/*windowFrame.setPreferredSize(new Dimension(1000,700));
		windowFrame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    c.sendMessage("exit");
			  }
			});
		windowFrame.add(windowPanel);
		windowFrame.pack();
    	*/


    }
    private MouseAdapter mouse = new MouseAdapter()
    {
        public void mousePressed(MouseEvent e)
        {
            buttonStatus = true;
            buttonUpdated = false;
            System.out.println("Button pressed");
        }
        public void mouseReleased(MouseEvent e)
        {
            buttonStatus = false;
            buttonUpdated = false;
            System.out.println("Button released");
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
                System.out.println(buttonStatus);
            }
        }
    }
   /* private static void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("ButtonDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        CommGUI newContentPane = new CommGUI(game, client);
       /* newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);//

        //Display the window.
        frame.pack();
        frame.setSize(600,600);
        frame.setVisible(true);

    }*/

}
