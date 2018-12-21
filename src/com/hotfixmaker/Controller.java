package com.hotfixmaker;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import com.hotfixmaker.factory.FileCellFactory;
import com.hotfixmaker.model.SelectedFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller implements Initializable {

    private ObservableList<SelectedFile> selectedFiles;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField targetFolderField;

    @FXML
    private TextField defaultFolderField;

    @FXML
    private ListView<SelectedFile> filesList;

    @FXML
    private TextField nameField;

    @FXML
    private void onClickBrowse() {
        final DirectoryChooser targetFolderChooser = new DirectoryChooser();
        final Stage stage = (Stage) mainPane.getScene().getWindow();
        final File file = targetFolderChooser.showDialog(stage);
        if (file != null) {
            targetFolderField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void onClickPlus() {
        final FileChooser fileChooser = new FileChooser();
        final Stage stage = (Stage) mainPane.getScene().getWindow();
        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        if (!files.isEmpty()) {
            for (File file : files) {
                selectedFiles.add(new SelectedFile(file));
            }
            filesList.setItems(selectedFiles);
        }
    }

    @FXML
    private void onClickMinus() {
        SelectedFile selectedItem = filesList.getSelectionModel().getSelectedItem();
        selectedFiles.remove(selectedItem);
    }

    @FXML
    private void onClickOk() {
        String targetFolderPath = targetFolderField.getText();
        String defaultFolderPath = defaultFolderField.getText();
        //emptyValidation();
        if (!defaultFolderPath.isEmpty()) {
            createDefaultFolder(targetFolderPath);
        }
    }

    private void createDefaultFolder(String targetFolderPath) {
        System.out.println("targetFolderPath "+targetFolderPath);
        boolean isCreated = new File(targetFolderPath).mkdirs();
        if (!isCreated) {
            createErrorAlert("The default folders path is incorrect").showAndWait();
        }
    }

    private void emptyValidation() {
        if (nameField.getText().isEmpty()) {
            createErrorAlert("Name value must be specified").showAndWait();
        }
        if (selectedFiles.isEmpty()) {
            createErrorAlert("No files selected").showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filesList.setCellFactory(new FileCellFactory());
        selectedFiles = FXCollections.observableArrayList();
    }

    private Alert createErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation error");
        alert.setContentText(message);
        return alert;
    }
}
