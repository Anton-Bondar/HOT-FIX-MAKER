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

    private static final String DEFAULT_FOLDER = "\\applications\\NetCracker\\APP-INF";
    private static final String DEF_FOLDER_REGEXP = "^(\\\\)([a-zA-Z_\\\\\\-\\s0-9!+=&@#$%^&()\\[\\]{}]+)";
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
        defaultFolderValidation(defaultFolderPath);
        if (!defaultFolderPath.isEmpty()) {
            createDefaultFolder(targetFolderPath, defaultFolderPath);
        }
    }

    private void defaultFolderValidation(String defaultFolderPath) {
        if(!defaultFolderPath.matches(DEF_FOLDER_REGEXP)) {
           createErrorAlert("The def. folder should has pattern /-||-/-||-/ and contains only allowed for folder name characters").showAndWait();
        }
    }

    private void createDefaultFolder(String targetFolderPath, String defaultFolderPath) {
        String path = targetFolderPath + defaultFolderPath;
        boolean isCreated = new File(path).mkdirs();

        if (!isCreated) {
            createErrorAlert("The default folder has already created").showAndWait();
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
        defaultFolderField.setText(DEFAULT_FOLDER);
        targetFolderField.setDisable(true);
    }

    private Alert createErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation error");
        alert.setContentText(message);
        return alert;
    }
}
