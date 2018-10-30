package com.hotfixmaker.factory;

import com.hotfixmaker.model.SelectedFile;
import com.hotfixmaker.model.control.FileCell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class FileCellFactory implements Callback<ListView<SelectedFile>, ListCell<SelectedFile>> {

    @Override
    public ListCell<SelectedFile> call(ListView<SelectedFile> param) {
        return new FileCell();
    }
}
