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
     * Get details of a country's population
     */
    public Country getCountryPop(String code)
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
     * Display Country's Population
     */
    public void displayCountryPop(Country c)
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
     * Get details of a country's population
     */
    public City getCityPop(String ID)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create SQL query
            String strSelect =
                    "SELECT ID, Name, Population "
                            + "FROM city "
                            + "WHERE ID = '" + ID + "'";

            // Execute SQL query
            ResultSet rset = stmt.executeQuery(strSelect);

            // Check whether a city was returned
            if (rset.next())
            {
                City c1 = new City();

                c1.CityID = rset.getString("ID");
                c1.CityName = rset.getString("Name");
                c1.CityPopulation = rset.getInt("Population");


                return c1;
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
     * Display Country's Population
     */
    public void DisplayCityPop(City c1)
    {
        if (c1 != null)
        {
            System.out.println(
                    "ID: " + c1.CityID + "\n"
                            + "Name: " + c1.CityName + "\n"
                            + "Population: " + c1.CityPopulation + "\n"


            );
        }
        else
        {
            System.out.println("City not found.");
        }
    }

    /**
     * Gets Details on District Population
     */


    public City getDistrictPop(String District)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create SQL query
            String strSelect =
                    "SELECT District, SUM(Population) As DistrictPopulation "
                            + "FROM city "
                            + "WHERE District = '" + District + "'";

            // Execute SQL query
            ResultSet rset = stmt.executeQuery(strSelect);

            // Check whether a city was returned
            if (rset.next())
            {
                City c2 = new City();


                c2.District = rset.getString("District");
                c2.CityPopulation = rset.getInt("DistrictPopulation");

                return c2;
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
     * Display District's Population
     */
    public void DisplayDistrictPop(City c2)
    {
        if (c2 != null)
        {
            System.out.println(

                            "District Name: " + c2.District + "\n"
                            + "District Population: " + c2.CityPopulation + "\n"


            );
        }
        else
        {
            System.out.println("District not found.");
        }
    }




    /**
     * Gets all the countries in the world, organised by largest population to smallest.
     * @return A list of all countries, or null if there is an error.
     */
    public ArrayList<Country> getAllCountriesPopDesc()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Code, country.Name, country.Population "
                            + "FROM country "
                            + "ORDER BY country.Population DESC "
                            + "LIMIT 20";
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
     * Displays all Countries from largest to smallest population
     */
    public void displayCountriesPopDesc(ArrayList<Country> countries)
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
     * This will get the population of the world
     * @return World population or an error if something goes wrong
     */

    public Long getWorldPop()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create SQL query
            String strSelect =
                    "SELECT SUM(Population) As GlobalPopulation "
                            + "FROM country ";

            // Execute SQL query
            ResultSet rset = stmt.executeQuery(strSelect);

            // Check whether a city was returned
            if (rset.next())
            {





                return rset.getLong("GlobalPopulation");
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get World Population");
            return null;
        }
    }
    /**
     * Display the population of the Worl d
     */
    public void DisplayWorldPop(long worldpop )
    {
        if (worldpop >= 0)
        {
            System.out.println("World Population: " + worldpop );
        }
        else
        {
            System.out.println("World Population not found.");
        }
    }

    /**
     * Gets all the cities in the world, organised by largest population to smallest.
     * @return A list of all cities ordered from largest population to smallest, or null if there is an error.
     */
    public ArrayList<City> getAllCitiesPopDesc()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.CountryCode, city.Name, city.Population "
                            + "FROM city "
                            + "ORDER BY city.Population DESC "
                            + "LIMIT 20";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<City> cities = new ArrayList<City>();
            while (rset.next())
            {
                City c = new City();
                c.CityCode = rset.getString("city.CountryCode");
                c.CityName = rset.getString("city.Name");
                c.CityPopulation = rset.getInt("city.Population");
                cities.add(c);
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get all city details");
            return null;
        }
    }

    /**
     * Displays all cities from largest to smallest population
     */
    public void displayCitiesPopDesc(ArrayList<City> cities)
    {
        if (cities == null)
        {
            System.out.println("No cities");
            return;
        }

        System.out.printf("%-5s %-45s %-15s%n", "Code", "Name", "Population");

        for (City c : cities)
        {
            if (c == null) continue;
            System.out.printf("%-5s %-45s %-15s%n",
                    c.CityCode, c.CityName, c.CityPopulation);
        }
    }

    public Country getContinentPop(String Continent)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create SQL query
            String strSelect =
                    "SELECT Continent, SUM(Population) As ContinentPopulation "
                            + "FROM country "
                            + "WHERE Continent = '" + Continent + "' "
                            + "GROUP BY Continent";

            // Execute SQL query
            ResultSet rset = stmt.executeQuery(strSelect);

            // Check whether a continent was returned
            if (rset.next())
            {
                Country cont = new Country();


                cont.Continent = rset.getString("Continent");
                cont.CountryPopulation = rset.getInt("ContinentPopulation");

                return cont;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Continent details");
            return null;
        }
    }
    /**
     * Display Continent's Population
     */
    public void DisplayContinentPop(Country cont)
    {
        if (cont != null)
        {
            System.out.println(

                            "Continent Name: " + cont.Continent + "\n"
                            + "Continent Population: " + cont.CountryPopulation + "\n"


            );
        }
        else
        {
            System.out.println("Continent not found.");
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


        System.out.println(" ");
        System.out.println("FIRST QUERY// ONE COUNTRY'S POPULATION(GREAT BRITAN)");
        System.out.println(" ");


        //Get Country to display population
        Country c = a.getCountryPop("GBR");

        //Display The country's population

        a.displayCountryPop(c);


        System.out.println(" ");
        System.out.println("SECOND QUERY// POPULATION OF A CITY(AMSTERDAM)");
        System.out.println(" ");


        // Gets city's population
        City c1 = a.getCityPop("5");



        // displays city's population
        a.DisplayCityPop(c1);





        System.out.println(" ");
        System.out.println("THIRD QUERY// POPULATION OF A DISTRICT(SCOTLAND)");
        System.out.println(" ");


        // Gets district's population
        City c2 = a.getDistrictPop("Scotland");



        // displays district's population
        a.DisplayDistrictPop(c2);


        System.out.println(" ");
        System.out.println("FOURTH QUERY// ALL COUNTRIES IN WORLD ORDERED BY LARGEST POPULATION TO SMALLEST LIMITED BY 20");
        System.out.println(" ");

        //Gets all countries and puts them ordered by population descending
        ArrayList<Country> countries = a.getAllCountriesPopDesc();
        //Displays them
        a.displayCountriesPopDesc(countries);

        System.out.println(" ");
        System.out.println("FIFTH QUERY// GLOVAL POPULATION OF EVERYONE");
        System.out.println(" ");

        // Gets Global Population
        long worldpop = a.getWorldPop();

        //Displays Global Population
        a.DisplayWorldPop(worldpop);

        System.out.println(" ");
        System.out.println("SIXTH QUERY// ALL CITIES IN WORLD ORDERED BY LARGEST POPULATION TO SMALLEST LIMITED BY 20");
        System.out.println(" ");

        //Gets all cities and puts them ordered by population descending
        ArrayList<City> cities = a.getAllCitiesPopDesc();
        //Displays them
        a.displayCitiesPopDesc(cities);


        System.out.println(" ");
        System.out.println("SEVENTH QUERY// POPULATION OF A CONTINENT(EUROPE)");
        System.out.println(" ");


        // Gets continent's population
        Country cont = a.getContinentPop("Europe");



        // displays continent's population
        a.DisplayContinentPop(cont);
        // Disconnect from database
        a.disconnect();
    }
}
