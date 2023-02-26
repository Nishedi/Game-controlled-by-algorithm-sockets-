package server;

import model.GameMap;
import tools.GamerMenager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class ServerController {
    ServerFrame serverFrame;
    Timer timer;
    GameMap gameMap;
    GamerMenager gamerMenager;


    public ServerController(GameMap gameMap, GamerMenager gamerMenager) throws FileNotFoundException {
        this.gamerMenager = gamerMenager;
        this.gameMap=gameMap;
        serverFrame=new ServerFrame(gameMap, gamerMenager);
        timer = new Timer(100, new TimerListener());
        timer.start();
    }
    class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            serverFrame.repaint();
        }
    }
}
