import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.util.ArrayList;

public class Chamber {
    private MapElement[][] room;
    /** Starting x position for player */
    private int startx;
    /** Starting y position for player */
    private int starty;
    /** Ending x position for player */
    private int endx;
    /** Ending y position for player */
    private int endy;
    /** Scanner object*/
    private Scanner sc = new Scanner(System.in);
    /** current player x position */
    private int playerPosx;
    /** current player y position */
    private int playerPosy;
    /** Player object*/
    private Player player;
    private int input = KeyEvent.VK_UNDEFINED;

    /** Initializes chamber object
     *
     * @param room chamber room
     * @param startx chamber starting x position for player
     * @param starty chamber starting y position for player
     * @param endx chamber ending x position for player
     * @param endy chamber ending y position for player
     * @param player player object in chamber
     */
    public Chamber(MapElement[][] room, int startx, int starty, int endx, int endy, Player player)
    {
        this.room = room;
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        this.player = player;
        playerPosx = startx;
        playerPosy = starty;
        room[startx][starty] = player;

    }

    /** Runs the game
     * (prints the room, shows player info, runs player movement logic)
     * (shows death message, game over message, end of room messages)
     *
     */
    public void game()
    {
        boolean ah = true;
        while (ah)
        {
            printRoom();
            showPlayerInfo();
            movePlayer();
            if(player.isDead())
            {
                System.out.println("You died");
                System.out.println("You have " + player.getLifeCount() + " lives remaining.") ;
                player.setDead(false);
            }
            if(player.getLifeCount() <= 0)
            {
                ah = false;
                System.out.println("Game Over");
            }
            if(room[endx][endy] instanceof Player)
            {
                if(noMoreItems())
                {
                    ah = false;
                    System.out.println("Room Complete");
                } else
                {
                    System.out.println("You still have items to get.");
                }
            }

        }
    }

    /**
     * Gives user option to show player info and shows player info
     */
    public void showPlayerInfo()
    {
        String ans = "";
        System.out.print("Show Player Stats? Enter (Y) for yes or anything else for no ");
        ans = sc.nextLine();
        ans = ans.toLowerCase();
        if(ans.equals("y") || ans.equals("yes"))
        {
            System.out.println(player.toString());
        }
    }

    /** Returns boolean of if there are any more items in room
     *
     * @return is there no more items in room
     */
    public boolean noMoreItems()
    {
        for (int i = 0; i < room.length; i++) {
            for (int f = 0; f < room[0].length; f++) {
                if (room[i][f] instanceof Item) {
                   return false;
                }
            }
        }
        return true;
    }

    /**
     * Resets the starting and ending postion displays if the player has moved off of them
     * Prints out the room
     */
    public void printRoom()
    {
        if(!(room[startx][starty] instanceof Player))
        {
            room[startx][starty] = new StartMapElement();
        }
        if(!(room[endx][endy] instanceof Player))
        {
            room[endx][endy] = new EndMapElement();
        }
        for (int i = 0; i < room.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < room[0].length; j++) {
                if (room[i][j] == null) {
                    System.out.print("  | ");
                } else
                {
                    System.out.print(room[i][j].getMapDisplay() + " | ");
                }
            }
            System.out.println();
        }
    }

    //if blank, just set new space to player space and old place to blank
    //if item, pick up item (make method for that), set new space to player space and old place to blank
    //if oob, don't allow movement

    /** Moves player
     * Ask user what direction do they want to go
     * Picks up item if they land on a space with one
     * Falls down hole if player lands on one
     * Stops player from going out of bounds
     * checks if player is touching wall (look at wallMovement method)
     * checks if player is touching fire (look at fireMovement method
     *
     */
    public void movePlayer()
    {

        boolean lop = true;
        String choice = "";
        while (lop) {
            System.out.println("Where do you want to move? \n(U)p \n(D)own \n(L)eft \n(R)ight");
            choice = sc.nextLine();
            choice = choice.toLowerCase();
            if(choice.equals("u") || choice.equals("up") || choice.equals("d") || choice.equals("down") || choice.equals("l") || choice.equals("left") || choice.equals("r") || choice.equals("right"))
            {
                lop = false;
                choice = choice.substring(0,1);
            } else
            {
                System.out.println("Invaild Option. Please pick again.");
            }
        }
        int oldPosx = playerPosx;
        int oldPosy = playerPosy;
        int newPosx = playerPosx;
        int newPosy = playerPosy;
        if(choice.equals("d"))
        {
            newPosx++;
        } else if (choice.equals("u"))
        {
            newPosx--;
        } else if (choice.equals("l"))
        {
            newPosy--;
        } else
        {
            newPosy++;
        }
        //if movement, change previous player location to blank
        if (outOfBounds(newPosx, newPosy))
        {
            System.out.println("Nope, you don't get to go out of bounds.");
        } else if(room[newPosx][newPosy] instanceof WallMapElement)
        {
            //if hammer in inventory, ask to break though walls, stand in place where wall was
            //if hammer not in inventory or player says no, no movement
            //order of events
            //check if hammer in inventory
            //ask to use hammer or not
            //show all hammers in inventory
            //pick hammer to use
            //confirm hammer choice
            //change wallElement to blank
            //ask player to move
            //if player doesn't move, doesn't break wall, or doesn't have hammer player stays still
            System.out.println("There is a wall in your way.");
            wallMovement(oldPosx, oldPosy, newPosx, newPosy);
        } else if(room[newPosx][newPosy] instanceof FireMapElement)
        {
            //if extinguisher in inventory, ask to use to extinguish fire, and move to place where fire was
            //otherwise move back 2
            System.out.println("There is fire in your way.");
            boolean burned = fireMovement(oldPosx, oldPosy, newPosx, newPosy);
            while(burned)
            {
                player.setHp(player.getHp()-3);
                System.out.println("You got burned");
                if(choice.equals("d"))
                {
                    newPosx = newPosx - 2;
                } else if (choice.equals("u"))
                {
                    newPosx = newPosx + 2;
                } else if (choice.equals("l"))
                {
                    newPosy = newPosy + 2;
                } else
                {
                    newPosy  = newPosy - 2;
                }
                if(outOfBounds(newPosx, newPosy) || room[newPosx][newPosy] instanceof WallMapElement)
                {
                    burned = false;
                    while (outOfBounds(newPosx, newPosy) || room[newPosx][newPosy] instanceof WallMapElement) {
                        if (choice.equals("d")) {
                            newPosx++;
                        } else if (choice.equals("u")) {
                            newPosx--;
                        } else if (choice.equals("l")) {
                            newPosy--;
                        } else {
                            newPosy++;
                        }
                    }
                    playerMove(oldPosx, oldPosy, newPosx, newPosy);
                }
                if(room[newPosx][newPosy] instanceof HoleMapElement)
                {
                    burned = false;
                    fallDown(oldPosx, oldPosy);
                }
                if(room[newPosx][newPosy] instanceof Item)
                {
                    burned = false;
                    pickUp(oldPosx, oldPosy, newPosx, newPosy);
                }
                if(room[newPosx][newPosy] instanceof BlankMapElement)
                {
                    burned = false;
                    playerMove(oldPosx, oldPosy, newPosx, newPosy);
                }
            }
        }  else if(room[newPosx][newPosy] instanceof HoleMapElement)
        {
            //fall in hole and lose a life, and go back to start
            fallDown(oldPosx, oldPosy);
        }  else if(room[newPosx][newPosy] instanceof Item)
        {
            //pick up item and add it to inventory
            //move to place where item was
            pickUp(oldPosx, oldPosy, newPosx, newPosy);

        } else
        {
            playerMove(oldPosx, oldPosy, newPosx, newPosy);
        }

    }

    /**
     * Has player fall down hole, lose a life, and go back to start
     * @param oldPosx player x position before falling down hole
     * @param oldPosy player y position before falling down hole
     */
    public void fallDown(int oldPosx, int oldPosy)
    {
        player.setLifeCount(player.getLifeCount() - 1);
        makeSpaceBlank(oldPosx, oldPosy);
        playerMove(oldPosx, oldPosy, startx, starty);
        System.out.println("You fell down a hole and lost a life");
    }

    /** picks up item and moves player to space where item was
     *
     * @param oldPosx player x position before moving
     * @param oldPosy player y position before moving
     * @param newPosx player x position after moving
     * @param newPosy player y position after moving
     */
    public void pickUp(int oldPosx, int oldPosy, int newPosx, int newPosy)
    {
        Item item = (Item) room[newPosx][newPosy];
        player.addItemToInven(item);
        playerMove(oldPosx, oldPosy, newPosx, newPosy);
        System.out.println("You picked up an " + item.getName());
    }

    /** checks if new player position is out of bounds
     *
     * @param newPosx new possible player x position
     * @param newPosy new possible player y position
     * @return is new player postion out of bounds?
     */
    public boolean outOfBounds(int newPosx, int newPosy)
    {
        return newPosx < 0 || newPosy < 0 || newPosy >= room.length || newPosx >= room[0].length;
    }

    /** Makes space in room blank
     *
     * @param x x position of space that will be made blank
     * @param y y position of space that will be made blank
     */
    public void makeSpaceBlank(int x, int y)
    {
        room[x][y] = new BlankMapElement();
    }

    /** moves player and sets old player position to blank
     *
     * @param oldx player x position before moving
     * @param oldy player y position before moving
     * @param newx player x position after moving
     * @param newy player y position after moving
     */
    public void playerMove(int oldx, int oldy, int newx, int newy)
    {
        makeSpaceBlank(oldx,oldy);
        room[newx][newy] = player;
        playerPosx = newx;
        playerPosy = newy;
    }

    /** If the place the player is moving has a wall
     * the player has the choice to break the wall if they have a hammer item in their inventory
     * but if the player doesn't have a hammer or if they choose not to break the wall the player doesn't move
     *
     * @param oldPosx player x position before moving
     * @param oldPosy player y position before moving
     * @param newPosx new possible player x position
     * @param newPosy new possible player y position
     */
    public void wallMovement(int oldPosx, int oldPosy, int newPosx, int newPosy)
    {

        boolean stayStill = true;
        ArrayList<HammerItem> hammersInInven = player.hammersInInven();
        if(hammersInInven.size() != 0)
        {
            String ans = "";
            boolean spin = true;
            while (spin) {
                System.out.print("Do you want to break this wall? (Y)es or (N)o");
                ans = sc.nextLine();
                if(ans.length()>0) {
                    ans = ans.substring(0, 1).toLowerCase();
                }
                if(ans.equals("y") || ans.equals("n"))
                {
                    spin = false;
                } else
                {
                    System.out.println("Not a valid answer.");
                }
            }
            if(ans.equals("y"))
            {
                spin = true;
            }
            int anss = -1;
            while (spin) {
                System.out.println("Choose which hammer you want to use");
                Player.printHammers(hammersInInven);
                String temp = sc.nextLine();
                anss = Integer.parseInt(temp);
                anss--;
                if (anss >= 0 && anss < hammersInInven.size()) {
                    spin = false;
                } else
                {
                    System.out.println("Not a hammer number.");
                }
            }
            if(anss != -1)
            {
                room[newPosx][newPosy] = new BlankMapElement();
                hammersInInven.get(anss).setUses(hammersInInven.get(anss).getUses()-1);
                if(hammersInInven.get(anss).getUses() <= 0)
                {
                    player.removeItemFromInven(hammersInInven.get(anss));
                    System.out.println("This hammer ran out of uses.");
                }
                System.out.println("You broke the wall!");
                spin = true;
                ans = "";
                while (spin)
                {
                    System.out.println("Do you want to move? (Y)es or (N)o");
                    ans = sc.nextLine();
                    if(ans.length()>0) {
                        ans = ans.substring(0, 1).toLowerCase();
                    }
                    if(ans.equals("y") || ans.equals("n"))
                    {
                        if(ans.equals("y"))
                        {
                            stayStill = false;
                        }
                        spin = false;
                    }
                }
            }
        } else
        {
            System.out.println("You have no hammers to break this wall.");
        }
        if(!stayStill)
        {
            playerMove(oldPosx, oldPosy, newPosx, newPosy);
        }
    }

    /** If space player is moving has fire
     * the player has the choice to remove the fire if they have an extinguisher item in their inventory
     * but if the player doesn't have an extinguisher or if they choose not to remove the fore the player gets burned
     *
     * @param oldPosx player x position before moving
     * @param oldPosy player y position before moving
     * @param newPosx new possible player x position
     * @param newPosy new possible player y position
     * @return if player doesn't put fire out
     */
    public boolean fireMovement(int oldPosx, int oldPosy, int newPosx, int newPosy)
    {
        boolean stayStill = true;
        boolean burn = true;
        ArrayList<ExtiguisherItem> extiguishersInInven = player.extiguishersInInven();
        if(extiguishersInInven.size() != 0)
        {
            String ans = "";
            boolean spin = true;
            while (spin) {
                System.out.print("Do you want to remove the fire? (Y)es or (N)o");
                ans = sc.nextLine();
                if(ans.length()>0) {
                    ans = ans.substring(0, 1).toLowerCase();
                }
                if(ans.equals("y") || ans.equals("n"))
                {
                    spin = false;

                }
                else
                {
                    System.out.println("Not a valid answer.");
                }
            }
            if(ans.equals("y"))
            {
                spin = true;
            }
            int anss = -1;
            while (spin)
            {
                System.out.println("Choose which extinguisher you want to use");
                Player.printExtgusihers(extiguishersInInven);
                String temp = sc.nextLine();
                anss = Integer.parseInt(temp);
                anss--;
                if(anss >= 0 && anss < extiguishersInInven.size())
                {
                    spin = false;
                } else
                {
                    System.out.println("Not an extinguisher number.");
                }
            }
            if(anss != -1)
            {
                room[newPosx][newPosy] = new BlankMapElement();
                extiguishersInInven.get(anss).setUses(extiguishersInInven.get(anss).getUses()-1);
                if(extiguishersInInven.get(anss).getUses() <= 0)
                {
                    player.removeItemFromInven(extiguishersInInven.get(anss));
                    System.out.println("This extinguisher ran out of uses.");
                }
                System.out.println("You removed the fire!");
                burn = false;
                spin = true;
                ans = "";
                while (spin)
                {
                    System.out.println("Do you want to move? (Y)es or (N)o");
                    ans = sc.nextLine();
                    if(ans.length()>0) {
                        ans = ans.substring(0, 1).toLowerCase();
                    }
                    if(ans.equals("y") || ans.equals("n"))
                    {
                        if(ans.equals("y"))
                        {
                            stayStill = false;
                        }
                        spin = false;
                    }
                }
            }
            if(!stayStill)
            {
                playerMove(oldPosx, oldPosy, newPosx, newPosy);
            }
        }


        return burn;
    }

    public int getInput()
    {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }
}
