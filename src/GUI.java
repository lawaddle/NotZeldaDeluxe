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
    private JPanel topSide;
    private JPanel bottomSide;
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
        topSide = new JPanel();
        gameScreen = new JPanel();
        menus = new JPanel();
        worldInfo = new JTextArea(10,10);


        makeFrame();
        logic.play();
    }

    public void makeFrame()
    {
        frame = new JFrame("Nintendo don't sue me");
        frame.setPreferredSize(new Dimension(1000,1000));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        updateGameScreen();
        topSide.add(gameScreen);
        frame.add(topSide, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);

    }

    public void updateMenus()
    {

    }

    public void updateRoom()
    {
        int currRoom = logic.getCurrRoomNum();
        System.out.println(currRoom);
        //System.out.println("m" + currRoom);
        if(currRoom == 1) {
            MapElement[][] room = logic.getCurrChamber();
            //System.out.println("a" + room.length);
            for (int i = 0; i < logic.getCurrChamber().length; i++) {
                for (int j = 0; j < logic.getCurrChamber()[0].length; j++) {

                    room1[i][j] = new JLabel(setSize(room[i][j].getImg()));
                    //System.out.println(room1[i][j]);
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
        gameScreen.removeAll();
        if(!logic.isAllRoomClear()) {
            updateRoom();
            int curRoom = logic.getCurrRoomNum() - 1;

            for (int i = 0; i < rooms.get(curRoom).length; i++) {
                for (int j = 0; j < rooms.get(curRoom)[0].length; j++) {
                    JLabel[][] room = rooms.get(curRoom);
                    //System.out.println(room);
                    //System.out.println(rooms.get(curRoom)[i][j]);

                    gameScreen.add(room[i][j]);
                }
            }

            layout = new GridLayout(logic.getCurrChamber().length, logic.getCurrChamber()[0].length);
            layout.setHgap(5);  // horizontal spacing between images
            layout.setVgap(10);  // vertical spacing between images
            gameScreen.setLayout(layout);
            //gameScreen.setPreferredSize(new Dimension(200,600));
            float[] HSB = new float[3];
            Color.RGBtoHSB(147,194,159, HSB);
            topSide.setBackground(Color.getHSBColor(HSB[0],HSB[1],HSB[2]));
        } else
        {
            JLabel gameEnd = new JLabel("Game End");
            gameScreen.removeAll();
            gameScreen.add(gameEnd);
        }
        frame.pack();

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
        System.out.println(e.getKeyCode());
        logic.setChamberInput(e.getKeyCode());
        logic.getChamber().game();
        if(logic.isCurrRoomClear())
        {
            logic.setCurrRoomNum(logic.getCurrRoomNum()+1);
        }
        updateGameScreen();


    }
}
