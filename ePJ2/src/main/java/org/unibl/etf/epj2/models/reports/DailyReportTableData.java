package org.unibl.etf.epj2.models.reports;

import java.time.LocalDate;

/**
 * Class to represent daily report data from all those hashmaps created when daily report is created
 * Class is created to make loading tableview with data possible
 * Not the best solution but...
 * @author Aleksandar Drljaca
 */
public class DailyReportTableData {
    /**
     * Date (hashmap key - reports are grouped by date)
     */
    private final LocalDate date;
    /**
     * Income value
     */
    private final double income;
    /**
     * Discounts
     */
    private final double discounts;
    /**
     * Promo
     */
    private final double promo;
    /**
     * Distance
     */
    private final double distance;
    /**
     * Maintenance
     */
    private final double maintenance;
    /**
     * Repairs
     */
    private final double repairs;

    /**
     * Constructor
     * @param date Date
     * @param income Income
     * @param disounts Discounts
     * @param promo Promo
     * @param distance Distance
     * @param maintenance Maintenance
     * @param repairs Repairs
     */
    public DailyReportTableData(LocalDate date, double income, double disounts, double promo, double distance, double maintenance, double repairs) {
        this.date = date;
        this.income = income;
        this.discounts = disounts;
        this.promo = promo;
        this.distance = distance;
        this.maintenance = maintenance;
        this.repairs = repairs;
    }

    /**
     * @return Date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return Income
     */
    public double getIncome() {
        return income;
    }

    /**
     * @return Returns discounts
     */
    public double getDiscounts() {
        return discounts;
    }

    /**
     * @return Returns promo
     */
    public double getPromo() {
        return promo;
    }

    /**
     * @return Return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @return Returns maintenance
     */
    public double getMaintenance() {
        return maintenance;
    }

    /**
     * @return Returns repairs
     */
    public double getRepairs() {
        return repairs;
    }
}
