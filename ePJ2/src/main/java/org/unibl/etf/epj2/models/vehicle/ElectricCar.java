package org.unibl.etf.epj2.models.vehicle;

import java.time.LocalDate;

/** Represents an electric car
 * @author Aleksandar Drljaca
 */
public class ElectricCar extends Vehicle implements TransportingPeople{
    /** Describes the electric car
     */
    private final String description;
    /**
     * Date the vehicle has been purchased
     */
    private final LocalDate procurementDate;

    /**
     * Creates an electric car with specified attributes
     * @param id The electric car's id
     * @param manufacturer The electric car's manufacturer
     * @param model The electric car's model
     * @param procurementCost The electric car's procurement cost
     * @param batteryLevel The electric car's current battery level
     * @param description The electric car's description
     * @param procurementDate The date electric car's procurement
     */
    public ElectricCar(String id, String manufacturer, String model, double procurementCost, int batteryLevel, String description, LocalDate procurementDate) {
        super(id, manufacturer, model, procurementCost, batteryLevel);
        this.description=description;
        this.procurementDate = procurementDate;
    }

    /**
     * Gets the car's description
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Returns date
     */
    public LocalDate getProcurementDate() {
        return procurementDate;
    }
    /**
     * @return Returns string representation of electric car object
     */
    @Override
    public String toString(){
        return id + " "+manufacturer+" "+model+" "+procurementCost+" "+procurementDate+" "+batteryLevel+" "+description;
    }
}
