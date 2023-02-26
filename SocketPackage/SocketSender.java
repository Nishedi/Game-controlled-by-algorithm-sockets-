package SocketPackage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketSender {
    private Socket socket;
    private BufferedWriter bufferedWriter;

    public SocketSender(Socket socket){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch(IOException e){
            close();
        }
    }
    public void sendMessage(String messageToSend){
        try{
            if (socket.isConnected()){
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (IOException e){
            close();
        }
    }
    public void close(){
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket!= null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
