package threads;

import model.MessageWithAccountQueue;
import model.SocketListenerWithAccount;
import tools.GamerAccount;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LoginAtServerListener extends Thread{
    ServerSocket serverSocket;
    private MessageWithAccountQueue messageWithAccountQueue;
    ServerListenerWithQueue threadlistenernew;
    public ArrayList<SocketListenerWithAccount> socketListenerWithAccounts = new ArrayList<>();
    public LoginAtServerListener(ServerSocket serverSocket, MessageWithAccountQueue mes){
        this.serverSocket = serverSocket;
        this.messageWithAccountQueue=mes;
    }
    @Override
    public void run() {
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                GamerAccount gamerAccount = new GamerAccount();
                threadlistenernew = new ServerListenerWithQueue(socket, gamerAccount, messageWithAccountQueue);
                threadlistenernew.start();
                socketListenerWithAccounts.add(new SocketListenerWithAccount(threadlistenernew, gamerAccount));
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
