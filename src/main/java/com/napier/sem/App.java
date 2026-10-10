package com.napier.sem;

import java.sql.*;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;

        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");

            try {
                // Wait a bit for db to start
                Thread.sleep(30000);

                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");


                System.out.println("Successfully connected");
                break;

            } catch (SQLException sqle) {
                System.out.println(
                        "Failed to connect to database attempt " + i
                );
                System.out.println(sqle.getMessage());

            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    /**
     * Get details of a country from its code.
     */
    public Country getCountry(String code)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create SQL query
            String strSelect =
                    "SELECT Code, Name, Continent, Region, SurfaceArea, "
                            + "IndepYear, Population, LifeExpectancy, GNP, GNPOld, "
                            + "LocalName, GovernmentForm, HeadOfState, Capital, Code2 "
                            + "FROM country "
                            + "WHERE Code = '" + code + "'";

            // Execute SQL query
            ResultSet rset = stmt.executeQuery(strSelect);

            // Check whether a country was returned
            if (rset.next())
            {
                Country c = new Country();

                c.CountryCode = rset.getString("Code");
                c.CountryName = rset.getString("Name");
                c.Continent = rset.getString("Continent");
                c.Region = rset.getString("Region");
                c.SurfaceArea = rset.getDouble("SurfaceArea");
                int indepYear = rset.getInt("IndepYear");
                c.IndepYear = rset.wasNull() ? null : indepYear;
                c.CountryPopulation = rset.getInt("Population");
                double lifeExpectancy = rset.getDouble("LifeExpectancy");
                c.LifeExpectancy = rset.wasNull() ? null : lifeExpectancy;
                double gnp = rset.getDouble("GNP");
                c.GNP = rset.wasNull() ? null : gnp;
                double gnpOld = rset.getDouble("GNPOld");
                c.GNPOld = rset.wasNull() ? null : gnpOld;
                c.LocalName = rset.getString("LocalName");
                c.GovernmentForm = rset.getString("GovernmentForm");
                c.HeadOfState = rset.getString("HeadOfState");
                int capital = rset.getInt("Capital");
                c.Capital = rset.wasNull() ? null : capital;
                c.CountryCode2 = rset.getString("Code2");

                return c;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }
    /**
     * Display Country Details
     */
    public void displayCountry(Country c)
    {
        if (c != null)
        {
            System.out.println(
                            "Country Code: " + c.CountryCode + "\n"
                            + "Name: " + c.CountryName + "\n"
                            + "Continent: " + c.Continent + "\n"
                            + "Region: " + c.Region + "\n"
                            + "Surface Area: " + c.SurfaceArea + "\n"
                            + "Independence Year: " + c.IndepYear + "\n"
                            + "Population: " + c.CountryPopulation + "\n"
                            + "Life Expectancy: " + c.LifeExpectancy + "\n"
                            + "GNP: " + c.GNP + "\n"
                            + "Old GNP: " + c.GNPOld + "\n"
                            + "Local Name: " + c.LocalName + "\n"
                            + "Government: " + c.GovernmentForm + "\n"
                            + "Head of State: " + c.HeadOfState + "\n"
                            + "Capital: " + c.Capital + "\n"
                            + "2-letter country Code: " + c.CountryCode2 + "\n"

            );
        }
        else
        {
            System.out.println("Country not found.");
        }
    }
    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Database disconnected");
            } catch (SQLException e) {
                System.out.println(
                        "Error closing connection to database"
                );
            }
        }
    }

    /**
     * Main method: starts the application.
     */
    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();
        System.out.println("PLEASE WORK");


        //Get Country
        Country c = a.getCountry("ARG");

        //Display The country

        a.displayCountry(c);

        // Disconnect from database
        a.disconnect();
    }
}
