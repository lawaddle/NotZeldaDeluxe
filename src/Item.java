public class Item extends MapElement{
    /** Uses the item has */
    private int uses;
    /** Damage value of the item */
    private int damage;

    /**
     * Initiates an item object
     *
     * @param name       item name
     * @param uses       item uses
     * @param damage     item damage
     * @param mapDisplay item display on map
     * @param img
     */
    public Item (String name, int uses, int damage, String mapDisplay, String img)
    {
        super(name, mapDisplay, img);
        this.uses = uses;
        this.damage = damage;
    }


    /** Returns current uses of item
     *
     * @return item uses
     */
    public int getUses() {
        return uses;
    }

    /** Returns current damage of item
     *
     * @return item damage
     */
    public int getDamage() {
        return damage;
    }

    /** Sets number of uses of item to new value
     *
     * @param uses new item uses
     */
    public void setUses(int uses) {
        this.uses = uses;
    }



    /** Returns string that contains values of name, uses,
     * and damage instance variables (the item's state)
     *
     * @return String representation of the item's state
     */
    @Override
    public String toString()
    {
        return super.toString() + ", Uses: " + uses + ", Damage: " + damage;
    }
}
