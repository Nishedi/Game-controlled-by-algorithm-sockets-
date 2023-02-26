package model;

import threads.ServerListenerWithQueue;
import tools.GamerAccount;

public class SocketListenerWithAccount {
    public GamerAccount gamerAccount;
    ServerListenerWithQueue threadlistenernew;

    public SocketListenerWithAccount(ServerListenerWithQueue threadlistenernew, GamerAccount gamerAccount) {
        this.gamerAccount = gamerAccount;
        this.threadlistenernew = threadlistenernew;
    }
}
