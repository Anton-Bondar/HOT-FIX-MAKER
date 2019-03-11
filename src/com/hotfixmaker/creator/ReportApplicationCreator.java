package com.hotfixmaker.creator;

import static com.hotfixmaker.model.message.HFMMessage.HFM1;
import static com.hotfixmaker.model.message.HFMMessage.HFM8;

import javafx.scene.control.Alert;

import java.io.File;

public class ReportApplicationCreator {

    public static void process(File archive) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        if (archive.exists()) {
            alert.setContentText(HFM8.get());
        } else {
            alert.setContentText(HFM1.get());
        }

        alert.showAndWait();
    }
}
