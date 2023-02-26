package threads;

import model.MessageWithAccount;
import model.MessageWithAccountQueue;
import tools.GamerAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerListenerWithQueue extends Thread {
    Socket socket = null;
    BufferedReader bufferedReader = null;
    GamerAccount gamerAccount;
    public MessageWithAccountQueue messageWithAccountQueue;
    public ServerListenerWithQueue(Socket socket, GamerAccount gamerAccount, MessageWithAccountQueue messageWithAccountQueue) throws IOException {
        this.socket=socket;
        this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.gamerAccount=gamerAccount;
        this.messageWithAccountQueue=messageWithAccountQueue;
    }
    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String message = bufferedReader.readLine();
                if(message!=null){
                    MessageWithAccount messageWithAccount = new MessageWithAccount(message,gamerAccount);
                    messageWithAccountQueue.add(messageWithAccount);
                }
            } catch (IOException e) {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if(socket!= null){
                        socket.close();
                    }
                } catch (IOException f) {
                    f.printStackTrace();
                }
            }
        }
    }
}
