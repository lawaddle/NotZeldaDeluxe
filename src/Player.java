import java.util.ArrayList;
import java.util.Scanner;


public class Player extends MapElement{
    /** life count of player*/
    private int lifeCount = 5;
    /** attack stat of player*/
    private int atk = 10;
    /** defense stat of player*/
    private int def = 10;
    /** player health points*/
    private int hp = 10;
    /** player starting hp*/
    private final int STARTING_HP = 10;
    /** player dead?*/
    private boolean dead = false;
    /** Player inventory    */
    private ArrayList<Item> inventory = new ArrayList<>();
    /** Scanner object*/
    private Scanner sc = new Scanner(System.in);

    /**
     * Initializes player object
     */
    public Player ()
    {
        super(" ", "\uD83D\uDE10", "src/imgs/lonk.png");
        userPickName();
    }

    /**
     * Lets use set the player name
     */
    public void userPickName()
    {
        System.out.print("What's your name: ");
        setName(sc.nextLine());
    }

    /**
     *  adds item to player inventory
     * @param item item to be added to inventory
     */
    public void addItemToInven(Item item)
    {
        inventory.add(item);
    }

    /**
     * removes item from player inventory
     * @param item item to be removed from inventory
     */
    public void removeItemFromInven(Item item)
    {
        inventory.remove(item);
    }

    /**
     * Sets new life count for player
     * @param lifeCount new life count value for player
     */
    public void setLifeCount(int lifeCount) {
        this.lifeCount = lifeCount;
    }

    /**
     * sets new hp for player
     * @param hp new hp value for player
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Checks and returns if player is dead or not and if the
     * player is dead lowers their life count
     * @return player dead?
     */
    public boolean isDead() {
        if(hp <= 0)
        {
            dead = true;
        }
        if(dead)
        {
            lifeCount--;
            hp = STARTING_HP;
        }
        return dead;
    }

    /**
     * Sets dead
     * @param dead new dead boolean
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * returns current player hp
     * @return player hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * returns current life count of player
     * @return player life count
     */
    public int getLifeCount() {
        return lifeCount;
    }

    /**
     * returns an array life of all the hammer items in the player's inventory
     * @return ArrayList representing all the hammer items in inventory
     */
    public ArrayList<HammerItem> hammersInInven()
    {
        ArrayList<HammerItem> hammers = new ArrayList<>();
        for (Item item: inventory) {
           if(item instanceof HammerItem)
           {
               HammerItem hammer = (HammerItem) item;
               hammers.add(hammer);
           }
        }
        return hammers;
    }
    /**
     * returns an array life of all the extinguisher items in the player's inventory
     * @return ArrayList representing all the extinguisher items in inventory
     */
    public ArrayList<ExtiguisherItem> extiguishersInInven()
    {
        ArrayList<ExtiguisherItem> extinguishers = new ArrayList<>();
        for (Item item: inventory) {
            if(item instanceof ExtiguisherItem)
            {
                ExtiguisherItem extiguisher = (ExtiguisherItem) item;
                extinguishers.add(extiguisher);
            }
        }
        return extinguishers;
    }

    /**
     * prints out hammer items in an array list of hammer items
     * @param list ArrayList of hammer items
     */
    public static void printHammers(ArrayList<HammerItem> list)
    {
        for (int i = 0; i < list.size(); i++) {
            int display =  i+1;
            System.out.println(display + ": " + list.get(i));
        }
    }

    /**
     * prints out extinguisher items in an array list of extinguisher items
     * @param list ArrayList of extinguisher items
     */
    public static void printExtgusihers(ArrayList<ExtiguisherItem> list)
    {
        for (int i = 0; i < list.size(); i++) {
            int display =  i+1;
            System.out.println(display + ": " + list.get(i));
        }
    }

    /** Returns string that contains values of name,
     *  damage, attack, hp, and inventory instance variables (the player's state)
     *
     * @return String representation of the player's state
     */
    @Override
    public String toString() {
        String str = super.toString();
        str+= "\nAttack: " + atk + " Defense: " +  def + " HP: " + hp;
        str+= "\nInventory: \n";
        for (Item item: inventory) {
            str += item + "\n";
        }
        if(inventory.size() <= 0)
        {
            str += "EMPTY";
        }
        return str;
    }
}
