package org.unibl.etf.epj2.utils;

import org.unibl.etf.epj2.models.rent.Location;
import org.unibl.etf.epj2.models.rent.Rent;
import org.unibl.etf.epj2.models.user.ForeginResidentUser;
import org.unibl.etf.epj2.models.user.LocalResidentUser;
import org.unibl.etf.epj2.models.user.User;
import org.unibl.etf.epj2.models.vehicle.ElectricBicycle;
import org.unibl.etf.epj2.models.vehicle.ElectricCar;
import org.unibl.etf.epj2.models.vehicle.ElectricScooter;
import org.unibl.etf.epj2.models.vehicle.Vehicle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


/**@author Aleksandar Drljaca
 * Parser class for parsing files
 */
public class ParserUtil {
    /**
     * Reads file
     * @param path Path to file
     * @return Returns list of strings (lines containing adequate attributes)
     */
    private static List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(new File(path).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Collecting data and creating vehicle objects
     * Data[0] represents vehicle's ID
     * Checking if map already contains ID (multiple vehicles with same ID)
     * Checking if present data has every parameter for creating an object of adequate type
     * @return Returns list of vehicles
     */
    public static HashMap<String, Vehicle> parseVehicleDataCSV() {
        List<String> lines = readFile(PropertiesUtil.getVehicleDataCSVFilePath());
        HashMap<String, Vehicle> vehicles = new HashMap<>();
        lines.remove(0);
        for (String line : lines) {
            String[] data = line.split(",");
            if (isAllVehicleDataPresent(data)) {
                //data[0] represents vehicle's ID
                if (!vehicles.containsKey(data[0])) {
                    if (line.contains(PropertiesUtil.getElectricCarIdentifier()) && isCarDataValid(data))
                        vehicles.put(data[0], createAnElectricCarFromData(data));
                    else if (line.contains(PropertiesUtil.getElectricBicycleIdentifier()) && isBicycleDataValid(data))
                        vehicles.put(data[0], createAnElectricBicycleFromData(data));
                    else if (line.contains(PropertiesUtil.getElectricScooterIdentifier()) && isScooterDataValid(data))
                        vehicles.put(data[0], createAnElectricScooterFromData(data));
                }
            }
        }

        return vehicles;
    }

    /**
     * Collecting data and creating rent objects
     * Before adding to the list:
     *      1.Checking if all data is present
     *      2.Checking if start and end locations are valid (valid form and not exceeding map range)
     *      3.Checking if vehicle with specified id exists
     *      4. 5. Checking for duplicates -> done through list.contains() -> overridden equals and hashcode methods in Rent class
     *      Rent objects are considered duplicates if they have same date and time and (same username or same vehicleID)
     * @return Return list of rents
     */
    public static List<Rent> parseRentDataCSV() {
        List<String> lines = readFile(PropertiesUtil.getRentDataCSVFilePath());
        List<Rent> rents = new ArrayList<>();
        HashMap<String, Vehicle> vehicles = parseVehicleDataCSV();
        Random r = new Random();
        for (String line : lines) {
            String[] data = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
            if (isAllRentDataPresent(data) && checkIfCoordinatesAreUnderQuotes(line) && isLocationValid(data[3]) && isLocationValid(data[4]) && isVehicleIdValid(vehicles, data[2])) {
                String dateTime = data[0];
                LocalDate date=null;
                LocalTime time=null;
                try{
                    date= parseDate(dateTime.split(" ")[0]);
                    time= parseTime(dateTime.split(" ")[1]);
                }catch(DateTimeException e){
                    System.out.println(e.getMessage());
                    continue;
                }
                String username = data[1];
                String vehicleId = data[2];
                Location startLocation = parseLocation(data[3]);
                Location endLocation = parseLocation(data[4]);
                int duration = Integer.parseInt(data[5]);
                boolean malfunction = parseMalfunctionPromo(data[6]);
                boolean promo = parseMalfunctionPromo(data[7]);
                User user = null;
                boolean value = r.nextBoolean();
                if (value)
                    user = new LocalResidentUser(username);
                else user = new ForeginResidentUser(username);
                Rent rent = new Rent(date, time, user, vehicles.get(vehicleId), startLocation, endLocation, duration, malfunction, promo);
                if (!rents.contains(rent))
                    rents.add(rent);
                else System.out.println("RENT IS ALREADY IN LIST (DUPLICATE): " + rent);
            }
        }
        return rents;
    }

    /**
     * Parsing string to extract the date
     * @param date String to process
     * @return Returns LocalDate
     */
    private static LocalDate parseDate(String date) {
        String[] ddMmYy = date.split("\\.");
        int dd = Integer.parseInt(ddMmYy[0]);
        int mm = Integer.parseInt(ddMmYy[1]);
        int yy = Integer.parseInt(ddMmYy[2]);
        return LocalDate.of(yy, mm, dd);
    }

    /**
     * Check time (hours/minutes) format
     * e.g. hours= 09 -> must be 9 to parse as int
     * e.g. minutes= 05 ->must be 5 to parse as int
     * @param time String to check
     * @return Returns true if format is problematic, else false
     */
    private static boolean checkHoursMinutesFormat(String time) {
        return (time.charAt(0) == '0' && time.length() == 2);
    }

    /**
     * Parse string to extract time
     * @param time String to process
     * @return Returns LocalTime object
     */
    private static LocalTime parseTime(String time) {
        String[] hm = time.split(":");
        String h = hm[0];
        String m = hm[1];
        if (checkHoursMinutesFormat(h))
            h = "" + h.charAt(1);
        if (checkHoursMinutesFormat(m))
            m = "" + m.charAt(1);
        return LocalTime.of(Integer.parseInt(h), Integer.parseInt(m));
    }

    /**
     * Extracting data as string parameters and parsing it to create an electric car object
     * ID[0],Manufacturer[1],Model[2],ProcurementDate[3],ProcurementCost[4],Description[7]
     * Battery level is default (Properties)
     * @param data Line split by comma (CSV file)
     * @return Returns an electric car object
     */
    private static ElectricCar createAnElectricCarFromData(String[] data) {
        LocalDate procurementDate = parseDate(data[3]);
        double procurementCost = Double.parseDouble(data[4]);
        return new ElectricCar(data[0], data[1], data[2], procurementCost,
                PropertiesUtil.getDefaultBatteryLevel(), data[7], procurementDate);
    }

    /**
     * Extracting data as string parameters and parsing it to create an electric bicycle object
     * ID[0],Manufacturer[1],Model[2],ProcurementCost[4],Autonomy[5]
     * Battery level is default (Properties)
     * @param data Line split by comma (CSV file)
     * @return Returns an electric bicycle object
     */
    private static ElectricBicycle createAnElectricBicycleFromData(String[] data) {
        double procurementCost = Double.parseDouble(data[4]);
        int autonomy = Integer.parseInt(data[5]);
        return new ElectricBicycle(data[0], data[1], data[2], procurementCost,
                PropertiesUtil.getDefaultBatteryLevel(), autonomy);
    }

    /**
     * Extracting data as string parameters and parsing it to create an electric scooter object
     * ID[0],Manufacturer[1],Model[2],ProcurementCost[4],MaxSpeed[6]
     * Battery level is default (Properties)
     * @param data Line split by comma (CSV file)
     * @return Returns an electric scooter object
     */
    private static ElectricScooter createAnElectricScooterFromData(String[] data) {
        double procurementCost = Double.parseDouble(data[4]);
        int maxSpeed = Integer.parseInt(data[6]);
        return new ElectricScooter(data[0], data[1], data[2], procurementCost,
                PropertiesUtil.getDefaultBatteryLevel(), maxSpeed);
    }

    /**
     * Checking if all car data is valid (not an empty string)
     * @param data Data to check
     * @return Return boolean value
     */
    private static boolean isCarDataValid(String[] data) {
        return (!data[0].isEmpty() && !data[1].isEmpty() && !data[2].isEmpty()
                && !data[3].isEmpty() && !data[4].isEmpty() && isANumber(data[4]) && !data[7].isEmpty());
    }

    /**
     * Checking if all bicycle data is valid (not an empty string)
     * @param data Data to check
     * @return Return boolean value
     */
    private static boolean isBicycleDataValid(String[] data) {
        return (!data[0].isEmpty() && !data[1].isEmpty() && !data[2].isEmpty()
                && !data[4].isEmpty() && isANumber(data[4]) && !data[5].isEmpty() && isANumber(data[5]));
    }

    /**
     * Checking if all scooter data is valid (not an empty string)
     * @param data Data to check
     * @return Return boolean value
     */
    private static boolean isScooterDataValid(String[] data) {
        return (!data[0].isEmpty() && !data[1].isEmpty() && !data[2].isEmpty()
                && !data[4].isEmpty() && isANumber(data[4]) && !data[6].isEmpty() && isANumber(data[6]));
    }

    /**
     * Check if all parameters are present from rent csv lines
     * Checks if coordinates are under quotes
     * Checks if duration attribute is a digit
     * Checks if coordinates are properly listed
     * @param data Data to process
     * @return Returns boolean value
     */
    private static boolean isAllRentDataPresent(String[] data) {
        return (data.length == 8 && !data[0].isEmpty() && !data[1].isEmpty() && !data[2].isEmpty() && !data[3].isEmpty()
                && !data[4].isEmpty() && !data[5].isEmpty() && isANumber(data[5]) && !data[6].isEmpty() && !data[7].isEmpty());
    }

    /**
     * Checking if coordinates are properly listed
     * Because order of columns won't change we can assume
     * that start and end location are listed between third and seventh comma
     * Coordinates must be under quotes
     * @param line Line to check
     * @return Returns boolean value
     */
    private static boolean checkIfCoordinatesAreUnderQuotes(String line) {
        int quoteCount = 0;
        int commaCount = 0;
        // tracking where quotes are positioned
        // they must be between third and seventh comma
        int firstQuotePos = -1;
        int lastQuotePos = -1;
        int thirdCommaPos = -1;
        int seventhCommaPos = -1;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '"') {
                quoteCount++;
                if (quoteCount == 1)
                    firstQuotePos = i;
                if (quoteCount == 4)
                    lastQuotePos = i;
            }
            if (line.charAt(i) == ',') {
                commaCount++;
                if (commaCount == 3)
                    thirdCommaPos = i;
                if (commaCount == 7)
                    seventhCommaPos = i;
            }
        }

        return (quoteCount == 4 && (thirdCommaPos + 1) == firstQuotePos && (seventhCommaPos - 1) == lastQuotePos);
    }

    /**
     * Checks whether all columns are present
     * @param data Data to check
     * @return Returns boolean value
     */
    private static boolean isAllVehicleDataPresent(String[] data) {
        return data.length == 9;
    }

    /**
     * Checking if string contains digits only
     * @param data String to check
     * @return Return boolean value
     */
    private static boolean isANumber(String data) {
        for (int i = 0; i < data.length(); i++)
            if (Character.isAlphabetic(data.charAt(i)))
                return false;
        return true;
    }

    /**
     * Checking if x or y exceeds row/column length
     * @param location Location as a string
     * @return Return boolean value
     */
    private static boolean isLocationValid(String location) {
        if (!location.contains("\""))
            return false;
        Location loc = parseLocation(location);
        return ((loc.getX() >= 0 && loc.getX() < 20) && (loc.getY() >= 0 && loc.getY() < 20));
    }

    /**
     * Checking if the vehicle with passed id exists
     * @param vehicles Collection of vehicles in possession of company
     * @return Returns boolean value
     */
    private static boolean isVehicleIdValid(HashMap<String, Vehicle> vehicles, String id) {
        return vehicles.containsKey(id);
    }

    /**
     * Parsing location -> removing " " from string and parsing digits to get x and y
     * @param location String to process
     * @return Return location
     */
    private static Location parseLocation(String location) {
        String temp = location.replaceAll("^\"|\"$", "");
        String[] loc = temp.split(",");
        int x = -1;
        if (isANumber(loc[0]))
            x = Integer.parseInt(loc[0]);
        int y = -1;
        if (isANumber(loc[1]))
            y = Integer.parseInt(loc[1]);
        return new Location(x, y);
    }

    /**
     * Method to parse malfunction or promo parameter from string
     * e.g. no->false | yes->true
     * @param data String to process
     * @return Returns boolean value
     */
    private static boolean parseMalfunctionPromo(String data) {
        return PropertiesUtil.getCSVBooleanTrue().equals(data);
    }

}
