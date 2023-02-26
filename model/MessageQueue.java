package model;

import java.util.ArrayList;

public class MessageQueue {
    private ArrayList<String> queue = new ArrayList<>();
    public String getAndRemove(){
        synchronized (this) {
            if (numberofmessages() == 0) return null;
            String str = queue.get(0);
            queue.remove(0);
            return str;
        }
    }
    public int numberofmessages(){
        return queue.size();
    }

    public void add(String s){
        synchronized (this) {
            queue.add(s);
        }

    }
}
