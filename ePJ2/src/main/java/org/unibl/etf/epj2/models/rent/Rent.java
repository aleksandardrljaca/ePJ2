package org.unibl.etf.epj2.models.rent;

import org.unibl.etf.epj2.models.user.User;
import org.unibl.etf.epj2.models.vehicle.Vehicle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * @author Aleksandar Drljaca
 * Represents a single rent of vehicle
 */
public class Rent {
    /**
     * Date when vehicle is rented
     */
    private final LocalDate date;
    /**
     * Time when vehicle is rented
     */
    private final LocalTime time;
    /**
     * User
     * Type of user is determined randomly when creating a rent
     */
    private final User user;
    /**
     * Starting location on map
     */
    /**
     * Rented vehicle
     */
    private final Vehicle vehicle;
    private final Location startLocation;
    /**
     * Ending location
     */
    private final Location endLocation;
    /**
     * Duration from A-B
     */
    private final int duration;

    /**
     * Indicates malfunction
     */
    private final boolean malfunction;
    /**
     * Indicates promo is available
     */
    private final boolean promo;
    /**
     * Indicates if rent has a discount
     * Only true if tenth in a row for processing
     */
    private boolean hasDiscount;

    /**
     * Creates a rent object
     * @param date Date
     * @param time Time
     * @param user User
     * @param startLocation Start location
     * @param endLocation End location
     * @param duration Duration in seconds
     * @param malfunction Malfunction indicator
     * @param promo Promo availability indicator
     */
    public Rent(LocalDate date, LocalTime time, User user, Vehicle vehicle, Location startLocation, Location endLocation, int duration, boolean malfunction, boolean promo) {
        this.date = date;
        this.time = time;
        this.user = user;
        this.vehicle = vehicle;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.duration = duration;
        this.malfunction = malfunction;
        this.promo = promo;
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
     * @return Returns user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return Returns vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * @return Returns start location
     */
    public Location getStartLocation() {
        return startLocation;
    }

    /**
     * @return Returns end location
     */
    public Location getEndLocation() {
        return endLocation;
    }

    /**
     * @return Returns duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @return Returns malfunction flag
     */
    public boolean isMalfunction() {
        return this.malfunction;
    }

    /**
     * @return Returns promo flag
     */
    public boolean isPromo() {
        return promo;
    }

    /**
     * Every tenth rent has a discount
     * Set when processing rents
     * @return Returns boolean value
     */
    public boolean isHasDiscount() {
        return hasDiscount;
    }

    /**
     * Every tenth rent has a discount
     * Set when processing rents
     */
    public void setHasDiscount(boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    /**
     * @return Returns string representation of rent object
     */
    @Override
    public String toString() {
        return date + " " + time + " " + user.getName() + " "+vehicle.getId()+" " + startLocation + " " +
                endLocation + " duration: " + duration + " malfunction: " + malfunction + " promo: " + promo;
    }

    /**
     *
     * @return Returns hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(date, time, user.getName(), vehicle.getId());
    }

    /**
     * @param o Object to compare
     * @return Return boolean value
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rent rent = (Rent) o;
        return date.equals(rent.date) &&
                time.equals(rent.time) &&
                (user.getName().equals(rent.user.getName()) || vehicle.getId().equals(rent.vehicle.getId()));
    }


}
