package org.unibl.etf.epj2.models.vehicle;

import org.unibl.etf.epj2.models.malfunction.Malfunction;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Aleksandar Drljaca
 */
public abstract class Vehicle implements Serializable {
    /**
     * ID
     */
    protected String id;
    /**
     * Manufacturer
     */
    protected String manufacturer;
    /**
     * Model
     */
    protected String model;
    /**
     * Procurement cost
     */
    protected double procurementCost;
    /**
     * Battery level
     */
    protected int batteryLevel;
    /**
     * Malfunction
     * Null by default
     * Set if malfunction occurs
     */
    protected Malfunction malfunction;

    /**
     * Constructor with parameters
     * @param id The vehicle's id
     * @param manufacturer The vehicle's manufacturer
     * @param model The vehicle's model
     * @param procurementCost The vehicles' procurement cost
     * @param batteryLevel The vehicle's current battery level
     */
    public Vehicle(String id, String manufacturer, String model, double procurementCost, int batteryLevel) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.procurementCost = procurementCost;
        this.batteryLevel = batteryLevel;
    }

    /**
     *
     * @return Returns vehicle's id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return Returns vehicle's manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @return Returns vehicle's model
     */
    public String getModel() {
        return model;
    }

    /**
     * @return Returns procurement cost of the vehicle
     */
    public double getProcurementCost() {
        return procurementCost;
    }

    /**
     *
     * @return Returns current battery level
     */
    public int getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * Creates malfunction report/object for this vehicle
     * @param description The vehicle's malfunction description
     */
    public void setMalfunction(LocalDate date, LocalTime time, String description){
        this.malfunction=new Malfunction(date,time,description);
    }

    /**
     * Gets this vehicle's malfunction
     * @return Malfunction object
     */
    public Malfunction getMalfunction(){
        return malfunction;
    }

    /**
     * Increases battery level by specified amount
     * @param charge Amount of charge received
     */
    protected void increaseBatteryLevel(int charge){
        this.batteryLevel+=charge;
    }

    /**
     * Decreases current battery level by specified amount
     * @param charge Amount of lost charge
     */
    public void decreaseBatteryLevel(int charge){
       if(this.batteryLevel>0)
           this.batteryLevel-=charge;
    }

    /**
     * @return Returns string representation of vehicle
     */
    @Override
    public String toString(){
        return id;
    }

}
