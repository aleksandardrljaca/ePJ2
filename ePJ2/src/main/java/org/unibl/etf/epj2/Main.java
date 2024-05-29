package org.unibl.etf.epj2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.unibl.etf.epj2.utils.ParserUtil;
import org.unibl.etf.epj2.utils.PropertiesUtil;
import org.unibl.etf.epj2.utils.ReceiptUtil;
import org.unibl.etf.epj2.utils.RentUtil;

import java.io.IOException;

/**
 * Main
 * @author Aleksandar Drljaca
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), PropertiesUtil.getMainWindowWidth(), PropertiesUtil.getMainWindowHeight());
        stage.setTitle("eMobility");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}