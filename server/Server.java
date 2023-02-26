package server;



import model.MessageWithAccount;
import model.MessageWithAccountQueue;
import threads.ServerThread;
import SocketPackage.SocketSender;
import model.GameMap;
import threads.LoginAtServerListener;
import tools.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void connect(GamerAccount gamerAccount) throws IOException{
        Socket socket = new Socket("localhost", gamerAccount.port);
        SocketSender sellersender = new SocketSender(socket);
        gamerAccount.socketSender=sellersender;
        gamerAccount.connected=1;
    }

    public static ServerSocket scanporttoopen(int startport){
        while(1==1) {
            try {
                ServerSocket serverSocket = new ServerSocket(startport);
                return serverSocket;
            } catch (IOException e) {
                startport++;
            }
        }
    }

    public static void serverGamer(String message, GameMap gameMap, GamerAccount gamerAccount, Long currenttime, GamerMenager gamerMenager){
        if (message.compareTo("see") == 0) {
            String response = gameMap.explore(gamerAccount.possition, gamerMenager);
            gamerAccount.socketSender.sendMessage("see;" + gamerAccount.possition.column + ";" + gamerAccount.possition.row + ";"  + response);
        }
        if (message.compareTo("score") == 0) {
            gamerAccount.socketSender.sendMessage("score;" +gamerMenager.scores());
        }
        String movetab[] = message.split(";");
        if (movetab[0].compareTo("move") == 0) {
            if(currenttime> gamerAccount.blockedto) {
                int column = Integer.parseInt(movetab[1]);
                int row = Integer.parseInt(movetab[2]);
                for(String s: gamerMenager.LoginBase.keySet()){
                    GamerAccount gamerAccounttemp = gamerMenager.LoginBase.get(s);
                    if(column==gamerAccounttemp.possition.column&&row==gamerAccounttemp.possition.row){
                        if(gamerAccounttemp!=gamerAccount)
                            return;
                    }

                }
                if (gameMap.getElement(column, row) == 'S') {
                    System.out.println("$"+column+"$"+row);
                    gameMap.putElemenet(column,row,' ');
                    gamerAccount.blockedto = currenttime + gameMap.getTreassuresAtPossition(column,row).weight*1000;

                    gamerAccount.score+=gameMap.getTreassuresAtPossition(column,row).value;
                    gamerAccount.possition.setPossition(column, row);
                    return;
                }

                if (gameMap.getElement(column, row) == ' ') {
                    gamerAccount.possition.setPossition(column, row);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        MessageWithAccountQueue messageWithAccountQueue = new MessageWithAccountQueue();
        MessageWithAccountQueue messageToLoginQueue = new MessageWithAccountQueue();
        GameMap gameMap = new GameMap();
        ServerSocket serverSocket = new ServerSocket(1000);
        GamerMenager gamerMenager =new GamerMenager();
        ServerController serverController = new ServerController(gameMap, gamerMenager);
        LoginAtServerListener loginThread = new LoginAtServerListener(serverSocket, messageToLoginQueue);
        loginThread.start();
        long starttime=System.currentTimeMillis();
        while(1==1) {
            long time=System.currentTimeMillis();
            Long currenttime = (time - starttime);
            while (messageToLoginQueue.numberofmessages()> 0) {
                MessageWithAccount messageWithAccount = messageToLoginQueue.getAndRemove();
                if(messageWithAccount!=null) {
                    String message = messageWithAccount.message;
                    GamerAccount gamerAccount = messageWithAccount.gamerAccount;

                    if (gamerMenager.checkcoreccness(message)== true) {
                        if (gamerAccount.connected == 0) {
                            gamerAccount.parse(message);
                            gamerAccount.blockedto=15000;
                            connect(gamerAccount);
                            ServerSocket serverSocketx = scanporttoopen(1000);
                            int porttosend=serverSocketx.getLocalPort();
                            gamerAccount.serverThreadnew = new ServerThread(serverSocketx, porttosend, gamerAccount, messageWithAccountQueue);
                            gamerAccount.serverThreadnew.start();
                            gamerAccount.possition = gameMap.putgamer();
                            gamerAccount.connected = 1;
                            gamerAccount.socketSender.sendMessage("username;" + gamerAccount.username + ";porttocomunicate;" + porttosend + ";width;" + gameMap.mapcolumns + ";" + "height;" + gameMap.maprows);
                            gamerMenager.add(gamerAccount);
                        }
                    }
                }
            }
            while(messageWithAccountQueue.numberofmessages()>0){
               MessageWithAccount messageWithAccount = messageWithAccountQueue.getAndRemove();
                if (messageWithAccount != null) {
                    serverGamer(messageWithAccount.message, gameMap, messageWithAccount.gamerAccount, currenttime, gamerMenager);
                }
            }
        }
    }
}

