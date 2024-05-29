package org.unibl.etf.epj2.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.unibl.etf.epj2.Simulation;
import org.unibl.etf.epj2.models.receipt.Receipt;
import org.unibl.etf.epj2.models.rent.Location;
import org.unibl.etf.epj2.models.rent.Rent;
import org.unibl.etf.epj2.models.user.ForeginResidentUser;
import org.unibl.etf.epj2.models.user.LocalResidentUser;
import org.unibl.etf.epj2.models.vehicle.ElectricBicycle;
import org.unibl.etf.epj2.models.vehicle.ElectricCar;
import org.unibl.etf.epj2.models.vehicle.ElectricScooter;
import org.unibl.etf.epj2.models.vehicle.Vehicle;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Aleksandar Drljaca
 */
public class ReceiptUtil {
    /**
     * Collection of rents read from provided csv file (sorted)
     * Will be used in further simulation
     */
    public static List<Rent> rents;
    /**
     * Collection of vehicles read from provided csv file
     * Will be used in further simulation
     */
    public static HashMap<String, Vehicle> vehicles;
    /**
     * Collection of receipt
     * Receipts will be added to this collection after simulation of rent finishes
     */
    public static List<Receipt> receipts=new ArrayList<>();

    static{
        rents=RentUtil.getRents();
        vehicles=ParserUtil.parseVehicleDataCSV();
    }

    /**
     * Method to create a receipt
     * Used in simulation after illustrating a rent
     * Each receipt contains basePrice,discount,promo,distance,totalPrice (all double values)
     * which are calculated in this method
     * FORMULAS:
     *      distance=basePrice*DISTANCE_NARROW or basePrice*DISTANCE_WIDE
     *      discount=basePrice*DISCOUNT (every tenth rent has a discount)
     *      promo=basePrice*DISCOUNT_PROM (if rent has a promo)
     *      basePrice=rent_duration* UNIT_PRICE(car,bicycle,scooter)
     *      totalPrice=(basePrice*distance)-discount-promo | if rent had a malfunction then totalPrice=0
     * @param rent Rent containing all information
     */
    public static void createReceipt(Rent rent,List<Integer> movementPath){
        Receipt receipt=new Receipt(rent);
        double totalPrice;
        if(rent.isMalfunction()) {
            totalPrice = 0.0;
            receipt.setTotalPrice(totalPrice);
        }
        else {
            double basePrice = calculateBasePrice(rent);
            double discount=0.0;
            double promo=0.0;
            double distance=0.0;
            double amount=0.0;
            if(rent.isHasDiscount())
                discount=basePrice*(PropertiesUtil.getDiscount()*0.01);
            if(rent.isPromo())
                promo=basePrice*(PropertiesUtil.getDiscountProm()*0.01);
            distance=basePrice*narrowWideDistance(movementPath);
            amount=basePrice*distance;
            totalPrice=amount-discount-promo;
            receipt.setBasePrice(basePrice);
            receipt.setDiscount(discount);
            receipt.setPromo(promo);
            receipt.setDistance(distance);
            receipt.setTotalPrice(totalPrice);
        }
        receipts.add(receipt);
        FileUtil.createFile(createNameForReceipt(receipt),createReceiptItems(receipt));
    }

    /**
     * Calculating base price
     * FORMULA: rent duration * UNIT_PRICE of corresponding vehicle type
     * @param rent Rent containing all needed information
     * @return Returns base price (double)
     */
    private static double calculateBasePrice(Rent rent){
        double basePrice=0.0;
        if(rent.getVehicle() instanceof ElectricCar)
            basePrice=rent.getDuration()*PropertiesUtil.getCarUnitPrice();
        else if(rent.getVehicle() instanceof ElectricBicycle)
            basePrice=rent.getDuration()*PropertiesUtil.getBicycleUnitPrice();
        else if(rent.getVehicle() instanceof ElectricScooter)
            basePrice=rent.getDuration()*PropertiesUtil.getScooterUnitPrice();
        return basePrice;
    }

    /**
     * Checking whether to use narrow or wide distance
     * Check whether path list contains some non-narrow fields
     * @param path List of narrow distance fields
     * @return Returns double value of adequate distance
     */
    private static double narrowWideDistance(List<Integer> path){
        boolean containsWideDistanceFiled= path.stream().noneMatch(e->Simulation.narrowDistanceFields.contains(e));
        if(!containsWideDistanceFiled)
            return PropertiesUtil.getDistanceNarrow();
        return PropertiesUtil.getDistanceWide();
    }

    /**
     * Generating items that will be stored into txt receipt
     * Provided user's driving licence and document if vehicle type is electricCar
     * @param receipt Receipt to generate items for
     * @return Returns list of strings used in createReceipt method in ReceiptUtil
     */
    private static List<String> createReceiptItems(Receipt receipt){
        String title="***** RECEIPT *****";
        String br="-----------------------------";
        String dateTime="DATE TIME: "+receipt.getRent().getDate().toString()+" "+receipt.getRent().getTime().toString();
        Vehicle vehicle=receipt.getRent().getVehicle();
        String vehicleInfo="VEHICLE ID: "+vehicle.getId();
        boolean isCar=false;
        if(vehicle instanceof ElectricCar){
            isCar=true;
            vehicleInfo+=" | VEHICLE TYPE: "+ElectricCar.class.getSimpleName();
        }else if(vehicle instanceof ElectricBicycle)
            vehicleInfo+=" | VEHICLE TYPE: "+ElectricBicycle.class.getSimpleName();
        else if(vehicle instanceof ElectricScooter)
            vehicleInfo+="| VEHICLE TYPE: "+ElectricScooter.class.getSimpleName();
        String userInfo="USER'S NAME: "+receipt.getRent().getUser().getName();
        if(isCar){
            if(receipt.getRent().getUser() instanceof LocalResidentUser){
                receipt.getRent().getUser().setDriverLicence();
                userInfo+="| IDENTITY CARD: "+((LocalResidentUser) receipt.getRent().getUser()).getIdentityCard()+
                        "| DRIVING LICENCE: "+receipt.getRent().getUser().getDriverLicence();
            }
            else if(receipt.getRent().getUser() instanceof ForeginResidentUser){
                receipt.getRent().getUser().setDriverLicence();
                userInfo+="| PASSPORT: "+((ForeginResidentUser) receipt.getRent().getUser()).getPassport()+
                        "| DRIVING LICENCE: "+receipt.getRent().getUser().getDriverLicence();
            }
        }
        return Arrays.asList(title,br,dateTime,br,vehicleInfo,br,userInfo,br,receipt.toString());

    }

    /**
     * Checking if directory exists, if not mkdir
     * @param receipt Receipt to make file name for
     * @return Returns name/path of receipt to be created
     */
    private static String createNameForReceipt(Receipt receipt){
        File f=new File(PropertiesUtil.getReceiptsPath());
        if(!f.exists())
            f.mkdir();
        String receiptName="receipt_"+receipt.getRent().getUser().getName()+"_"+System.currentTimeMillis();
        return f+"/"+receiptName+".txt";
    }

}
