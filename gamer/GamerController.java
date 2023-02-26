package gamer;

import SocketPackage.SocketSender;
import model.GameMap;
import model.Possition;
import model.Treassure;
import threads.GamerThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class GamerController {

    GamerFrame gamerFrame;
    int gamerPort;
    String username;
    Timer timer;
    String gamerHost;
    int serverPort;
    String serverHost;
    ServerSocket serverSocket = null;
    SocketSender socketsender=null;
    GamerThread gamerThread;
    int counter = 0;

    GameMap map=new GameMap();
    int state=0;// 0 - before opening post; 1 - before login, 2 - before start, 3 - after start
    public GamerController() throws FileNotFoundException {
       gamerFrame = new GamerFrame(map);
       map.treasures.clear();
       gamerFrame.openPort(new OpenPortListener());
       gamerFrame.login(new LoginListener());
       gamerFrame.start(new StartListener());
       timer = new Timer(100, new TimerListener());
       timer.start();


    }
    class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            counter++;
            if(counter%100==0){
                if(map!=null)
                    map.gamerCleaner();
            }
            if(state==3) {
                gamerFrame.repaint();
                socketsender.sendMessage("see");
                if(gamerFrame.visualisationPanel.gamerPossition.column!=-1&&gamerFrame.visualisationPanel.gamerPossition.row!=-1){
                    map.shortestpath(gamerFrame.visualisationPanel.gamerPossition);
                   // Possition localisation = map.findnearest(gamerFrame.visualisationPanel.gamerPossition, 'S');
                    Possition localisation = map.findnetreassures(gamerFrame.visualisationPanel.gamerPossition);

                    if(localisation==null) {
                       localisation = map.findnearest(gamerFrame.visualisationPanel.gamerPossition, 'U');
                    }
                    if(localisation!=null) {
                        socketsender.sendMessage("move;" + localisation.column + ";" + localisation.row);
                    }else{
                        socketsender.sendMessage("score");
                    }
                }
                if (gamerThread.threadlistener != null) {
                    if (gamerThread.messageQueue.numberofmessages() > 0) {
                        String message = gamerThread.messageQueue.getAndRemove();
                        if(message!=null){
                            System.out.println("response form command " + message);
                            if(message!=null){
                                String[] messagetylda = message.split("~");
                                String[] messagetab = messagetylda[0].split(";");
                                if(messagetab[0].compareTo("see")==0) {
                                    int column = Integer.valueOf(messagetab[1]);
                                    int row = Integer.valueOf(messagetab[2]);
                                    map.lightmap(column,row,messagetab[3]);
                                    gamerFrame.visualisationPanel.gamerPossition=new Possition(column,row);

                                    if(messagetylda.length>1){
                                        for(int el=1; el<=messagetylda.length-1;el++){
                                            String[] treasuresinfo = messagetylda[el].split(";");
                                            if(treasuresinfo[0].compareTo("treassure")!=0) continue;
                                            int col = Integer.valueOf(treasuresinfo[1]);
                                            int ro = Integer.valueOf(treasuresinfo[2]);
                                            int weight = Integer.valueOf(treasuresinfo[3]);
                                            int value = Integer.valueOf(treasuresinfo[4]);
                                            map.addTreassure(new Treassure(new Possition(col,ro),weight,value));
                                        }
                                    }
                                }
                                if(messagetab[0].compareTo("score")==0){
                                    gamerFrame.comunicates.setText(message);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    class OpenPortListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(state==1) {gamerFrame.comunicates.setText("WARNING! Port is already open"); return;}
            gamerPort=Integer.valueOf(gamerFrame.clientPort.getText());

            try {
                serverSocket = new ServerSocket(gamerPort);
                state=1;
            } catch (IOException ex) {
                gamerFrame.comunicates.setText("WARNING! Error in opening port, check if port is not used or try another one");
            }
        }
    }

    class LoginListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(state<1) {gamerFrame.comunicates.setText("WARNING! Open your port");return;}
            serverHost=gamerFrame.serverHost.getText();
            serverPort =Integer.valueOf( gamerFrame.serverPort.getText());
            gamerHost = gamerFrame.clientHost.getText();
            username = gamerFrame.username.getText();
            gamerFrame.comunicates.setText("");
            try {
                Socket socket = new Socket(serverHost, serverPort);
                SocketSender sellersender = new SocketSender(socket);
                sellersender.sendMessage("username;"+username+";"+"port;"+gamerPort+";"+"host;"+gamerHost);
            } catch (IOException ex) {
                gamerFrame.comunicates.setText("WARNING! Problem with connection");
                return;
            }
            gamerThread = new GamerThread(serverSocket,100);
            gamerThread.start();
            int loginsucces=0;
            while(loginsucces==0) {
                if (gamerThread.threadlistener != null) {
                    if (gamerThread.messageQueue.numberofmessages() > 0) {
                        String message = gamerThread.messageQueue.getAndRemove();
                        if(message!=null){

                            loginsucces=1;
                            String[] messageparse = message.split(";");
                            serverPort=Integer.valueOf(messageparse[3]);
                            int maprows =Integer.valueOf(messageparse[7]);
                            int mapcolumns =Integer.valueOf(messageparse[5]);
                            map.emptyMap(mapcolumns,maprows);
                        }
                    }
                }
                try {
                    sleep(10);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                Socket socket = new Socket(serverHost, serverPort);
                socketsender = new SocketSender(socket);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            state=2;
        }
    }

    class StartListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(state<2){ gamerFrame.comunicates.setText("WARNING! First you have to login");return;}
            gamerFrame.comunicates.setText("");state=3;
        }
    }
}


