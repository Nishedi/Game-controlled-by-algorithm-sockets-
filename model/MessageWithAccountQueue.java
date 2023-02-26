package model;

import java.util.ArrayList;

public class MessageWithAccountQueue {
    private ArrayList<MessageWithAccount> queue = new ArrayList<>();

    public MessageWithAccount getAndRemove(){
        synchronized (this) {
            if (numberofmessages() == 0) return null;
            MessageWithAccount messageWithAccount = queue.get(0);
            queue.remove(0);
            return messageWithAccount;
        }
    }
    public int numberofmessages(){
        return queue.size();
    }

    public void add(MessageWithAccount mwa){
        synchronized (this) {
            queue.add(mwa);
        }

    }
}
