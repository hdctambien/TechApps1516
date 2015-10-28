package spacegame.communications;
import spacegame.client.*;
/**
 * Created by Avery on 9/28/2010.;
 */
import spacegame.map.GameMap;
import spacegame.mapgui.MapPanel;

import java.io.IOException;
import java.awt.Graphics;
import spacegame.client.*;


public class CommGame implements Runnable
{
    Client client;
    CommProtocol protocol;

    String iaddress;// = "192.168.1.89";
    int port;// = 8080;

    //JButton exit;
    //JButton get;
    private Thread protocolThread;
    private Thread clientThread;
    private MapPanel map = new MapPanel(new GameMap());//import from client
    boolean running = false;
    CommGUI gui;
    private Client c;



    public CommGame(String address, int p, String name)
    {
        iaddress=address;
        port=p;

        
        gui = new CommGUI(this, this.client, map);
        Thread guiThread = new Thread(gui);
        guiThread.start();
       /* try{
            client = new Client(iaddress,port);
            clientThread = new Thread(client);
            clientThread.start();

        }catch (IOException e){
            e.printStackTrace();
        }*/
        protocol = new CommProtocol(client, this, gui);
        //protocolThread = new Thread(protocol);
        protocolThread.start();
      
        Thread gameThread = new Thread(this);
        gameThread.start();
        

    }
    public void run()
    {
        running=true;
        
        System.out.println("sdfs");
    }

}
