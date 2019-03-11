package com.hotfixmaker.creator;

import static com.hotfixmaker.model.message.HFMMessage.HFM1;
import static com.hotfixmaker.model.message.HFMMessage.HFM7;

import javafx.scene.control.Alert;

import java.io.File;

public class ReportApplicationCreator {

    public static void process(File archive) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        if (archive.exists()) {
            alert.setContentText(HFM7.get());
        } else {
            alert.setContentText(HFM1.get());
        }

        alert.showAndWait();
    }
}
