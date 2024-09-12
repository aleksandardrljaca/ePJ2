package org.unibl.etf.epj2.models.malfunction;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/** Represents a vehicle's malfunction
 * @author Aleksandar Drljaca
 */
public class Malfunction implements Serializable {
    /**
     * date of malfunction
     */
    private final LocalDate date;
    /**
     * time of malfunction
     */
    private final LocalTime time;
    /**
     * description of malfunction
     */
    private final String description;

    /**
     * Creates a malfunction object describing issues that occurred
     * @param date Date malfunction occurred
     * @param time Time malfunction occureed
     * @param description Description
     */
    public Malfunction(LocalDate date, LocalTime time, String description){
        this.date = date;
        this.time = time;
        this.description=description;
    }

    /**
     *
     * @return Returns the date of malfunction
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     *
     * @return Returns the time of malfunction
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     *
     * @return Returns the description of malfunction
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Returns string representation of malfunction object
     */
    @Override
    public String toString(){
        return "Malfunction: "+date+" "+time+" "+description;
    }
}
