package spacegame.communications;

import java.net.InetAddress;

import spacegame.client.*;


public class CommProtocol extends AbstractProtocol
{
    private InetAddress iaddress;
    private int port;
    private CommGame eGame;
    private CommGUI gui;
    private Client client;
    private Thread tClient;
    private Thread tP;

    public CommProtocol(Client client, CommGame cGame, CommGUI gui)
    {
        super(client);
        tClient = new Thread(client);
        tClient.start();
        tP = new Thread(this);
        tP.start();
        iaddress = client.getAddress();
        port = client.getPort();
        this.eGame = cGame;
        this.gui = gui;
        this.client= client;
        client.sendMessage("set job Comm");
    }

    public void process(String stringIn)
    {
        //eGame.processTextMessage(stringIn);
        System.out.println(stringIn);
      //  switch(stringIn)
    //    {
//            	case "set buttonStatus false": gui.setColor("red");break;
//            	case "set buttonStatus true" : gui.setColor("green");break;
//        }
    }
}