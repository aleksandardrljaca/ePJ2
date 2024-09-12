package org.unibl.etf.epj2.utils;

import org.unibl.etf.epj2.models.vehicle.ElectricBicycle;
import org.unibl.etf.epj2.models.vehicle.ElectricCar;
import org.unibl.etf.epj2.models.vehicle.ElectricScooter;
import org.unibl.etf.epj2.models.vehicle.Vehicle;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.List;

/** Util class for managing files
 * @author Aleksandar Drljaca
 */
public class FileUtil {
    /**
     * Creates a file and stores content inside
     * @param path String path/name of file that will be created
     * @param content Content to put inside the file
     */
    public static void createFile(String path, List<String> content){
        PrintWriter pw=null;
        try{
            pw=new PrintWriter(new FileOutputStream(path));
            for(String line:content)
                pw.println(line);
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for serialization of vehicle object
     * @param vehicle Vehicle  object to serialize
     */
    public static void serialize(Vehicle vehicle){
        String filename="";
        if(vehicle instanceof ElectricCar)
            filename+=((ElectricCar)vehicle).getClass().getSimpleName()+".ser";
        else if(vehicle instanceof ElectricBicycle)
            filename+=((ElectricBicycle)vehicle).getClass().getSimpleName()+".ser";
        else if(vehicle instanceof ElectricScooter)
            filename+=((ElectricScooter)vehicle).getClass().getSimpleName()+".ser";
        String path=PropertiesUtil.getLossesPath()+File.separator+filename;
        File lossesDir=new File(PropertiesUtil.getLossesPath());
        if(!lossesDir.exists())
            lossesDir.mkdir();
        ObjectOutputStream oos=null;
        try{
            oos=new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(vehicle);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for deserializing serialized vehicle object
     * @param path Path to file
     * @return Returns a vehicle object
     */
    public static Vehicle deserialize(String path){
        Vehicle vehicle=null;
        ObjectInputStream ois=null;
        try{
            ois=new ObjectInputStream(new FileInputStream(path));
            vehicle=(Vehicle) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return vehicle;

    }

    /**
     * List all files from directory
     * @param path Path to directory
     * @return Returns File[]
     */
    public static File[] listFiles(String path){
        File f=new File(path);
        if(!f.isDirectory())
            return null;
        return f.listFiles();
    }
}
