package org.unibl.etf.epj2.models.reports;

/**
 * Representing summary report
 * @author Aleksandar Drljaca
 */
public class SummaryReport {
    /**
     * Total amount of income
     */
    private final double incomeTotal;
    /**
     * Total discounts amount
     */
    private final double discountsTotal;
    /**
     * Total promos amount
     */
    private final double promosTotal;
    /**
     * Total distances amount
     */
    private final double distanceAmountTotal;
    /**
     * Total maintenance cost
     */
    private final double maintenanceTotal;
    /**
     * Total repair costs
     */
    private final double malfunctionRepairsTotal;
    /**
     * Total company costs
     */
    private final double companyCostsTotal;
    /**
     * Tax
     */
    private final double taxTotal;

    /**
     * Constructor
     * @param incomeTotal Income
     * @param discountsTotal Discounts
     * @param promosTotal Promos
     * @param distanceAmountTotal Distances
     * @param maintenanceTotal Maintenance
     * @param malfunctionRepairsTotal Malfunction repairs
     * @param companyCostsTotal Company costs
     * @param taxTotal Tax
     */
    public SummaryReport(double incomeTotal, double discountsTotal, double promosTotal,
                         double distanceAmountTotal, double maintenanceTotal, double malfunctionRepairsTotal,
                         double companyCostsTotal, double taxTotal) {
        this.incomeTotal = incomeTotal;
        this.discountsTotal = discountsTotal;
        this.promosTotal = promosTotal;
        this.distanceAmountTotal = distanceAmountTotal;
        this.maintenanceTotal = maintenanceTotal;
        this.malfunctionRepairsTotal = malfunctionRepairsTotal;
        this.companyCostsTotal = companyCostsTotal;
        this.taxTotal = taxTotal;
    }

    /**
     * @return Returns total income
     */
    public double getIncomeTotal() {
        return incomeTotal;
    }

    /**
     * @return Returns total discounts
     */
    public double getDiscountsTotal() {
        return discountsTotal;
    }

    /**
     * @return Returns total promos
     */
    public double getPromosTotal() {
        return promosTotal;
    }

    /**
     * @return Returns total distances amount
     */
    public double getDistanceAmountTotal() {
        return distanceAmountTotal;
    }

    /**
     * @return Returns maintenance costs
     */
    public double getMaintenanceTotal() {
        return maintenanceTotal;
    }

    /**
     * @return Returns malfunction repairs cost
     */
    public double getMalfunctionRepairsTotal() {
        return malfunctionRepairsTotal;
    }

    /**
     * @return Returns company costs
     */
    public double getCompanyCostsTotal() {
        return companyCostsTotal;
    }

    /**
     * @return Returns total tax amount
     */
    public double getTaxTotal() {
        return taxTotal;
    }
}
