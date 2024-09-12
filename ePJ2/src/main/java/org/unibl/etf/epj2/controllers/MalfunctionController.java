package org.unibl.etf.epj2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.unibl.etf.epj2.models.malfunction.MalfunctionData;
import org.unibl.etf.epj2.utils.ReceiptUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MalfunctionController {
    /**
     * Table to display data
     */
    public TableView malfunctionTable;
    /**
     * Column representing vehicle's type
     */
    public TableColumn typeCol;
    /**
     * Column representing vehicle's ID
     */
    public TableColumn idCol;
    /**
     * Column representing date malfunction occurred
     */
    public TableColumn dateCol;
    /**
     * Column representing time malfunction occurred
     */
    public TableColumn timeCol;
    /**
     * Column representing description of the malfunction that occurred
     */
    public TableColumn descCol;
    /**
     * List of malfunctions
     */
    private List<MalfunctionData> data;

    /**
     * Initialize
     */
    @FXML
    private void initialize(){
        prepareData();
        prepareTable();
    }

    /**
     * Method to display prepared data
     * Populating TableView with List<MalfunctionData>
     */
    private void prepareTable(){
        typeCol.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        malfunctionTable.getItems().clear();
        data.forEach(d->malfunctionTable.getItems().add(d));
    }

    /**
     * Method to prepare info
     * Data displayed inside table is of type MalfunctionData
     * Filtering rents where malfunction occurred
     * Storing extracted info into List<MalfunctionData>
     */
    private void prepareData(){
        data=new ArrayList<>();
        ReceiptUtil.rents.forEach(r->{
            if(r.isMalfunction() && ReceiptUtil.vehicles.get(r.getVehicle().getId()).getMalfunction()!=null) {
                String vehicleType = r.getVehicle().getClass().getSimpleName();
                String vehicleId=r.getVehicle().getId();
                LocalDate date=r.getDate();
                LocalTime time=r.getTime();
                String desc=ReceiptUtil.vehicles.get(r.getVehicle().getId()).getMalfunction().getDescription();
                data.add(new MalfunctionData(vehicleType,vehicleId,date,time,desc));
            }
        });
    }

}
