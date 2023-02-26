package server;

import tools.GamerMenager;

import javax.swing.*;
import java.awt.*;

public class Statistic extends JPanel {
    GamerMenager gamerMenager;

    public Statistic(GamerMenager gamerMenager){
        this.gamerMenager=gamerMenager;
    }

    public void paint(Graphics  G){
        Graphics2D G2D = (Graphics2D)  G;
        G2D.setStroke(new BasicStroke(10));
        G2D.drawRect(20,20,300,400);
        Font font = new Font("Serif", Font.BOLD+Font.ITALIC, 20);
        G2D.setFont(font);
        G2D.drawString("SCORE", 130,50);
        String results = gamerMenager.scores();
        String[] result = results.split(" ");
        if(results.compareTo("")==0)return;
        for(int i = 0; i<=result.length-1;i++ ){
            String[] wynik = result[i].split(";");

            G2D.drawString(wynik[0]+" "+wynik[1]+" "+wynik[2], 130, 50 + (i+1)*20);
        }
    }



}
