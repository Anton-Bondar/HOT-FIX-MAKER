package com.hotfixmaker;

import com.hotfixmaker.db.service.DBService;
import com.hotfixmaker.helper.AlertHelper;
import com.hotfixmaker.model.dto.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static com.hotfixmaker.Controller.EXECUTION_ERROR;
import static com.hotfixmaker.model.message.HFMMessage.HF10;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/hotfixmaker/sample.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();
        initOnCloseListener(primaryStage, controller);
        primaryStage.setTitle("Hot Fix Maker");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 421, 453));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initOnCloseListener(Stage primaryStage, final Controller controller) {
        primaryStage.setOnCloseRequest(event -> {
            Session sessionBean = controller.getSessionBeanFromView();
            try {
                DBService.save(sessionBean);
            } catch (IOException e) {
                AlertHelper.create(HF10.get(), Alert.AlertType.ERROR, EXECUTION_ERROR).showAndWait();
            }
        });
    }
}
