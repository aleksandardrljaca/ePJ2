package org.unibl.etf.epj2.models.user;

import org.unibl.etf.epj2.models.document.IdentityCard;


/**
 * @author Aleksandar Drljaca
 * Represents a Local resident user
 */
public class LocalResidentUser extends User {
    /*
        Local resident's identity card
     */
    private final IdentityCard identityCard;

    /**
     * Creates a local resident user
     * @param name User's name
     */
    public LocalResidentUser(String name){
        super(name);
        this.identityCard=new IdentityCard();
    }

    /**
     * @return Returns user's identity card
     */
    public IdentityCard getIdentityCard() {
        return identityCard;
    }

    /**
     * @return Returns string representation of local resident user object
     */
    @Override
    public String toString(){
        return name+" "+identityCard.toString();
    }
}
