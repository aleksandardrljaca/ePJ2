package org.unibl.etf.epj2.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.unibl.etf.epj2.models.vehicle.ElectricBicycle;
import org.unibl.etf.epj2.models.vehicle.ElectricCar;
import org.unibl.etf.epj2.models.vehicle.ElectricScooter;
import org.unibl.etf.epj2.models.vehicle.Vehicle;
import org.unibl.etf.epj2.utils.FileUtil;
import org.unibl.etf.epj2.utils.PropertiesUtil;

import java.io.File;

public class LossesController {
    /**
     * ListView for displaying reported vehicles paths
     */
    public ListView<String> listView;
    /**
     * Button to do deserialization
     */
    public Button deserializeBtn;
    /**
     * Text area for display vehicle info
     */
    public TextArea textArea;

    /**
     * Initialize
     */
    @FXML
    private void initialize(){
        deserializeBtn.disableProperty().bind(Bindings.isEmpty(listView.getSelectionModel().getSelectedItems()));
        populateListView();
    }

    /**
     * Method for vehicle object deserialization
     * If an item from list view is selected
     * take path and deserialize corresponding file/object
     * Display vehicle data in text area
     * @param mouseEvent Mouse click event
     */
    public void deserializeBtnClicked(MouseEvent mouseEvent) {
        String selection=listView.getSelectionModel().getSelectedItem();
        Vehicle vehicle=null;
        if(!selection.isEmpty())
            vehicle=FileUtil.deserialize(selection);
        if(vehicle!=null){
            if(vehicle instanceof ElectricCar)
                textArea.setText(vehicle.toString());
            else if(vehicle instanceof ElectricBicycle)
                textArea.setText(vehicle.toString());
            else if(vehicle instanceof ElectricScooter)
                textArea.setText(vehicle.toString());
        }
    }

    /**
     * Method to populate list view
     * Reading all files from losses path
     * Populating list view with paths
     */
    private void populateListView(){
       File[] files= FileUtil.listFiles(PropertiesUtil.getLossesPath());
       if(files!=null)
           for(File f:files)
               listView.getItems().add(f.toPath().toString());
    }

}
