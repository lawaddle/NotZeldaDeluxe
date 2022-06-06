import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI implements KeyListener {
    private JLabel[][] room1;
    private JLabel[][] room2;
    private ArrayList<JLabel[][]> rooms;
    private GridLayout layout;
    private JFrame frame;
    private JPanel gameScreen;
    private JPanel menus;
    private JLabel playerInfo;
    private JTextArea worldInfo;
    private JLabel tempMenus;
    private Gaming logic;
    private Player player;

    public GUI()
    {
        logic = new Gaming();
        player = logic.getPlayer();
        room1 = new JLabel[logic.getRoom1().length][logic.getRoom1()[0].length];
        room2 = new JLabel[logic.getRoom2().length][logic.getRoom2()[0].length];
        rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);
        gameScreen = new JPanel();
        makeFrame();
        logic.play();
    }

    public void makeFrame()
    {
        frame = new JFrame("Nintendo don't sue me");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        updateGameScreen();
        frame.add(gameScreen, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);

    }

    public void updateRoom()
    {
        int currRoom = logic.getCurrRoomNum();
        System.out.println("m" + currRoom);
        if(currRoom == 1) {
            MapElement[][] room = logic.getCurrChamber();
            System.out.println("a" + room.length);
            for (int i = 0; i < logic.getCurrChamber().length; i++) {
                for (int j = 0; j < logic.getCurrChamber()[0].length; j++) {

                    room1[i][j] = new JLabel(setSize(room[i][j].getImg()));
                    System.out.println(room1[i][j]);
                }
            }
        }
        if(currRoom == 2)
        {
            MapElement[][] room = logic.getCurrChamber();
            for (int i = 0; i < logic.getCurrChamber().length; i++) {
                for (int j = 0; j < logic.getCurrChamber()[0].length; j++) {
                    room2[i][j] = new JLabel(setSize(room[i][j].getImg()));
                }
            }
        }
    }

    public void updateGameScreen()
    {
        updateRoom();
        int curRoom = logic.getCurrRoomNum() -1;
        for (int i = 0; i < rooms.get(curRoom).length; i++) {
            for (int j = 0; j < rooms.get(curRoom)[0].length; j++) {
                JLabel[][] room = rooms.get(curRoom);
                System.out.println(room);
                System.out.println(rooms.get(curRoom)[i][j]);
                gameScreen.add(rooms.get(curRoom)[i][j]);
            }
        }
        layout = new GridLayout(logic.getCurrChamber().length, logic.getCurrChamber()[0].length);
        layout.setHgap(5);  // horizontal spacing between images
        layout.setVgap(10);  // vertical spacing between images
        gameScreen.setLayout(layout);


    }


    public void showStats()
    {

    }

    public ImageIcon setSize(String str)
    {
        ImageIcon img = new ImageIcon(str);
        Image imgData = img.getImage();
        Image scaled = imgData.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //if s pressed then show player stats
        logic.setChamberInput(e.getKeyCode());
        updateGameScreen();

    }
}
