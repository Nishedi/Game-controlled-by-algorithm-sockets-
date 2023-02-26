package gamer;

import model.GameMap;
import model.Possition;

import javax.swing.*;
import java.awt.*;

public class VisualisationPanel extends JPanel {
    GameMap map;
    public Possition gamerPossition=new Possition(-1,-1);
    public VisualisationPanel(GameMap map) {
        this.map = map;
    }

    public void paint(Graphics G) {
        Graphics2D G2D = (Graphics2D) G;
        if (map != null) {
            for (int row = 0; row < map.maprows; row++) {
                for (int column = 0; column < map.mapcolumns; column++) {
                    if (map.getElement(column,row) == 'W') {

                        G2D.setColor(Color.GREEN);
                        G2D.fillRect(column * 20,row*20,  20, 20);
                    }
                    if (map.getElement(column,row) == 'U') {
                        G2D.setColor(Color.BLACK);
                        G2D.fillRect(column * 20,row*20,  20, 20);
                    }
                    if (map.getElement(column,row) == 'S') {
                        G2D.setColor(Color.BLUE.brighter().brighter());
                        G2D.fillRect(column * 20,row*20,  20, 20);
                    }
                    if (map.getElement(column,row) == 'P') {
                        G2D.setColor(Color.RED.brighter().brighter());
                        G2D.fillRect(column * 20,row*20,  20, 20);
                    }
                }
            }
            G2D.setColor(Color.RED);
            G2D.fillRect(gamerPossition.column*20,gamerPossition.row*20, 20,20);
        }
    }
}
