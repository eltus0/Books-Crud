package org.david.bookscrud.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Credentials {

    private String url;
    private String username;
    private String password;

    public Credentials() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/resources/config.properties"));
            properties.load(inputStream);

            url = properties.getProperty("URL");
            username = properties.getProperty("USERNAME");
            password = properties.getProperty("PASSWORD");
        } catch (IOException e) {
            System.err.println("file properties not found: " + e.getMessage());
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
