package tools;

import threads.ServerThread;
import SocketPackage.SocketSender;
import model.Possition;

public class GamerAccount {
    public String username;
    public int port;
    public String host;
    public int connected =0;//0-false, 1 - true
    public SocketSender socketSender;
    public ServerThread serverThreadnew =null;
    public Possition possition=new Possition(1,1);
    public int score=0;

    public long blockedto=1000;

    public GamerAccount(){
        this.username="";
        this.port=0;
        this.host="";
        this.connected=0;
    }

    public GamerAccount(String username, int port, String host) {
        this.username = username;
        this.port = port;
        this.host = host;
        this.connected=0;
    }
    public void parse(String loginstring){
        String[] parsetab = loginstring.split(";");
        username=parsetab[1];
        port=Integer.valueOf(parsetab[3]);
        host=parsetab[5];
    }
}
