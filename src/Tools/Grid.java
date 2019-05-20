package Tools;

/**
 * Encapsulation class responsible for getting and setting the total amount of lines to be drawn for the grid
 */

public class Grid {

    private int total;// private variable for keeping track of the total amount of grid lines.

    /**
     * @param setting amount of lines to be drawn for the grid
     */
    public void setSetting(int setting) {
        this.total = setting;
    }

    /**
     *
     * @return the total amount of lines to be drawn.
     */
    public int getSetting(){ return this.total; }
}
