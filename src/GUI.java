import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI implements KeyListener {
    private JFrame frame;

    private JLabel[][] room1;
    private JLabel[][] room2;
    private ArrayList<JLabel[][]> rooms;
    private GridLayout layoutGameScreen;
    private JPanel gameScreen;
    private JPanel topSide;

    private JPanel menus;
    private JPanel playerInfo;
    private JPanel worldThings;
    private JLabel worldInfo;
    private JPanel tempMenus;

    private JPanel bottomSide;

    private Gaming logic;
    private Player player;

    private boolean showStats;
    private String worldText = "";
    private ArrayList<JLabel> tempMenuOptions;
    private int currOption = 0;
    private int selection = KeyEvent.VK_UNDEFINED;
    private GridLayout tempMenuLayout = new GridLayout();

    private JPanel active;

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
        topSide = new JPanel();

        menus = new JPanel();
        playerInfo = new JPanel();
        playerInfo.setPreferredSize(new Dimension(275,275));
        worldInfo = new JLabel(worldText);
        worldThings = new JPanel();
        worldInfo.setPreferredSize(new Dimension(275,275));
        tempMenus = new JPanel();
        tempMenus.setPreferredSize(new Dimension(275,275));
        bottomSide = new JPanel();

        tempMenus.setLayout(tempMenuLayout);

        showStats = false;
        worldText = "<html></html>";
        tempMenuOptions = new ArrayList<>();

        makeFrame();
    }

    public void makeFrame()
    {
        frame = new JFrame("Nintendo don't sue me");
        frame.setPreferredSize(new Dimension(825,825));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        updateGameScreen();
        topSide.add(gameScreen, BorderLayout.CENTER);
        frame.add(topSide, BorderLayout.NORTH);
        updateMenus();
        menus.add(playerInfo, BorderLayout.WEST);
        menus.add(worldInfo, BorderLayout.CENTER);
        menus.add(tempMenus, BorderLayout.EAST);
        bottomSide.add(menus);
        frame.add(bottomSide, BorderLayout.SOUTH);


        frame.pack();
        frame.setVisible(true);

    }

    public void updateMenus()
    {
        updateWorldInfo();
        updatePlayerMenu();
        updateTempMenus();
        selectionTempMenu();

        frame.pack();
    }

    public void updatePlayerMenu()
    {
        playerInfo.removeAll();
        String stats = player.toString();
        String str = "<html>";
        for (int j = 0; j < stats.length(); j++) {
            int comma = stats.indexOf(",");
            if(comma < 0)
            {
                break;
            }
            str += stats.substring(0, comma) + "<br>";
            stats = stats.substring(comma+1);
        }
        JLabel info = new JLabel(str);
        playerInfo.add(info);
        playerInfo.setVisible(showStats);

    }

    public void updateWorldInfo()
    {
        Chamber currChamber = logic.getChamber();
        if(currChamber.isSend()) {
            worldInfo.removeAll();

            int lineBr = 0;
            String html = "<html>";
            for (int i = html.length(); i < worldText.length(); i++) {
                String worldTextSub = worldText.substring(i);
                if (worldTextSub.contains(".") || worldTextSub.contains("!")) {
                    lineBr++;
                    if(worldTextSub.contains(".")) {
                        i += worldTextSub.indexOf(".") + 1;
                    }
                    if(worldTextSub.contains("!")) {
                        i += worldTextSub.indexOf("!") + 1;
                    }
                }
            }
            System.out.println("line" + lineBr);
            if (lineBr >= 10) {
                System.out.println("linesss"  + lineBr);
                if(worldText.indexOf(".")  < worldText.indexOf("!")) {
                    String tempStr = worldText.substring(worldText.indexOf(".") + 1);
                    worldText = "<html>" + tempStr;
                } else {
                    String tempStr = worldText.substring(worldText.indexOf("!") + 1);
                    worldText = "<html>" + tempStr;
                }
            }
            worldText = worldText.substring(0, worldText.indexOf("</html>"));
            worldText += currChamber.sendMessage() + "<br></html>";
            worldInfo.setText(worldText);
            currChamber.setSend(false);
        }

    }

    public void updateOptionMenu()
    {
        updateMenus();
        System.out.println(logic.getChamber().getFocus());

    }

    public void updateTempMenus()
    {
        tempMenus.removeAll();
        Chamber currChamber = logic.getChamber();
        ArrayList<Object> options  = currChamber.getOptions();
        if(options.size() < 1) {
            tempMenuLayout.setRows(1);
        } else {
            tempMenuLayout.setRows(options.size());
        }
        tempMenuLayout.setColumns(1);
        if(checkifEverythingisOfString(options))
        {
            for (int i = 0; i < options.size(); i++) {
                String str = (String) options.get(i);
                JLabel screenOption = new JLabel(str);
                screenOption.setFont( new Font("Helvetica", Font.BOLD, 20));
                screenOption.setForeground(Color.CYAN);
                tempMenuOptions.add(screenOption);
                //screenOption.setBorder(BorderFactory.createLineBorder());
                tempMenus.add(screenOption);
            }
        } else if (checkifEverythingisOfHammer(options)) {
            for (int i = 0; i < options.size(); i++) {
                HammerItem hammer = (HammerItem)  options.get(i);
                String hammerStr = hammer.toString();
                String str = "<html>";
                for (int j = 0; j < hammerStr.length(); j++) {
                    int comma = hammerStr.indexOf(",");
                    if(comma < 0)
                    {
                        break;
                    }
                    str += hammerStr.substring(0, comma) + "<br>";
                    hammerStr = hammerStr.substring(comma+1);
                }
                str += "</html>";
                JLabel screenOption = new JLabel(str);
                screenOption.setFont( new Font("Helvetica", Font.BOLD, 20));
                screenOption.setForeground(Color.CYAN);
                tempMenuOptions.add(screenOption);
                //screenOption.setBorder(BorderFactory.createLineBorder());
                tempMenus.add(screenOption);
            }

        } else if (checkifEverythingisOfExtiguisher(options)) {
            for (int i = 0; i < options.size(); i++) {
                ExtiguisherItem extiguisher = (ExtiguisherItem)  options.get(i);
                String extinguisherStr = extiguisher.toString();
                String str = "<html>";
                for (int j = 0; j < extinguisherStr.length(); j++) {
                    int comma = extinguisherStr.indexOf(",");
                    if(comma < 0)
                    {
                        break;
                    }
                    str += extinguisherStr.substring(0, comma) + "<br>";
                    extinguisherStr = extinguisherStr.substring(comma+1);
                }
                str += "</html>";
                JLabel screenOption = new JLabel(str);
                screenOption.setFont( new Font("Helvetica", Font.BOLD, 20));
                screenOption.setForeground(Color.CYAN);
                tempMenuOptions.add(screenOption);
                //screenOption.setBorder(BorderFactory.createLineBorder());
                tempMenus.add(screenOption);
            }
        }
        tempMenus.setVisible(true);
    }

    public static boolean checkifEverythingisOfString(ArrayList<Object> arrayList)
    {
        for (Object object: arrayList) {
            if(!(object instanceof String))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean checkifEverythingisOfHammer(ArrayList<Object> arrayList)
    {
        for (Object object: arrayList) {
            if(!(object instanceof HammerItem))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean checkifEverythingisOfExtiguisher(ArrayList<Object> arrayList)
    {
        for (Object object: arrayList) {
            if(!(object instanceof ExtiguisherItem))
            {
                return false;
            }
        }
        return true;
    }

    public void selectionTempMenu()
    {
        if(tempMenuOptions.size() > 0) {
            float[] HSB = new float[3];
            Color.RGBtoHSB(147, 194, 212, HSB);
            tempMenus.setBackground(Color.getHSBColor(HSB[0], HSB[1], HSB[2]));
            for (JLabel option : tempMenuOptions) {
                option.setBorder(BorderFactory.createLineBorder(Color.green, 0));
            }
            tempMenuOptions.get(currOption).setBorder(BorderFactory.createLineBorder(Color.green, 5));
            frame.pack();
        }
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

            layoutGameScreen = new GridLayout(logic.getCurrChamber().length, logic.getCurrChamber()[0].length);
            layoutGameScreen.setHgap(5);  // horizontal spacing between images
            layoutGameScreen.setVgap(10);  // vertical spacing between images
            gameScreen.setLayout(layoutGameScreen);
            //gameScreen.setPreferredSize(new Dimension(200,600));
            float[] HSB = new float[3];
            Color.RGBtoHSB(147,194,159, HSB);
            topSide.setBackground(Color.getHSBColor(HSB[0],HSB[1],HSB[2]));
            Color.RGBtoHSB(147,194,212, HSB);
            gameScreen.setBackground(Color.getHSBColor(HSB[0],HSB[1],HSB[2]));
            gameScreen.setBorder(BorderFactory.createLineBorder(Color.green, 5,true));
        } else
        {
            JLabel gameEnd = new JLabel("Game End");
            gameScreen.removeAll();
            gameScreen.add(gameEnd);
        }
        frame.pack();

    }




    public ImageIcon setSize(String str)
    {
        ImageIcon img = new ImageIcon(str);
        Image imgData = img.getImage();
        Image scaled = imgData.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public void subMenuMovement()
    {
        updateMenus();
        logic.getChamber().setFocus(InputFocus.SelectionMenu);

        if(selection == KeyEvent.VK_UP)
        {
            currOption++;
            if(currOption >= tempMenuOptions.size())
            {
                currOption = tempMenuOptions.size()-1;
            }
            System.out.println("up");
            logic.getChamber().setFocus(InputFocus.SelectionMenu);
            selectionTempMenu();
        } else if (selection == KeyEvent.VK_DOWN) {
            currOption--;
            if(currOption < 0)
            {
                currOption = 0;
            }
            System.out.println("down");
            logic.getChamber().setFocus(InputFocus.SelectionMenu);
            selectionTempMenu();
        } else if (selection == KeyEvent.VK_ENTER)
        {
            System.out.println(logic.getChamber().getSms());
            logic.getChamber().setSelection(currOption);
            if (logic.getChamber().getSms() == SubMenuSelection.YNStart) {
                logic.getChamber().setFocus(InputFocus.SelectionMenu);
                logic.getChamber().subMenYNStartWall(this);
            } else if (logic.getChamber().getSms() == SubMenuSelection.ItemPick)
            {
                logic.getChamber().setFocus(InputFocus.SelectionMenu);
                logic.getChamber().subMenItemPickWall(this);
            } else if (logic.getChamber().getSms() == SubMenuSelection.YNStayStill) {
                logic.getChamber().setFocus(InputFocus.SelectionMenu);
                logic.getChamber().stayStill();
                updateGameScreen();
            }

        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(logic.getChamber().getFocus());
        //if s pressed then show player stats
        //System.out.println("hi");
        if(e.getKeyCode() == KeyEvent.VK_P)
        {
            showStats = !showStats;
            logic.setChamberInput(e.getKeyCode());
        } else {
            if(logic.getChamber().getFocus() == InputFocus.SelectionMenu)
            {
                System.out.println("hihihi");
                updateMenus();
                selection = e.getKeyCode();
                subMenuMovement();
            } else if(logic.getChamber().getFocus() == InputFocus.GameScreen)
            {
                System.out.println(e.getKeyCode());
                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    logic.setChamberInput(e.getKeyCode());
                    logic.getChamber().game(this);
                }
                if(logic.isCurrRoomClear())
                {
                    logic.setCurrRoomNum(logic.getCurrRoomNum()+1);
                    worldText = "<html></html>";
                    worldInfo.setText(worldText);
                    worldInfo.removeAll();
                }
            } else
            {
                updateMenus();

            }
        }
        updateGameScreen();
        updateMenus();


    }

}
