package org.unibl.etf.epj2.models.user;

import org.unibl.etf.epj2.models.document.Passport;

/**
 * @author Aleksandar Drljaca
 * Represents a foreign resident user
 */
public class ForeginResidentUser extends User {
    /**
     * User's passport
     */
    private final Passport passport;

    /**
     * Creates a foreign resident user
     * @param name User's name
     */
    public ForeginResidentUser(String name) {
        super(name);
        this.passport=new Passport();
    }

    /**
     * @return Returns passport
     */
    public Passport getPassport() {
        return passport;
    }

    /**
     * @return Returns string representation of foreign resident user object
     */
    @Override
    public String toString(){
        return name+" "+passport.toString();
    }
}
