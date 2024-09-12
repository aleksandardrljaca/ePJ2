package org.unibl.etf.epj2.utils;

import org.unibl.etf.epj2.models.rent.Rent;


import java.util.*;
import java.util.stream.Collectors;
/**
 * @author Aleksandar Drljaca
 */
public class RentUtil {
    /**
     * In the simulation tracking of the current date must be implemented
     * in a way if the next date comes 5s sleep should be performed
     * @return Returns rents grouped by date and time
     */
    public static List<Rent> getRents(){
        List<Rent> rents=ParserUtil.parseRentDataCSV();
        return rents.stream().sorted(Comparator.comparing(Rent::getDate).thenComparing(Rent::getTime)).collect(Collectors.toList());
    }
}
