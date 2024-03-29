package com.hotfixmaker;

import com.hotfixmaker.creator.FixPackageStructureCreator;
import com.hotfixmaker.creator.ReportApplicationCreator;
import com.hotfixmaker.creator.ZipArchiveCreator;
import com.hotfixmaker.db.service.DBService;
import com.hotfixmaker.helper.AlertHelper;
import com.hotfixmaker.helper.FileOperationHelper;
import com.hotfixmaker.helper.LoggerHelper;
import com.hotfixmaker.helper.ValidationHelper;
import com.hotfixmaker.model.SelectedFile;
import com.hotfixmaker.model.dto.Session;
import com.hotfixmaker.model.exception.HFMValidationException;
import com.hotfixmaker.model.factory.FileCellFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.hotfixmaker.model.message.HFMMessage.*;
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

public class Controller implements Initializable {

    private static final String DEFAULT_FOLDER = "\\applications\\NetCracker\\APP-INF\\classes";

    public static final String VALIDATION_ERROR = "Validation error";
    public static final String EXECUTION_ERROR = "Execution error";

    private static Logger LOGGER = Logger.getLogger(Controller.class);

    private ObservableList<SelectedFile> selectedFiles;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField nameField;
    @FXML
    private TextField targetFolderField;
    @FXML
    private TextField defaultFolderField;
    @FXML
    private ListView<SelectedFile> filesList;
    @FXML
    private CheckBox notRemoveTmpFolderCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filesList.setCellFactory(new FileCellFactory());
        selectedFiles = FXCollections.observableArrayList();
        defaultFolderField.setText(DEFAULT_FOLDER);
        targetFolderField.setDisable(true);
        try {
            Session session = DBService.load();
            if (session != null) loadPrevSessionOnView(session);
        } catch (IOException e) {
            AlertHelper.create(HF11.get(), Alert.AlertType.ERROR, EXECUTION_ERROR).showAndWait();
        }
    }

    @FXML
    private void onClickOk() {
        try {
            String archiveName = nameField.getText();
            String targetFolderPath = targetFolderField.getText();
            String defaultFolderPath = defaultFolderField.getText();
            ObservableList<SelectedFile> filesForPacking = filesList.getItems();

            ValidationHelper.emptyValidation(archiveName, filesForPacking);
            ValidationHelper.defaultFolderValidation(defaultFolderPath);

            File archiveFile = new File(targetFolderPath, archiveName + ".zip");
            FileOperationHelper.removeOldZipArchive(archiveFile);

            File tempFolder = FileOperationHelper.createTempFolder(targetFolderPath);
            LoggerHelper.initLogger(tempFolder, LOGGER);
            LoggerHelper.logInputParams(archiveName, targetFolderPath, defaultFolderPath, filesForPacking, LOGGER);

            File targetFolderForClasses = FileOperationHelper.createDefaultFolder(tempFolder.getPath(), defaultFolderPath);
            File rootArchiveFolder = tempFolder.listFiles()[0];

            FixPackageStructureCreator.process(targetFolderForClasses.getPath(), filesForPacking);
            ZipArchiveCreator.process(archiveName, targetFolderPath, rootArchiveFolder);

            ReportApplicationCreator.process(archiveFile);
            if (!notRemoveTmpFolderCheckBox.isSelected()) {
                LOGGER.shutdown();
                FileOperationHelper.removeTmpFolder(tempFolder);
            }
        } catch (HFMValidationException e) {
            LOGGER.error(e.getMessage(), e);
            AlertHelper.create(e.getMessage(), Alert.AlertType.ERROR, VALIDATION_ERROR).showAndWait();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            AlertHelper.create(HFM1.get(), Alert.AlertType.ERROR, EXECUTION_ERROR).showAndWait();
        }
    }

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
        if (!selectedFiles.isEmpty()) {
            String lastLocationPath = selectedFiles.get(selectedFiles.size() - 1).getFile().getParent();
            fileChooser.setInitialDirectory(new File(lastLocationPath));
        }
        List<File> prevSelectedFiles = selectedFiles.stream().map(SelectedFile::getFile).collect(Collectors.toList());
        final Stage stage = (Stage) mainPane.getScene().getWindow();
        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        if (files != null && !files.isEmpty()) {
            try {
                for (File file : files) {
                    if (prevSelectedFiles.contains(file)) {
                        throw new HFMValidationException(HFM6.get());
                    }
                    if (file.getPath().split("classes").length == 2) {
                        selectedFiles.add(new SelectedFile(file));
                    } else {
                        throw new HFMValidationException(HFM2.get());
                    }
                }
            } catch (HFMValidationException e) {
                AlertHelper.create(e.getMessage(), Alert.AlertType.ERROR, VALIDATION_ERROR).showAndWait();
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
    public void onReset() {
        Alert confirmation = AlertHelper.create(HFM9.get(), CONFIRMATION, "Reset fields");
        Optional<ButtonType> option = confirmation.showAndWait();
        if (option.get() == ButtonType.OK) {
            resetFields();
        }
    }

    public Session getSessionBeanFromView() {
        Session session = new Session();
        session.setName(nameField.getText());
        session.setTargetFolder(targetFolderField.getText());
        session.setDefaultServerAppFolder(defaultFolderField.getText());
        session.setFiles(filesList.getItems());
        session.setNotRemoveTmpFolder(notRemoveTmpFolderCheckBox.isSelected());
        return session;
    }

    private void resetFields() {
        nameField.clear();
        targetFolderField.clear();
        defaultFolderField.setText(DEFAULT_FOLDER);
        filesList.getItems().clear();
        notRemoveTmpFolderCheckBox.setSelected(false);
    }

    private void loadPrevSessionOnView(Session session) {
        nameField.setText(session.getName());
        targetFolderField.setText(session.getTargetFolder());
        defaultFolderField.setText(session.getDefaultServerAppFolder());
        filesList.getItems().addAll(session.getFiles());
        notRemoveTmpFolderCheckBox.setSelected(session.isNotRemoveTmpFolder());
    }
}
