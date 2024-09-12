package org.unibl.etf.epj2.models.user;

import java.util.Random;
import java.util.UUID;

/**
 * Represents a user that rents the vehicle
 */
public abstract class User {
    /**
     * User's name
     */
    protected String name;
    /**
     * Driving licence number (generated on request -> renting an electric car)
     */
    protected String driverLicence;

    /**
     * Creates a user
     * @param name User's name
     */
    public User(String name){
        this.name=name;
        this.driverLicence=null;
    }

    /**
     * Creating driving licence for the user
     */
    public void setDriverLicence(){
        this.driverLicence= UUID.randomUUID().toString().split("-")[0].toUpperCase();
    }

    /**
     * Gets user's name
     * @return Return name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets user's driver licence number
     * @return Return driver licence
     */
    public String getDriverLicence(){
        return driverLicence;
    }

    /**
     * @return Returns string representation of user object
     */
    @Override
    public String toString(){
        return name;
    }

}
