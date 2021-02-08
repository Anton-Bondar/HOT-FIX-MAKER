package com.hotfixmaker.db.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotfixmaker.model.dto.Session;

import java.io.File;
import java.io.IOException;

public class DBService {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String SESSION_FILE_NAME = "current_session.json";

    public static void save(Session session) throws IOException {
        objectMapper.writeValue(new File(SESSION_FILE_NAME), session);
    }

    public static Session load() throws IOException {
        return new File(SESSION_FILE_NAME).exists() ?
                objectMapper.readValue(new File(SESSION_FILE_NAME), Session.class)
                : null;
    }

}
