public class MapElement {
    /** name of item */
    private String name;
    /** String of item display on map */
    private String mapDisplay;

    /**
     * Initiates a map element object
     *
     * @param name map element name
     * @param mapDisplay map element display on map
     */
    public MapElement (String name, String mapDisplay)
    {
        this.name = name;
        this.mapDisplay = mapDisplay;
    }

    /** Returns current name of item
     *
     * @return map element name
     */
    public String getName() {
        return name;
    }

    /** sets name of map element
     *
     * @param name new name of map element
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns map element display on map
     *
     * @return map element display on map
     */
    public String getMapDisplay() {
        return mapDisplay;
    }


    /** returns state of map element name as string
     *
     * @return String representation of the object's state (only the name)
     */
    @Override
    public String toString()
    {
        return "Name: " + name;
    }
}
