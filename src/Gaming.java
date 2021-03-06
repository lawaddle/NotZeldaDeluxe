import java.util.ArrayList;

public class Gaming {
    /** Hole map element for game*/
    private HoleMapElement hole = new HoleMapElement();
    /** Fire map element for game*/
    private FireMapElement fire = new FireMapElement();
    /** Wall map element for game*/
    private WallMapElement wall = new WallMapElement();
    /** Blank map element for game*/
    private BlankMapElement blank = new BlankMapElement();

    /** player object of game*/
    private Player player;
    /** 1st room of game*/
    private MapElement[][] room2 = new MapElement[8][8];
    /** 2nd room of game*/
    private MapElement[][] room1 = new MapElement[5][5];
    private int currRoom = 1;
    private ArrayList<Chamber> chambers= new ArrayList<>();
    private ArrayList<MapElement[][]> rooms = new ArrayList<>();


    /** Initializes Gaming object
     *
     */
    public Gaming()
    {


        player = new Player();
        makeRoom1();
        makeRoom2();
        rooms.add(room1);
        rooms.add(room2);
        Chamber chamber1 = new Chamber(room1, 0, 0, 3, 3, player);
        Chamber chamber2 = new Chamber(room2, 0, 0, 4,7, player);
        chambers.add(chamber1);
        chambers.add(chamber2);
    }

    public Player getPlayer() {
        return player;
    }

    /** Plays whole game
     *
     */
    public void play()
    {

        System.out.println("Game Start");
        for (int i = 1; i <= chambers.size(); i++) {
            Chamber currChamber = chambers.get(currRoom-1);
            System.out.println("Room " + i + " Start");
            while((player.getLifeCount() > 0) && !currChamber.isCleared())
            {


                currRoom = i;

                //currChamber.game();
            }
        }

//        System.out.println("Room 1 Start");
//        currRoom =1;
//        chamber1.game();
//        if(!(player.isDead())) {
//            System.out.println("Room 2 Start");
//            currRoom = 2;
//            chamber2.game();
//        }
        System.out.println("Game End");
    }

    /** Makes 2nd room of game
     *
     */
    public void makeRoom2()
    {
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

    public MapElement[][] getRoom1() {
        return room1;
    }

    public MapElement[][] getRoom2() {
        return room2;
    }

    public int getCurrRoomNum() {
        return currRoom;
    }

    public void setCurrRoomNum(int newNum)
    {
        currRoom = newNum;
    }

    public MapElement[][] getCurrChamber(){
        return (chambers.get(currRoom-1).getRoom());
    }

    public Chamber getChamber(){
        return chambers.get(currRoom-1);
    }

    public boolean isCurrRoomClear()
    {
        return chambers.get(currRoom-1).isCleared();
    }

    public boolean isAllRoomClear()
    {
        for (Chamber chamber: chambers) {
            if(!chamber.isCleared())
            {
                return false;
            }
        }
        return true;
    }



    public void setChamberInput(int input) {
        //System.out.println(currRoom-1);
        chambers.get(currRoom-1).setInput(input);
    }
}
