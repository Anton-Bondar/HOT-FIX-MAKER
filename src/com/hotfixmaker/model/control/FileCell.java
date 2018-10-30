package com.hotfixmaker.model.control;

import com.hotfixmaker.model.SelectedFile;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;

public class FileCell extends ListCell<SelectedFile> {

    @Override
    public void updateItem(SelectedFile item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(item.getName());
            setTooltip(new Tooltip(item.getPath()));
        } else {
            setText("");
            setTooltip(null);
        }
    }
}
