package org.unibl.etf.epj2.utils;

import org.unibl.etf.epj2.models.receipt.Receipt;
import org.unibl.etf.epj2.models.reports.DailyReport;
import org.unibl.etf.epj2.models.reports.SummaryReport;
import org.unibl.etf.epj2.models.vehicle.ElectricBicycle;
import org.unibl.etf.epj2.models.vehicle.ElectricCar;
import org.unibl.etf.epj2.models.vehicle.ElectricScooter;
import org.unibl.etf.epj2.models.vehicle.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Util class used to create financial reports
 * @author Aleksandar Drljaca
 */
public class FinancialReportUtil {
    /**
     * Method to generate summary report
     * Contains data: incomeTotal,discountsTotal,promoTotal,distanceTotal
     * maintenanceTotal,malfunctionRepairsTotal
     * Additionally contains info for companyCostsTotal,taxTotal
     * @return Summary report
     */
    public static SummaryReport generateSummaryFinancialReport() {
        List<Receipt> receipts = ReceiptUtil.receipts;
        double incomeTotal = receipts.stream().mapToDouble(Receipt::getTotalPrice).sum();
        double discountsTotal = receipts.stream().mapToDouble(Receipt::getDiscount).sum();
        double promoTotal = receipts.stream().mapToDouble(Receipt::getPromo).sum();
        double distanceTotal = receipts.stream().mapToDouble(Receipt::getDistance).sum();
        double maintenanceTotal = incomeTotal * 0.2;
        double malfunctionRepairsTotal = receipts.stream().filter(r -> r.getRent().isMalfunction()).mapToDouble(r -> {
            Vehicle vehicle = r.getRent().getVehicle();
            if (vehicle instanceof ElectricCar)
                return 0.07 * vehicle.getProcurementCost();
            else if (vehicle instanceof ElectricBicycle)
                return 0.04 * vehicle.getProcurementCost();
            else if (vehicle instanceof ElectricScooter)
                return 0.02 * vehicle.getProcurementCost();
            return 0.0;
        }).sum();
        double companyCostsTotal = 0.2 * incomeTotal;
        double taxTotal = (incomeTotal - maintenanceTotal - malfunctionRepairsTotal - companyCostsTotal) * 0.1;
        return new SummaryReport(incomeTotal, discountsTotal, promoTotal, distanceTotal, maintenanceTotal, malfunctionRepairsTotal, companyCostsTotal, taxTotal);
    }

    /**
     * Method to generate summary report
     * Contains data: incomeTotal,discountsTotal,promoTotal,distanceTotal
     * maintenanceTotal,malfunctionRepairsTotal
     * Every item is calculated for the individual date
     * @return Daily report
     */
    public static DailyReport generateDailyFinancialReport() {
        List<Receipt> receipts = ReceiptUtil.receipts;
        Map<LocalDate, Double> incomeTotal = receipts.stream().collect(Collectors.groupingBy(r -> r.getRent().getDate(), Collectors.summingDouble(Receipt::getTotalPrice)));
        Map<LocalDate, Double> discountsTotal = receipts.stream().collect(Collectors.groupingBy(r -> r.getRent().getDate(), Collectors.summingDouble(Receipt::getDiscount)));
        Map<LocalDate, Double> promoTotal = receipts.stream().collect(Collectors.groupingBy(r -> r.getRent().getDate(), Collectors.summingDouble(Receipt::getPromo)));
        Map<LocalDate, Double> distanceTotal = receipts.stream().collect(Collectors.groupingBy(r -> r.getRent().getDate(), Collectors.summingDouble(Receipt::getDistance)));
        Map<LocalDate, Double> maintenanceTotal = new HashMap<>();
        incomeTotal.entrySet().forEach(e -> {
            maintenanceTotal.put(e.getKey(), e.getValue() * 0.2);
        });
        Map<LocalDate, Double> malfunctionRepairsTotal = new HashMap<>();
        receipts.stream().filter(r -> r.getRent().isMalfunction()).collect(Collectors.groupingBy(r -> r.getRent().getDate()))
                .entrySet().forEach(e -> {
                    double malfRepairDayTotal = e.getValue().stream().mapToDouble(r -> {
                        Vehicle vehicle = r.getRent().getVehicle();
                        if (vehicle instanceof ElectricCar)
                            return 0.07 * vehicle.getProcurementCost();
                        else if (vehicle instanceof ElectricBicycle)
                            return 0.04 * vehicle.getProcurementCost();
                        else if (vehicle instanceof ElectricScooter)
                            return 0.02 * vehicle.getProcurementCost();
                        return 0.0;
                    }).sum();
                    malfunctionRepairsTotal.put(e.getKey(), malfRepairDayTotal);
                });
        return new DailyReport(incomeTotal, discountsTotal, promoTotal, distanceTotal, maintenanceTotal, malfunctionRepairsTotal);
    }

    /**
     * Method to find the vehicle that produced highest loss
     * Extracting receipts where rents used specific vehicle type for transportation
     * Grouping extracted receipts by vehicle id and summing all total prices from
     * receipts where transportation is done by that vehicle
     * Next step is to sort result and return first element which has lowest produced value
     * @param vehicleClass Specifying wanted vehicle type
     */
    public static void findVehicleWithHighestLoss(Class vehicleClass) {
        List<Receipt> vehicleReceipts = new ArrayList<>();
        // extracting receipts where specified type of vehicle is used for transportation
        ReceiptUtil.receipts.stream().collect(Collectors.groupingBy(r -> r.getRent().getVehicle().getClass().getSimpleName()))
                .entrySet().forEach(e -> {
                    if (e.getKey().equals(vehicleClass.getSimpleName()))
                        vehicleReceipts.addAll(e.getValue());
                });
        //  grouping vehicles of certain type by ID and summing receipt's total earnings for rents where vehicle with this id was driven
        // sorting
        // the lowest value => requested vehicle
        List<Map.Entry<String,Double>> l=vehicleReceipts.stream().collect(Collectors.groupingBy(r -> r.getRent().getVehicle().getId()
                        , Collectors.summingDouble(Receipt::getTotalPrice))).entrySet()
                .stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        // serializing report
        FileUtil.serialize(ReceiptUtil.vehicles.get(l.get(0).getKey()));
    }
}

