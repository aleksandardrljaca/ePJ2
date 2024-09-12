package org.unibl.etf.epj2.models.document;

import java.util.UUID;

/**
 * @author Aleksandar Drljaca
 * Represents a passport that must be submitted before renting an electric car
 */
public class Passport {
    /**
     * Passport number
     */
    private final String number;

    /**
     * Constructor
     * Creates a passport
     */
    public Passport(){
        this.number= UUID.randomUUID().toString().split("-")[0].toUpperCase();
    }

    /**
     * @return Returns passport number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @return Returns string representation of passport object
     */
    @Override
    public String toString(){
        return number;
    }
}
