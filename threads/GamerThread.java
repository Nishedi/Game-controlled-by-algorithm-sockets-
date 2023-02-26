package threads;

import model.MessageQueue;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GamerThread extends Thread{
    int id=0;
    ServerSocket serverSocket;
    public GamerListenerWithQueue threadlistener;
    public MessageQueue messageQueue = new MessageQueue();
    public GamerThread(ServerSocket serverSocket, int id){
        this.serverSocket = serverSocket;
        this.id=id;
    }
    @Override
    public void run() {
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                threadlistener = new GamerListenerWithQueue(socket, messageQueue);
               threadlistener.start();
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
