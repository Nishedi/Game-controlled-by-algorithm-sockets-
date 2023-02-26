package model;

import tools.GamerAccount;

public class MessageWithAccount {
    public String message;
    public GamerAccount gamerAccount;

    public MessageWithAccount(String message, GamerAccount gamerAccount) {
        this.message = message;
        this.gamerAccount = gamerAccount;
    }
}
