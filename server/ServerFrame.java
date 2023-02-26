package server;

import model.GameMap;
import tools.GamerMenager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class ServerFrame extends JFrame {
    JTextField serverHost;
    JTextField serverPort;
    JPanel controllPanel;
    JPanel statisticPanel;
    GameMap gameMap;
    JPanel visualisationPanel;
    GamerMenager gamerMenager;
    public ServerFrame(GameMap gameMap, GamerMenager gamerMenager) throws FileNotFoundException {
        controllPanel = new JPanel();
        this.gamerMenager = gamerMenager;
        add(controllPanel, BorderLayout.SOUTH);
        this.gameMap=gameMap;
        visualisationPanel=new VisualisationServerPanel(gameMap, gamerMenager);
        add(visualisationPanel, BorderLayout.WEST);
        setControllpanel();
        statisticPanel = new Statistic(gamerMenager);
        add(statisticPanel, BorderLayout.EAST);
        visualisationPanel.setPreferredSize(new Dimension(gameMap.mapcolumns*20+50,gameMap.maprows*20+50));
        statisticPanel.setPreferredSize(new Dimension(350,100));
        this.add(statisticPanel, BorderLayout.EAST);
        this.setResizable(true);
        this.setPreferredSize(new Dimension(gameMap.mapcolumns*20+400,gameMap.maprows*20+200));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    private void setControllpanel(){
        serverHost=new JTextField();
        serverPort=new JTextField();
        serverHost.setText("localhost");
        serverPort.setText("1000");
        serverHost.setPreferredSize(new Dimension(80,20));
        serverPort.setPreferredSize(new Dimension(80,20));
        controllPanel.add(serverPort);
        controllPanel.add(serverHost);
    }
}
