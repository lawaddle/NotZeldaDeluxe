public class Gaming {
    /** Hole map element for game*/
    private HoleMapElement hole = new HoleMapElement();
    /** Fire map element for game*/
    private FireMapElement fire = new FireMapElement();
    /** Wall map element for game*/
    private WallMapElement wall = new WallMapElement();
    /** Blank map element for game*/
    private BlankMapElement blank = new BlankMapElement();
//
//    /** Hammer item for game*/
//    private HammerItem hammer = new HammerItem("Hammer", 10, 5);
//    /** sword item for game*/
//    private SwordItem sword = new SwordItem("Sword", 15, 3);
//    /** Extinguisher item for game*/
//    private ExtiguisherItem extinguisher = new ExtiguisherItem("Extinguisher", 7, 1);
//
    /** player object of game*/
    private Player player;
    /** 1st room of game*/
    private MapElement[][] room2;
    /** 2nd room of game*/
    private MapElement[][] room1;

    /** Initializes Gaming object
     *
     */
    public Gaming()
    {
        player = new Player();
        makeRoom2();
        makeRoom1();
    }

    /** Plays whole game
     *
     */
    public void play()
    {
        System.out.println("Game Start");
        System.out.println("Room 1 Start");
        Chamber chamber1 = new Chamber(room1, 0, 0, 3, 3, player);
        chamber1.game();
        if(!(player.isDead())) {
            System.out.println("Room 2 Start");
            Chamber chamber2 = new Chamber(room2, 0, 0, 4,7, player);
            chamber2.game();
        }
        System.out.println("Game End");
    }

    /** Makes 2nd room of game
     *
     */
    public void makeRoom2()
    {
        room2 = new MapElement[8][8];
        room2[0][2] = hole;
        room2[0][6] = fire;
        room2[0][7] = new SwordItem("Sword", 15, 3);
        room2[1][7] = hole;
        room2[2][4] = hole;
        room2[2][6] = fire;
        room2[2][7] = new SwordItem("Sword", 15, 3);
        room2[3][0] = new HammerItem("Hammer", 10, 5);
        room2[3][3] = new SwordItem("Sword", 15, 3);
        room2[3][7] = wall;
        room2[4][0] = new ExtiguisherItem("Extinguisher", 7, 1);
        room2[4][2] = hole;
        room2[4][4] = fire;
        room2[4][5] = new HammerItem("Hammer", 10, 5);
        room2[4][6] = fire;
        room2[5][7] = wall;
        room2[6][0] = wall;
        room2[6][1] = hole;
        room2[6][4] = hole;
        room2[6][6] = fire;
        room2[6][7] = new ExtiguisherItem("Extinguisher", 7, 1);
        room2[7][0] = new SwordItem("Sword", 15, 3);
        room2[7][2] = fire;
        room2[7][4] = hole;

        fillWithBlanks(room2);
    }

    /** Makes 1st room of game
     * (is a testing room)
     */
    public void makeRoom1()
    {
        room1 = new MapElement[5][5];
        room1[1][1] = wall;
        room1[0][3] = fire;
        room1[0][2] = fire;
        room1[2][4] = hole;
        room1[4][0] = new ExtiguisherItem("Extinguisher", 7, 1);
        room1[2][0] = new HammerItem("Hammer", 10, 5);
        room1[1][3] = new HammerItem("Hammer", 10, 5);
        fillWithBlanks(room1);
    }

    /** Fills remaining spaces in a with blank map elements
     *
     * @param room
     */
    public void fillWithBlanks(MapElement[][] room)
    {
        for (int i = 0; i < room.length; i++) {
            for (int f = 0; f < room[0].length; f++) {
                if (room[i][f] == null) {
                    room[i][f] = blank;
                }
            }
        }
    }

}
