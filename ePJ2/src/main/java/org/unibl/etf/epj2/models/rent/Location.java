package org.unibl.etf.epj2.models.rent;

/**
 * @author Aleksandar Drljaca
 */
public class Location {
    /**
     * X-axis value on the map
     */
    private final int x;
    /**
     * Y-axis value on the map
     */
    private final int y;

    /**
     * Creates a location
     * @param x X axis value
     * @param y Y axis value
     */
    public Location(int x, int y){
        this.x=x;
        this.y=y;
    }

    /**
     *
     * @return Returns X-axis value
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return Returns Y-axis value
     */
    public int getY() {
        return y;
    }

    /**
     * Overridden toString method
     * @return Returns string representation of location object
     */
    @Override
    public String toString(){
        return "location=("+x+","+y+")";
    }

}
