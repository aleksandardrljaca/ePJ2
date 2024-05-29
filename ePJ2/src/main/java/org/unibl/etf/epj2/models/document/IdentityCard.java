package org.unibl.etf.epj2.models.document;

import java.util.UUID;

/**
 * @author Aleksandar Drljaca
 * Represents user's identity card that must be submitted before renting an electric car
 */
public class IdentityCard {
    /** Card's id
     */
    private final String id;

    /**
     * Creates an identity card
     */
    public IdentityCard(){
        this.id= UUID.randomUUID().toString().split("-")[0].toUpperCase();
    }

    /**
     * @return Returns card ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return Returns string representation of identity card object
     */
    @Override
    public String toString(){
        return id;
    }
}
