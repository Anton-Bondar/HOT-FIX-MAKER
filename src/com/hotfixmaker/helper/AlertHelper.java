package com.hotfixmaker.helper;

import javafx.scene.control.Alert;

public class AlertHelper {

    public static Alert create(String message, Alert.AlertType type, String title) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        return alert;
    }
}
