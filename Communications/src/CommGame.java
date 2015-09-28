/**
 * Created by Avery on 9/28/2015.
 */
import java.io.IOException;

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

    boolean running = false;
    CommGUI gui;
    private Client c;

    public static void main(String args[])
    {
        CommGame game = new CommGame("", 2, "");

    }

    public CommGame(String address, int p, String name)
    {
        iaddress=address;
        port=p;


        gui = new CommGUI(this, this.client);
        Thread guiThread = new Thread(gui);
        guiThread.start();
        try{
            client = new Client(iaddress,port);
            clientThread = new Thread(client);
            clientThread.start();

        }catch (IOException e){
            e.printStackTrace();
        }
        protocol = new CommProtocol(client, this, gui);
        protocolThread = new Thread(protocol);
        protocolThread.start();
        run();
        //gui.close();

    }
    public void run()
    {
        running=true;
    }

}
