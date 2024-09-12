package org.unibl.etf.epj2;


import org.unibl.etf.epj2.models.rent.Location;
import org.unibl.etf.epj2.models.rent.Rent;
import org.unibl.etf.epj2.models.vehicle.ElectricBicycle;
import org.unibl.etf.epj2.models.vehicle.ElectricCar;
import org.unibl.etf.epj2.models.vehicle.ElectricScooter;
import org.unibl.etf.epj2.utils.FinancialReportUtil;
import org.unibl.etf.epj2.utils.PropertiesUtil;
import org.unibl.etf.epj2.utils.ReceiptUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Simulation class
 * @author Aleksandar Drljaca
 */
public class Simulation {
    /**
     * Painting fields callback
     */
    public static BiConsumer<Integer,String> paint;
    /**
     * Removing paint from fields callback
     */
    public static Consumer<Integer> removePaint;
    /**
     * Enable/Disable buttons
     * If simulation is started - disable
     * When simulation is finished - enable
     */
    public static Consumer<Boolean> buttonsOnOffSwitch;
    /**
     * Counter for to count every tenth rent being processed
     */
    public static int discountRentCounter=0;
    /**
     * List<Integer> to represent inner fields on map
     * Needed to get the right distance value
     *
     */
    public static List<Integer> narrowDistanceFields;

    /**
     * Initializing list
     * Used from Receipt class in narrowWideDistance method to calculate right distance
     */
    static{
        narrowDistanceFields=new ArrayList<>();
        for (int row = 6; row <= 15; row++) {
            for (int col = 6; col <= 15; col++) {
                int position = (row - 1) * 20 + col;
                narrowDistanceFields.add(position);
            }
        }
    }
    /**
     * Simulation method
     * Rents are divided into separate lists where each list contains rents with one date only
     * Then the rents are grouped by time and then sorted, because grouping by won't achieve sorted ordering
     * Grouping and sorting is performed for each list (separate list that contains only rents on a single date)
     * e.g. groupedByTime => 11:00=[R1,R2], 9:30=[R1,R2]
     * e.g. sortedGroupedByTime => 9:30=[R1,R2], 11:00=[R1,R2]
     * Because the map is now sorted, we can start iterating through it
     * Getting list of rents for specific time and run processing logic for each rent
     * Processing will start after countDownLatch count is equal to zero
     * Processing includes:
     *          - checking if the current processing rent is tenth in a row
     *          - checking if rent had a malfunction, so it can be reported in vehicle history (malfunction object/attribute)
     *          - generate movement path
     *          - simulating user's movement from start to end location
     *          - creating receipt - rent itself contains need info to create receipt
     *                             - path list is needed to determine whether user is moving inside or outside the city
     */
    public static void startSimulation(){
        buttonsOnOffSwitch.accept(false);
        List<List<Rent>> separateDatesLists=makeSeparateDatesLists();
        for(List<Rent> rentsForDate:separateDatesLists){
            rentsForDate.stream().collect(Collectors.groupingBy(Rent::getTime))
                    .entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e->{
                        List<Rent> rents=e.getValue();
                        CountDownLatch countDownLatch=new CountDownLatch(1);
                        for(Rent rent:rents){
                            discountRentCounter++;
                            // every tenth rent has a discount
                            if(discountRentCounter==10){
                                rent.setHasDiscount(true);
                                discountRentCounter=0;
                            }
                            // setting info that vehicle had a malfunction
                            if(rent.isMalfunction()){
                                rent.getVehicle().setMalfunction(rent.getDate(),rent.getTime(),PropertiesUtil.getDefaultMalfunctionDescription());
                                ReceiptUtil.vehicles.get(rent.getVehicle().getId()).setMalfunction(rent.getDate(),rent.getTime(),PropertiesUtil.getDefaultMalfunctionDescription());
                            }
                            new Thread(()->{
                                try {
                                    countDownLatch.await();
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }
                                //logic
                                // generating movement path
                                List<Integer> path=generateMovementPath(rent);
                                System.out.println(rent);
                                simulateMovement(rent,path);
                                // passing path to create receipt - uses method for comparing path and distance fields
                                // comparison needs to be done because of getting the right distance value on the receipt
                                ReceiptUtil.createReceipt(rent,path);
                            }).start();
                        }
                        countDownLatch.countDown();
                        sleep(5000);
                    });
            sleep(5000);
        }
        generateLossesReport();
        buttonsOnOffSwitch.accept(true);


    }

    /**
     * Storing rents from rent list to separate lists
     * where each list contains rents with the same single date
     * @return List<List<Rent>>
     */
    private static List<List<Rent>> makeSeparateDatesLists(){
        List<List<Rent>> singleDateLists=new ArrayList<>();
        List<Rent> rents=ReceiptUtil.rents;
        LocalDate currDate=rents.get(0).getDate();
        int listCounter=-1;
        for(Rent rent:rents){
            if(singleDateLists.isEmpty()){
                singleDateLists.add(new ArrayList<>());
                listCounter++;
            }
            if(!currDate.equals(rent.getDate())){
                currDate=rent.getDate();
                singleDateLists.add(new ArrayList<>());
                listCounter++;
            }
            singleDateLists.get(listCounter).add(rent);
        }
        return singleDateLists;
    }

    /**
     * Generating path from A to B
     * Iterating through button position/index list (fields)
     * Painting button to simulate movement on the field
     * @param rent Rent to simulate on map - need rent to get vehicle info
     * @param fields List of fields to paint
     */
    private static void simulateMovement(Rent rent,List<Integer> fields){
        for(Integer i:fields){
            String userNBatteryLevel=rent.getUser().getName()+" "+rent.getVehicle().getBatteryLevel()+"%";
            rent.getVehicle().decreaseBatteryLevel(1);
            ReceiptUtil.vehicles.get(rent.getVehicle().getId()).decreaseBatteryLevel(1);
            paint.accept(i,userNBatteryLevel);
            //System.out.println(rent.getVehicle().getId() +" on position "+i +" battery level= "+rent.getVehicle().getBatteryLevel()+"%");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            removePaint.accept(i);
        }
    }

    /**
     * Calculates button index in grid pane
     * @param x Location's x coordinate
     * @param y Location's y coordinate
     * @return Returns a number that represents index of a button in grid pane
     */
    private static int mapCoordinateToButtonPosition(int x,int y){
        return x*20 + (y+1);
    }

    /**
     * Method to pause simulation
     * @param mills Pause time
     */
    private static void sleep(int mills){
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to generate losses report
     * Generates report for each vehicle type that produced most losses
     * Found vehicles are serialized
     */
    private static void generateLossesReport(){
        FinancialReportUtil.findVehicleWithHighestLoss(ElectricCar.class);
        FinancialReportUtil.findVehicleWithHighestLoss(ElectricBicycle.class);
        FinancialReportUtil.findVehicleWithHighestLoss(ElectricScooter.class);
    }

    /**
     * Method for generating movement path from start to end location
     * Used in simulateMovement method
     * @param rent Rent containing info about start and end location
     * @return Returns List<Integer> - path list to traverse/paint
     */
    private static List<Integer> generateMovementPath(Rent rent){
        List<Integer> fields=new ArrayList<>();
        Location start=rent.getStartLocation();
        Location end=rent.getEndLocation();
        Location currLocation=start;
        while(!(currLocation.getX()==end.getX() && currLocation.getY()==end.getY())){
            fields.add(mapCoordinateToButtonPosition(currLocation.getX(),currLocation.getY()));

            int currentX = currLocation.getX();
            int currentY = currLocation.getY();

            if (currentX < end.getX()) {
                currLocation = new Location(currentX + 1, currentY);
            } else if (currentX > end.getX()) {
                currLocation = new Location(currentX - 1, currentY);
            } else if (currentY < end.getY()) {
                currLocation = new Location(currentX, currentY + 1);
            } else if (currentY > end.getY()) {
                currLocation = new Location(currentX, currentY - 1);
            }
        }

        fields.add(mapCoordinateToButtonPosition(end.getX(),end.getY()));
        return fields;
    }

}
