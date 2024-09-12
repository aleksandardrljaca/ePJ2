package org.unibl.etf.epj2.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import org.unibl.etf.epj2.models.rent.Location;
import org.unibl.etf.epj2.models.vehicle.ElectricBicycle;
import org.unibl.etf.epj2.models.vehicle.ElectricCar;
import org.unibl.etf.epj2.models.vehicle.ElectricScooter;
import org.unibl.etf.epj2.models.vehicle.Vehicle;
import org.unibl.etf.epj2.utils.ReceiptUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller for vehicles view
 * @author Aleksandar Drljaca
 */
public class VehiclesController {
    /**
     * Car ID column
     */
    public TableColumn idCol;
    /**
     * Car manufacturer column
     */
    public TableColumn manufacturerCol;
    /**
     * Car model column
     */
    public TableColumn modelCol;
    /**
     * Car cost column
     */
    public TableColumn costCol;
    /**
     * Car battery level column
     */
    public TableColumn batteryLevelCol;
    /**
     * Car description column
     */
    public TableColumn descCol;
    /**
     * Car procurement date column
     */
    public TableColumn dateCol;
    /**
     * Bicycle ID column
     */
    public TableColumn idColB;
    /**
     * Bicycle manufacturer column
     */
    public TableColumn manufacturerColB;
    /**
     * Bicycle model column
     */
    public TableColumn modelColB;
    /**
     * Bicycle procurement cost column
     */
    public TableColumn costColB;
    /**
     * Bicycle battery level column
     */
    public TableColumn batteryLevelColB;
    /**
     * Bicycle autonomy column
     */
    public TableColumn autonomyB;
    /**
     * Scooter ID column
     */
    public TableColumn idColS;
    /**
     * Scooter manufacturer column
     */
    public TableColumn manufacturerColS;
    /**
     * Scooter model column
     */
    public TableColumn modelColS;
    /**
     * Scooter procurement cost column
     */
    public TableColumn costColS;
    /**
     * Scooter battery level column
     */
    public TableColumn batteryLevelColS;
    /**
     * Scooter max speed column
     */
    public TableColumn maxSpeedColS;
    /**
     * Cars table
     */
    public TableView carsTable;
    /**
     * Bicycles table
     */
    public TableView bicyclesTable;
    /**
     * Scooters table
     */
    public TableView scootersTable;
    /**
     * List of electric cars
     */
    private final List<ElectricCar> cars=new ArrayList<>();
    /**
     * List of bicycles
     */
    private final List<ElectricBicycle> bicycles=new ArrayList<>();
    /**
     * List of scooters
     */
    private final List<ElectricScooter> scooters=new ArrayList<>();

    /**
     * Init method
     */
    @FXML
    private void initialize(){
        initLists();
        prepareCarsTable();
        prepareBicyclesTable();
        prepareScootersTable();
    }

    /** Preparing table to present car info
     */
    private void prepareCarsTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("procurementCost"));
        batteryLevelCol.setCellValueFactory(new PropertyValueFactory<>("batteryLevel"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("procurementDate"));
        carsTable.getItems().clear();
        cars.forEach(c->carsTable.getItems().add(c));
    }

    /** Preparing table to present bicycle info
     */
    private void prepareBicyclesTable(){
        idColB.setCellValueFactory(new PropertyValueFactory<>("id"));
        manufacturerColB.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        modelColB.setCellValueFactory(new PropertyValueFactory<>("model"));
        costColB.setCellValueFactory(new PropertyValueFactory<>("procurementCost"));
        batteryLevelColB.setCellValueFactory(new PropertyValueFactory<>("batteryLevel"));
        autonomyB.setCellValueFactory(new PropertyValueFactory<>("autonomy"));
        bicyclesTable.getItems().clear();
        bicycles.forEach(b->bicyclesTable.getItems().add(b));
    }

    /** Preparing table to present scooter info
     */
    private void prepareScootersTable(){
        idColS.setCellValueFactory(new PropertyValueFactory<>("id"));
        manufacturerColS.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        modelColS.setCellValueFactory(new PropertyValueFactory<>("model"));
        costColS.setCellValueFactory(new PropertyValueFactory<>("procurementCost"));
        batteryLevelColS.setCellValueFactory(new PropertyValueFactory<>("batteryLevel"));
        maxSpeedColS.setCellValueFactory(new PropertyValueFactory<>("maxSpeed"));
        scootersTable.getItems().clear();
        scooters.forEach(s->scootersTable.getItems().add(s));
    }

    /**
     * Method to initialize lists
     * Split one collection into three
     * Cars,Bicycles and Scooters
     */
    private void initLists(){
        ReceiptUtil.vehicles.entrySet().forEach(e->{
            Vehicle v=e.getValue();
            if(v instanceof ElectricCar)
                cars.add((ElectricCar) v);
            else if(v instanceof ElectricBicycle)
                bicycles.add((ElectricBicycle) v);
            else if(v instanceof ElectricScooter)
                scooters.add((ElectricScooter) v);
        });
    }

}
