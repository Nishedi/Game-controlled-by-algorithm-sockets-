package gamer;

import model.GameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GamerFrame extends JFrame {
    JPanel controllPanel;
    JTextField clientPort;
    JTextField clientHost;
    JButton openPort;
    JTextField username;

    JTextField serverPort;
    JTextField serverHost;
    JButton login;
    JButton start;
    JTextArea comunicates;
    public VisualisationPanel visualisationPanel;
    GameMap map;
    public GamerFrame(GameMap map){
        this.map=map;
        visualisationPanel = new VisualisationPanel(map);
        setControllPanel();
        this.add(visualisationPanel);
        add(controllPanel, BorderLayout.SOUTH);
        this.setResizable(true);
        this.setPreferredSize(new Dimension(1000,1000));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ustawia domyślną akcję zamknięcia okna
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setControllPanel(){
        controllPanel=new JPanel();
        username=new JTextField();
        username.setText("Johny");
        username.setPreferredSize(new Dimension(80,20));

        clientPort=new JTextField();
        clientPort.setText("1234");
        clientPort.setPreferredSize(new Dimension(80,20));
        clientHost = new JTextField();
        clientHost.setText("localhost");
        clientHost.setPreferredSize(new Dimension(80,20));
        openPort = new JButton();
        openPort.setText("Open port");
        openPort.setPreferredSize(new Dimension(100,20));
        serverPort=new JTextField();
        serverPort.setText("1000");
        serverPort.setPreferredSize(new Dimension(80,20));
        serverHost=new JTextField();
        serverHost.setText("localhost");
        serverHost.setPreferredSize(new Dimension(80,20));
        login=new JButton();
        login.setText("login");
        login.setPreferredSize(new Dimension(80,20));
        start=new JButton();
        start.setText("start");
        start.setPreferredSize(new Dimension(80,20));
        comunicates=new JTextArea();
        comunicates.setPreferredSize(new Dimension(500,20));

        controllPanel.setPreferredSize(new Dimension(500,100));
        controllPanel.add(username);
        controllPanel.add(clientPort,BorderLayout.AFTER_LINE_ENDS);

        controllPanel.add(clientHost);
        controllPanel.add(openPort);
        controllPanel.add(serverPort);
        controllPanel.add(serverHost);
        controllPanel.add(login);
        controllPanel.add(start);
        controllPanel.add(comunicates, BorderLayout.SOUTH);
    }
    public void openPort(ActionListener listenforopenPort){openPort.addActionListener(listenforopenPort);}
    public void login(ActionListener listenforlogin){login.addActionListener(listenforlogin);}
    public void start(ActionListener listenforstart){start.addActionListener(listenforstart);}
}
