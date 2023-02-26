package threads;


import model.MessageWithAccountQueue;
import tools.GamerAccount;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    int id=0;
    ServerSocket serverSocket;
    public MessageWithAccountQueue messageWithAccountQueue;
    GamerAccount gamerAccount;
    public ServerThread(ServerSocket serverSocket, int id, GamerAccount gamerAccount, MessageWithAccountQueue messageWithAccountQueue){
        this.serverSocket = serverSocket;
        this.id=id;
        this.gamerAccount=gamerAccount;
        this.messageWithAccountQueue=messageWithAccountQueue;
    }
    @Override
    public void run() {
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ServerListenerWithQueue threadlistenernew = new ServerListenerWithQueue(socket, gamerAccount, messageWithAccountQueue);
                threadlistenernew.start();
            }
        }catch (Exception e){
            closeServerSocket();
        }
    }

    public void closeServerSocket(){
        try{
            if(serverSocket!=null)
                serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
