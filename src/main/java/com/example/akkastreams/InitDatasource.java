package com.example.akkastreams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InitDatasource {

    @Autowired
    private static DatabaseConfig databaseConfig;

    public static void main(String[] args) {
        SpringApplication.run(InitDatasource.class, args);
        try {
            System.out.println("Trying to establish database connection...");
            Connection connection = DriverManager.getConnection(
                    databaseConfig.getUrl(),
                    databaseConfig.getUser(),
                    databaseConfig.getPwd()
            );
            Statement statement = connection.createStatement();
            // Initialize object for ScripRunner
            ScriptRunner sr = new ScriptRunner(connection);
            // Give the input file to Reader
            String dataStructureFilePath = "src/main/java/resources/db-structure.sql";
            Reader reader = new BufferedReader(new FileReader(dataStructureFilePath));
            // Execute script
            System.out.println("Starting database initialisation...");
            sr.runScript(reader);
            // Parse files and populate data (in a page by page way)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}