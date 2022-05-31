import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI implements KeyListener {
    private JLabel[][] room1;
    private JLabel[][] room2;
    private GridLayout layout;
    private JFrame frame;
    private JPanel gameScreen;
    private JPanel menu;
    private Gaming logic;

    public GUI()
    {
        logic = new Gaming();

        frame = new JFrame("Nintendo don't sue me");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        room1 = new JLabel[logic.getRoom1().length][logic.getRoom1()[0].length];
        room2 = new JLabel[logic.getRoom2().length][logic.getRoom2()[0].length];
    }

    public void updateRoom()
    {
        int currRoom = logic.getCurrRoom();
        if(currRoom == 1) {
            room1 = new JLabel[logic.getRoom1().length][logic.getRoom1()[0].length];
            MapElement[][] room = logic.getRoom1();
            for (int i = 0; i < logic.getRoom1().length; i++) {
                for (int j = 0; j < logic.getRoom1()[0].length; j++) {
                    room1[i][j] = new JLabel(new ImageIcon("src//placeholder.jpg"))
                }
            }
        }
        if(currRoom == 2)
        {
            room2 = new JLabel[logic.getRoom2().length][logic.getRoom2()[0].length];
            MapElement[][] room = logic.getRoom2();
            for (int i = 0; i < logic.getRoom2().length; i++) {
                for (int j = 0; j < logic.getRoom2()[0].length; j++) {
                    room2[i][j] = new JLabel(new ImageIcon("src//placeholder.jpg"))
                }
            }
        }
    }

    public void showStats()
    {

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
        logic.setInput(e.getKeyCode());
    }
}
