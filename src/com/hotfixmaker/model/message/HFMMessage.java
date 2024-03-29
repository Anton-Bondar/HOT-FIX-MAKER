package com.hotfixmaker.model.message;

public enum HFMMessage {

    HFM1("[HFM1] Error while hot fix creation see log for details"),

    // Validation messages
    HFM2("[HFM2] The file path should contains one 'classes' folder"),
    HFM3("[HFM3] The default folder path should has pattern /-||-/-||-/ and contains only allowed for folder name characters"),
    HFM4("[HFM4] Name value must be specified"),
    HFM5("[HFM5] No files selected"),
    HFM6("[HFM6] The file(s) already added"),

    // Notification messages
    HFM7("[HFM7] Old zip archive was deleted"),
    HFM8("[HFM8] Hot fix successfully created"),

    //Confirmation messages
    HFM9("[HFM9] Do you want to reset all fields to default?"),
    HF10("[HFM10] Error during current session saving"),
    HF11("[HFM11] Error during prev. session loading. Please try to remove session.json");

    private String message;

    private HFMMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
