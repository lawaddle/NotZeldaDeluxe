import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI implements KeyListener {
    private JLabel[][] room1;
    private JLabel[][] room2;
    private GridLayout layout;
    private JFrame frame;
    private JPanel game;
    private JPanel instructions;
    private Gaming eee;

    public GUI()
    {
        eee = new Gaming();

        frame = new JFrame("Nintendo don't sue me");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void updateRoom()
    {
        room1 = new JLabel[eee.getRoom1().length][eee.getRoom1()[0].length];
        MapElement[][] room = eee.getRoom1();
        for (int i = 0; i < eee.getRoom1().length;i++) {
            for (int i = 0; i < eee.getRoom1()[0].length;i++) {
                room1 = new JLabel(new ImageIcon("src//placeholder.jpg"))
            }
        }
    }



}
