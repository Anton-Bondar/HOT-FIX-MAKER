package com.hotfixmaker.helper;

import com.hotfixmaker.model.SelectedFile;
import com.hotfixmaker.model.exception.HFMValidationException;
import javafx.collections.ObservableList;

import static com.hotfixmaker.model.message.HFMMessage.*;

public class ValidationHelper {

    private static final String DEF_FOLDER_REGEXP = "^(\\\\)([a-zA-Z_\\\\\\-\\s0-9!+=&@#$%^&()\\[\\]{}]+)";

    public static void defaultFolderValidation(String defaultFolderPath) throws HFMValidationException {
        if (!defaultFolderPath.isEmpty() && !defaultFolderPath.matches(DEF_FOLDER_REGEXP)) {
            throw new HFMValidationException(HFM3.get());
        }
    }

    public static void emptyValidation(String name, ObservableList<SelectedFile> selectedFiles) throws HFMValidationException {
        if (name.isEmpty()) {
            throw new HFMValidationException(HFM4.get());
        }
        if (selectedFiles.isEmpty()) {
            throw new HFMValidationException(HFM5.get());
        }
    }
}
