package org.unibl.etf.epj2.models.vehicle;

/** Represents an electric scooter
 * @author Aleksandar Drljaca
 */
public class ElectricScooter extends Vehicle{
    /**
     * max speed scooter the scooter can achieve
     */
    private final int maxSpeed;

    /**
     * Creates an electric scooter with specified attributes
     * @param id The electric scooter's id
     * @param manufacturer The electric scooter's manufacturer
     * @param model The electric scooter's model
     * @param procurementCost The electric scooter's procurement cost
     * @param batteryLevel The electric scooter's current battery level
     * @param maxSpeed The electric scooter's max speed
     */
    public ElectricScooter(String id, String manufacturer, String model, double procurementCost, int batteryLevel, int maxSpeed) {
        super(id, manufacturer, model, procurementCost, batteryLevel);
        this.maxSpeed=maxSpeed;
    }

    /**
     * Gets the scooter's max speed
     * @return Max speed
     */
    public int getMaxSpeed() {
        return maxSpeed;
    }
    /**
     * @return Returns string representation of electric scooter object
     */
    @Override
    public String toString(){
        return id + " "+manufacturer+" "+model+" "+procurementCost+" "+batteryLevel+" "+maxSpeed;
    }
}
