package server;

import model.GameMap;
import tools.GamerAccount;
import tools.GamerMenager;

import javax.swing.*;
import java.awt.*;

public class VisualisationServerPanel extends JPanel {
    GameMap gameMap;
    public GamerMenager gamerMenager;
    public VisualisationServerPanel(GameMap gameMap, GamerMenager gamerMenager){
        this.gameMap=gameMap;
        this.gamerMenager = gamerMenager;
    }
    public void paint(Graphics G) {
        Graphics2D G2D = (Graphics2D) G;
        for(int row = 0 ; row<gameMap.maprows;row++){
            for(int column = 0; column<gameMap.mapcolumns; column++){
                if(gameMap.getElement(column,row)=='W'){
                    G2D.setColor(Color.GREEN);
                    G2D.fillRect(column*20,row*20,20,20);
                }
                if(gameMap.getElement(column,row)=='S'){
                    G2D.setColor(Color.BLUE.brighter().brighter());
                    G2D.fillRect(column*20,row*20,20,20);
                }
                if(gameMap.getElement(column,row)=='G'){
                    G2D.setColor(Color.RED.brighter().brighter());
                    G2D.fillRect(column*20,row*20,20,20);
                }
            }
        }
        G2D.setColor(Color.RED);
        for(String s: gamerMenager.LoginBase.keySet()) {
            GamerAccount gamerAccount = gamerMenager.LoginBase.get(s);
            G2D.fillRect(gamerAccount.possition.column * 20, gamerAccount.possition.row * 20, 20, 20);
        }
    }
}
