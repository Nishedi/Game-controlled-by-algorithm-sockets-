package threads;

import model.MessageQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class GamerListenerWithQueue extends Thread{
    Socket socket = null;
    BufferedReader bufferedReader = null;

    private MessageQueue  messageQueue;
    public GamerListenerWithQueue(Socket socket, MessageQueue messageQueue) throws IOException {
        this.socket=socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.messageQueue =messageQueue;
    }
    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String message = bufferedReader.readLine();
                if(message!=null){
                    messageQueue.add(message);
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
