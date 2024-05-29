package org.unibl.etf.epj2.models.receipt;

import org.unibl.etf.epj2.models.rent.Rent;
import org.unibl.etf.epj2.models.vehicle.Vehicle;
/** Represents a receipt object
 * @author Aleksandar Drljaca
 */
public class Receipt {
    /**
     * Base price of a rent
     */
    private double basePrice;
    /** Discount value if exists
     * Every tenth rent has discount
     */
    private double discount;
    /**
     * Discount promo value
     */
    private double promo;
    /**
     * Price based on distance (inside or outside the city)
     */
    private double distance;
    /**
     * Total price
     */
    private double totalPrice;
    /**
     * Rent that contains all necessary info
     */
    private Rent rent;

    /**
     * Constructor
     * @param rent Rent containing necessary info
     */
    public Receipt(Rent rent){
        this.rent=rent;
    }

    /**
     * @return Returns base price
     */
    public double getBasePrice() {
        return basePrice;
    }
    /**
     * Setter for base price
     */
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
    /**
     * @return Returns discount price
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Setter for discount price
     * @param discount Discount price
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Gets discount promo price
     * @return Return promo
     */
    public double getPromo() {
        return promo;
    }
    /**
     * Setter for promo
     */
    public void setPromo(double promo) {
        this.promo = promo;
    }

    /**
     * @return Price based on distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets distance price
     * @param distance Distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return Returns rent
     */
    public Rent getRent() {
        return rent;
    }

    /**
     * @param rent Rent
     */
    public void setRent(Rent rent) {
        this.rent = rent;
    }

    /**
     * @return Returns total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Setter for total price
     * @param totalPrice Total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return String representation of a receipt
     */
    @Override
    public String toString(){
        String malfunctionDesc="";
        if(rent.isMalfunction() && rent.getVehicle().getMalfunction()!=null)
            malfunctionDesc+="description: "+rent.getVehicle().getMalfunction().getDescription();
        return "BASE PRICE: "+basePrice+"\n------------------------------\n"+
                "DISCOUNT: ("+rent.isHasDiscount()+") => "+discount+"\n------------------------------\n"+
                "PROMO: ("+rent.isPromo()+") "+ promo +"\n------------------------------\n"+
                "DISTANCE: "+distance+"\n------------------------------\n"+
                "MALFUNCTION: ("+rent.isMalfunction()+") "+
                malfunctionDesc+"\n------------------------------\n"+
                "TOTAL PRICE: "+totalPrice;

    }
}
