package com.napier.sem;

import java.sql.*;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

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
                    "SELECT Code, Name, Population "
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
                c.CountryPopulation = rset.getInt("Population");

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
     * Display Country's Details
     */
    public void displayCountry(Country c)
    {
        if (c != null)
        {
            System.out.println(
                            "Country Code: " + c.CountryCode + "\n"
                            + "Name: " + c.CountryName + "\n"
                            + "Population: " + c.CountryPopulation + "\n"


            );
        }
        else
        {
            System.out.println("Country not found.");
        }
    }

    /**
     * Gets all the countries in the world, organised by largest population to smallest.
     * @return A list of all countries, or null if there is an error.
     */
    public ArrayList<Country> getAllCountries()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Code, country.Name, country.Population "
                            + "FROM country "
                            + "ORDER BY country.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next())
            {
                Country c = new Country();
                c.CountryCode = rset.getString("country.Code");
                c.CountryName = rset.getString("country.Name");
                c.CountryPopulation = rset.getInt("country.Population");
                countries.add(c);
            }
            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Displays all Countries
     */
    public void displayCountries(ArrayList<Country> countries)
    {
        if (countries == null)
        {
            System.out.println("No countries");
            return;
        }

        System.out.printf("%-5s %-45s %-15s%n", "Code", "Name", "Population");

        for (Country c : countries)
        {
            if (c == null) continue;
            System.out.printf("%-5s %-45s %-15s%n",
                    c.CountryCode, c.CountryName, c.CountryPopulation);
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

        System.out.println("FIRST QUERY// ONE COUNTRY'S POPULATION(GREAT BRITAN)");

        //Get Country to display population
        Country c = a.getCountry("GBR");

        //Display The country's population

        a.displayCountry(c);

       System.out.println("SECOND QUERY// ALL COUNTRIES IN WORLD ORDERED BY POP");

        //Gets all countries and puts them ordered by population descending
        ArrayList<Country> countries = a.getAllCountries();
        //Displays them
        a.displayCountries(countries);





        // Disconnect from database
        a.disconnect();
    }
}
