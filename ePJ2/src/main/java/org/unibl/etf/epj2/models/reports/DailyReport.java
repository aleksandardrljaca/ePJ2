package org.unibl.etf.epj2.models.reports;

import org.unibl.etf.epj2.models.receipt.Receipt;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DailyReport {
    /**
     * Hashmap representing total income for individual dates
     */
    private final Map<LocalDate, Double> incomeTotal;
    /**
     * Hashmap representing total discounts for individual dates
     */
    private final Map<LocalDate, Double> discountsTotal;
    /**
     * Hashmap representing promo total for individual dates
     */
    private final Map<LocalDate, Double> promoTotal;
    /**
     * Hashmap representing total distance for individual dates
     */
    private final Map<LocalDate, Double> distanceTotal;
    /**
     * Hashmap representing maintenance total for individual dates
     */
    private final Map<LocalDate, Double> maintenanceTotal;
    /**
     * Hashmap representing malfunction repairs total amount for individual dates
     */
    private final Map<LocalDate, Double> malfunctionRepairsTotal;

    /**
     * Constructor
     * @param incomeTotal Income map
     * @param discountsTotal Discounts map
     * @param promoTotal Promo map
     * @param distanceTotal Distance map
     * @param maintenanceTotal Maintenance map
     * @param malfunctionRepairsTotal Malfunction repairs map
     */
    public DailyReport(Map<LocalDate, Double> incomeTotal, Map<LocalDate, Double> discountsTotal,
                       Map<LocalDate, Double> promoTotal, Map<LocalDate, Double> distanceTotal,
                       Map<LocalDate, Double> maintenanceTotal, Map<LocalDate, Double> malfunctionRepairsTotal) {
        this.incomeTotal = incomeTotal;
        this.discountsTotal = discountsTotal;
        this.promoTotal = promoTotal;
        this.distanceTotal = distanceTotal;
        this.maintenanceTotal = maintenanceTotal;
        this.malfunctionRepairsTotal = malfunctionRepairsTotal;
    }

    /**
     * @return Returns income map
     */
    public Map<LocalDate, Double> getIncomeTotal() {
        return incomeTotal;
    }

    /**
     * @return Returns discounts map
     */

    public Map<LocalDate, Double> getDiscountsTotal() {
        return discountsTotal;
    }

    /**
     * @return Returns promo map
     */

    public Map<LocalDate, Double> getPromoTotal() {
        return promoTotal;
    }

    /**
     * @return Returns distance map
     */

    public Map<LocalDate, Double> getDistanceTotal() {
        return distanceTotal;
    }

    /**
     * @return Returns maintenance map
     */

    public Map<LocalDate, Double> getMaintenanceTotal() {
        return maintenanceTotal;
    }

    /**
     * @return Returns malfunction repairs cost map
     */

    public Map<LocalDate, Double> getMalfunctionRepairsTotal() {
        return malfunctionRepairsTotal;
    }

}
