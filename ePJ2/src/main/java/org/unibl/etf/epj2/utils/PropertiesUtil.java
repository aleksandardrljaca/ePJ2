package org.unibl.etf.epj2.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Aleksandar Drljaca
 */
public class PropertiesUtil {
    /**
     * Properties
     */
    private final static Properties properties=new Properties();

    /**
     * Loading properties file
     */
    static{
        try{
            FileInputStream fis=new FileInputStream("src/main/resources/org/unibl/etf/epj2/config/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return Returns narrow distance
     */
    public static int getDistanceNarrow(){
        return Integer.parseInt(properties.getProperty("DISTANCE_NARROW"));
    }

    /**
     * @return Returns wide distance
     */
    public static int getDistanceWide(){
        return Integer.parseInt(properties.getProperty("DISTANCE_WIDE"));
    }

    /**
     *
     * @return Returns discount
     */
    public static double getDiscount(){
        return Double.parseDouble(properties.getProperty("DISCOUNT"));
    }

    /**
     *
     * @return Returns promotion discount
     */
    public static double getDiscountProm(){
        return Double.parseDouble(properties.getProperty("DISCOUNT_PROM"));
    }

    /**
     * @return Returns car's unit price
     */
    public static int getCarUnitPrice(){
        return Integer.parseInt(properties.getProperty("CAR_UNIT_PRICE"));
    }

    /**
     *
     * @return Returns bicycle's unit price
     */
    public static int getBicycleUnitPrice(){
        return Integer.parseInt(properties.getProperty("BICYCLE_UNIT_PRICE"));
    }

    /**

     * @return Returns scooter's unit price
     */
    public static int getScooterUnitPrice(){
        return Integer.parseInt(properties.getProperty("SCOOTER_UNIT_PRICE"));
    }

    /**
     *
     * @return Returns path to vehicle data csv file
     */
    public static String getVehicleDataCSVFilePath() {
        return properties.getProperty("VEHICLE_DATA_CSV_FILE_PATH");
    }

    /**
     * @return Returns path to rent data csv file
     */
    public static String getRentDataCSVFilePath() {
        return properties.getProperty("RENT_DATA_CSV_FILE_PATH");
    }

    /**
     * @return Returns main window width
     */
    public static int getMainWindowWidth(){
        return Integer.parseInt(properties.getProperty("MAIN_VIEW_WINDOW_WIDTH"));
    }

    /**
     * @return Returns main window height
     */
    public static int getMainWindowHeight(){
        return Integer.parseInt(properties.getProperty("MAIN_VIEW_WINDOW_HEIGHT"));
    }

    /**
     * @return Returns vehicles window width
     */
    public static int getVehiclesWindowWidth(){
        return Integer.parseInt(properties.getProperty("VEHICLES_VIEW_WINDOW_WIDTH"));
    }

    /**
     * @return Returns vehicles window height
     */
    public static int getVehiclesWindowHeight(){
        return Integer.parseInt(properties.getProperty("VEHICLES_VIEW_WINDOW_HEIGHT"));
    }

    /**
     * @return Returns car identifier representing the type of vehicle specified in csv file ( e.g. automobile,auto etc.)
     */
    public static String getElectricCarIdentifier(){
        return properties.getProperty("ELECTRIC_CAR_IDENTIFIER");
    }
    /**
     * @return Returns bicycle identifier representing the type of vehicle specified in csv file ( e.g. bicycle,bic etc.)
     */
    public static String getElectricBicycleIdentifier(){
        return properties.getProperty("ELECTRIC_BICYCLE_IDENTIFIER");
    }
    /**
     * @return Returns scooter identifier representing the type of vehicle specified in csv file ( e.g. scooter,s,sct etc.)
     */
    public static String getElectricScooterIdentifier(){
        return properties.getProperty("ELECTRIC_SCOOTER_IDENTIFIER");
    }

    /**
     * Used when creating a vehicle object
     * @return Returns default battery level
     */
    public static int getDefaultBatteryLevel(){
        return Integer.parseInt(properties.getProperty("DEFAULT_BATTERY_LEVEL"));
    }

    /**
     * @return Returns path to receipts directory
     */
    public static String getReceiptsPath(){
        return properties.getProperty("RECEIPTS_FOLDER_PATH");
    }

    /**
     * e.g. da,yes -> true
     * @return Returns string representation of boolean value specified in csv
     */
    public static String getCSVBooleanTrue(){
        return properties.getProperty("BOOLEAN_TRUE");
    }
    /**
     * e.g. ne,no -> true
     * @return Returns string representation of boolean value specified in csv
     */
    public static String getCSVBooleanFalse(){
        return properties.getProperty("BOOLEAN_FALSE");
    }

    /**
     * @return Returns default malfunction description string
     */
    public static String getDefaultMalfunctionDescription(){
        return properties.getProperty("DEFAULT_MALFUNCTION_DESCRIPTION");
    }

    /**
     * @return Returns path to losses directory
     */
    public static String getLossesPath(){
        return properties.getProperty("LOSSES_PATH");
    }

}
