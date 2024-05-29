package org.unibl.etf.epj2.models.vehicle;

/** Represents an electric bicycle
 * @author Aleksandar Drljaca
 */
public class ElectricBicycle extends Vehicle{
    /**
     * autonomy - max range on a single charge
     */
    private final int autonomy;

    /**
     * Creates an electric bicycle with specified attributes
     * @param id The electric bicycle's id
     * @param manufacturer The electric bicycle's manufacturer
     * @param model The electric bicycle's model
     * @param procurementCost The electric bicycle's procurement cost
     * @param batteryLevel The electric bicycle's current battery level
     * @param autonomy The electric bicycle's max range on a single charge
     */
    public ElectricBicycle(String id, String manufacturer, String model, double procurementCost, int batteryLevel,int autonomy) {
        super(id, manufacturer, model, procurementCost, batteryLevel);
        this.autonomy=autonomy;
    }

    /**
     * Gets the vehicle's autonomy
     * @return Returns autonomy
     */
    public int getAutonomy() {
        return autonomy;
    }
    /**
     * @return Returns string representation of electric bicycle object
     */
    @Override
    public String toString(){
        return id + " "+manufacturer+" "+model+" "+procurementCost+" "+batteryLevel+" "+autonomy;
    }
}
