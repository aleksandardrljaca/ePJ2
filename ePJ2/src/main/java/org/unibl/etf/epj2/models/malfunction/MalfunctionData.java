package org.unibl.etf.epj2.models.malfunction;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * MalfunctionData - used to populate table in malfunction window
 * @author Aleksandar Drljaca
 */
public class MalfunctionData {
    /**
     * Vehicle type
     */
    private final String vehicleType;
    /**
     * Vehicle id
     */
    private final String vehicleID;
    /**
     * Date malfunction occurred
     */
    private final LocalDate date;
    /**
     * Time malfunction occurred
     */
    private final LocalTime time;
    /**
     * Malfunction description
     */
    private final String description;

    /**
     * Constructor
     * @param vehicleType Vehicle type
     * @param vehicleID Vehicle ID
     * @param date Date
     * @param time Time
     * @param description Description
     */
    public MalfunctionData(String vehicleType, String vehicleID, LocalDate date, LocalTime time, String description) {
        this.vehicleType = vehicleType;
        this.vehicleID = vehicleID;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    /**
     * @return Returns type of vehicle
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * @return Returns vehicle id
     */
    public String getVehicleID() {
        return vehicleID;
    }

    /**
     * @return Returns date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return Returns time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * @return Returns description
     */
    public String getDescription() {
        return description;
    }
}
