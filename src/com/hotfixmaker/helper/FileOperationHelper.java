package com.hotfixmaker.helper;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static com.hotfixmaker.model.message.HFMMessage.HFM7;

public class FileOperationHelper {

    public static File createTempFolder(String targetFolderPath) throws IOException {
        File tmpFolder = new File(targetFolderPath, "/tmp");
        removeTmpFolder(tmpFolder);
        tmpFolder.mkdir();
        return tmpFolder;
    }

    public static void removeTmpFolder(File tmpFolder) throws IOException {
        if (tmpFolder.exists()) {
            Files.walk(tmpFolder.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    public static void removeOldZipArchive(File archiveFile) throws IOException {
        if (Files.exists(archiveFile.toPath())) {
            Files.delete(archiveFile.toPath());
            AlertHelper.create(HFM7.get(), Alert.AlertType.WARNING, "Warning").show();
        }
    }

    public static File createDefaultFolder(String tmpFolder, String defaultFolderPath) {
        File defaultFolder;

        if (defaultFolderPath != null) {
            defaultFolder = new File(tmpFolder, defaultFolderPath);
        } else {
            defaultFolder = new File(tmpFolder);
        }

        defaultFolder.mkdirs();

        return defaultFolder;
    }
}
