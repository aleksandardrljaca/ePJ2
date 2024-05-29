package org.unibl.etf.epj2.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.unibl.etf.epj2.Main;
import org.unibl.etf.epj2.Simulation;
import org.unibl.etf.epj2.utils.ReceiptUtil;

import java.io.IOException;
import java.security.Signature;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Aleksandar Drljaca
 */
public class MainController {
    /**
     * Button to open the window and display vehicles info
     */
    public Button vehiclesBtn;
    /**
     * Button to open the window and display malfunctioning vehicles info
     */
    public Button malfunctionBtn;
    /**
     * Button to open the window and display financial reports
     */
    public Button financialBtn;
    /**
     * Start simulation button
     */

    public Button startBtn;
    /**
     * Button to open the losses windows and display more info
     */
    public Button lossesBtn;
    /**
     * GridPane containing map
     * Vehicles and users move along this map
     */
    @FXML
    private GridPane mapPane;

    /**
     * Initialize
     * Configure grid pane
     * Add buttons to simulate map fields
     * Create callbacks for painting and remove paint from fields
     * Initialize callbacks in Simulation class
     */
    @FXML
    private void initialize() {

        for (int i = 0; i < 20; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(35);
            colConstraints.setPrefWidth(35);
            colConstraints.setMaxWidth(35);
            mapPane.addColumn(i);
            mapPane.getColumnConstraints().add(colConstraints);
        }

        for (int j = 0; j < 20; j++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(35);
            rowConstraints.setPrefHeight(35);
            rowConstraints.setMaxHeight(35);
            mapPane.addRow(j);
            mapPane.getRowConstraints().add(rowConstraints);

        }
        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 20; j++) {
                Button b = new Button("");
                b.setStyle("-fx-background-color: #ffffff");
                b.setFont(Font.font(10));
                b.setMinWidth(35);
                b.setMinHeight(35);
                mapPane.add(b, j, i);
            }

        BiConsumer<Integer, String> paint = ((pos, batteryPercentage) -> {
            Platform.runLater(() -> {
                Timeline timeline = new Timeline();
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.001), e -> {
                    mapPane.getChildren().get(pos).setStyle("-fx-background-color:#eb9234");
                    ((Button) mapPane.getChildren().get(pos)).setText(batteryPercentage);
                });
                timeline.getKeyFrames().add(keyFrame);
                timeline.playFromStart();
            });
        });
        Consumer<Integer> removePaint = (pos -> {
            Platform.runLater(() -> {
                Timeline timeline = new Timeline();
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.001), e -> {
                    mapPane.getChildren().get(pos).setStyle("-fx-background-color:#ffffff");
                    ((Button) mapPane.getChildren().get(pos)).setText("");
                });
                timeline.getKeyFrames().add(keyFrame);
                timeline.playFromStart();
            });
        });
        Consumer<Boolean> enableButtons=p->{
          if(p){
              malfunctionBtn.setDisable(false);
              financialBtn.setDisable(false);
              lossesBtn.setDisable(false);
          }else{
              malfunctionBtn.setDisable(true);
              financialBtn.setDisable(true);
              lossesBtn.setDisable(true);
          }
        };
        Simulation.paint = paint;
        Simulation.removePaint = removePaint;
        Simulation.buttonsOnOffSwitch=enableButtons;

    }

    /**
     * Button opening vehicle info window
     * @param mouseEvent Mouse event
     * @throws IOException Exception thrown if loader cannot find specified fxml file
     */
    public void vehiclesBtnClicked(MouseEvent mouseEvent) throws IOException {
        vehiclesBtn.setDisable(true);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/vehicles.fxml"));
        loader.setControllerFactory(c -> new VehiclesController());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Vehicles");
        newStage.setResizable(false);
        newStage.setScene(scene);
        newStage.show();
        newStage.setOnCloseRequest(e -> {
            vehiclesBtn.setDisable(false);
        });


    }
    /**
     * Button opening window with malfunctioning vehicles info
     * @param mouseEvent Mouse event
     * @throws IOException Exception thrown if loader cannot find specified fxml file
     */
    public void malfunctionsBtnClicked(MouseEvent mouseEvent) throws IOException {
        malfunctionBtn.setDisable(true);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/malfunction.fxml"));
        loader.setControllerFactory(c -> new MalfunctionController());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Malfunctions");
        newStage.setResizable(false);
        newStage.setScene(scene);
        newStage.show();
        newStage.setOnCloseRequest(e -> {
            malfunctionBtn.setDisable(false);
        });

    }
    /**
     * Button opening financial reports window
     * @param mouseEvent Mouse event
     * @throws IOException Exception thrown if loader cannot find specified fxml file
     */
    public void financialBtnClicked(MouseEvent mouseEvent) throws IOException {
        financialBtn.setDisable(true);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/financialreports.fxml"));
        loader.setControllerFactory(c -> new ReportController());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Financial reports");
        newStage.setResizable(false);
        newStage.setScene(scene);
        newStage.show();
        newStage.setOnCloseRequest(e -> {
            financialBtn.setDisable(false);
        });
    }

    /**
     * Start simulation button event
     * @param mouseEvent Mouse event
     */
    public void startBtnClicked(MouseEvent mouseEvent) {
        startBtn.setDisable(true);
        new Thread(Simulation::startSimulation).start();

    }

    /**
     * Button opening window with losses info
     * @param mouseEvent Mouse event
     * @throws IOException IOException thrown if loader cannot find specified view/fxml file
     */
    public void lossBtnClicked(MouseEvent mouseEvent) throws IOException {
        lossesBtn.setDisable(true);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/losses.fxml"));
        loader.setControllerFactory(c -> new LossesController());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Losses");
        newStage.setResizable(false);
        newStage.setScene(scene);
        newStage.show();
        newStage.setOnCloseRequest(e -> {
            lossesBtn.setDisable(false);
        });
    }

}
