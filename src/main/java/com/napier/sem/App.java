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
     * This is a one and all function that gets population for the world, continent, region, country, district and city.
     * This was done partly because I was annoyed looking at 6 different functions for essetinally the same thing
     * function use example
     * //////////////////////////////
     * Looking for Scotland which is a district
     * long a = getPopulation("district","Scotland")
     * //////////////////////////////
     * You can use displayPopulation to get and display this in one call displayPopulation("district","Scotland")
     * /////////////////////////////////
     * @param choice This decides what type of population you're looking for ie the world population you'd say 'world' or a region's you say 'region'
     * @param searching This decides the individual population you're looking at ie you wish to find Scotland which is a district you'd just say 'Scotland'. Ignored for 'world'.
     * @return The population, or -1 if it could not be found
     */
    public long getPopulation(String choice, String searching)
    {
        String sql = "";
        String alias = "";

        if (choice.equals("world"))
        {
            alias = "GlobalPopulation";
            sql = "SELECT SUM(Population) AS GlobalPopulation FROM country"; //String for everyone Pop
        }
        else if (choice.equals("continent"))
        {
            alias = "ContinentPopulation";
            sql = "SELECT SUM(Population) AS ContinentPopulation FROM country WHERE Continent = '" + searching + "'"; //String for Continent Pop
        }
        else if (choice.equals("region"))
        {
            alias = "RegionPopulation";
            sql = "SELECT SUM(Population) AS RegionPopulation FROM country WHERE Region = '" + searching + "'"; //String for Region Pop
        }
        else if (choice.equals("country"))
        {
            alias = "CountryPopulation";
            sql = "SELECT Population AS CountryPopulation FROM country WHERE Name = '" + searching + "'";  //String for Country Pop
        }
        else if (choice.equals("district"))
        {
            alias = "DistrictPopulation";
            sql = "SELECT SUM(Population) AS DistrictPopulation FROM city WHERE District = '" + searching + "'"; //String for District Pop
        }
        else if (choice.equals("city"))
        {
            alias = "CityPopulation";
            sql = "SELECT Population AS CityPopulation FROM city WHERE Name = '" + searching + "'"; //String for City Pop
        }
        else
        {
            System.out.println("Sorry you must've mispelled or this does not exist please try again.");
            return -1;
        }
        // Create an SQL statement
        try (
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(sql))
        {
            if (rset.next())
            {
                long pop = rset.getLong(alias);

                if (rset.wasNull())
                {
                    return -1; //Returns -1 if there is a failure as there cannot be a -1 population
                }
                else
                {
                    return pop;
                }
            }
            else
            {
                return -1; //Returns -1 if there is a failure as there cannot be a -1 population
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("could not get a  population for " + choice);
            return -1;
        }
    }

    /**
     * This is a one and all function that gets population for the world, continent, region, country, district and city.
     * This was done partly because I was annoyed looking at 6 different functions for essetinally the same thing
     * function use example
     * //////////////////////////////
     * Looking for Scotland which is a district
     * long a = displayPopulation("district, "Scotland")
     * //////////////////////////////
     * @param choice This decides what type of population you're looking for ie the world population you'd say 'world' or a region's you say 'region'
     * @param searching This decides the individual population you're looking at ie you wish to find Scotland which is a district you'd just say 'Scotland'. Ignored for 'world'.
     */
    public void displayPopulation(String choice, String searching)
    {
        long pop = getPopulation(choice, searching);

        System.out.printf("%-15s %-30s %-15s%n",
                "Type", "Search", "Population");

        if (pop >= 0)
        {
            System.out.printf("%-15s %-30s %-15d%n",
                    choice, searching, pop);
        }
        else
        {
            System.out.printf("%-15s %-30s %-15s%n",
                    choice, searching, "Not found");
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
                    "SELECT country.Code, country.Name, country.Continent, country.Region, "
                            + "country.Population, city.Name AS Capital "
                            + "FROM country "
                            + "LEFT JOIN city ON country.Capital = city.ID "
                            + "ORDER BY country.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            //Get Countries info
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next())
            {
                Country c = new Country();

                c.CountryCode = rset.getString("Code");
                c.CountryName = rset.getString("Name");
                c.Continent = rset.getString("Continent");
                c.Region = rset.getString("Region");
                c.CountryPopulation = rset.getInt("Population");
                c.CapitalName = rset.getString("Capital");

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

        System.out.printf("%-5s %-45s %-15s %-27s %-12s %-30s%n", "Code", "Name","Continent", "Region", "Population", "Capital");

        for (Country c : countries)
        {
            if (c == null) continue;
            System.out.printf("%-5s %-45s %-15s%n",
                    c.CountryCode, c.CountryName, c.CountryPopulation, c.Continent, c.Region, c.CountryPopulation, c.CapitalName);
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
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population "
                            + "FROM city "
                            + "JOIN country ON city.CountryCode = country.Code "
                            + "ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<City> cities = new ArrayList<City>();
            while (rset.next())
            {
                City c = new City();

                c.CityName = rset.getString("Name");
                c.CityCountry = rset.getString("Country");
                c.District = rset.getString("District");
                c.CityPopulation = rset.getDouble("Population");

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

        System.out.printf("%-35s %-45s %-20s %-12s%n", "Name", "Country", "District", "Population");

        for (City c : cities)
        {
            if (c == null) continue;
            System.out.printf("%-35s %-45s %-20s %-12s%n",
                    c.CityName, c.CityCountry, c.District, c.CityPopulation);
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
     * Main method (Where functions are called)
     */
    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        System.out.println(" ");
        System.out.println("FIRST REPORT FOR POPULATION// ALL COUNTRIES IN WORLD ORDERED BY LARGEST POPULATION TO SMALLEST ");
        System.out.println(" ");

        //Gets all countries and puts them organised by population descending
        ArrayList<Country> countries = a.getAllCountriesPopDesc();
        //Displays them
        a.displayCountriesPopDesc(countries);


        System.out.println(" ");
        System.out.println("SECOND REPORT FOR POPULATION// ALL CITIES IN THE WORLD ORDERED BY LARGEST POPULATION TO SMALLEST ");
        System.out.println(" ");

        //Gets all cities and puts them ordered by population descending
        ArrayList<City> cities = a.getAllCitiesPopDesc();
        //Displays them
        a.displayCitiesPopDesc(cities);

        System.out.println(" ");
        System.out.println("ADDITIONAL STUFF THAT THEY ASKED FOR IN THE BRIE");
        System.out.println(" ");


        System.out.println("Population for one country(United Kingdom)");
        System.out.println(" ");

        //Get an display the country's population
        a.displayPopulation("country", "United Kingdom");


        System.out.println(" ");
        System.out.println("Population for one city(Amsterdam)");
        System.out.println(" ");


        // Gets and displays city's population
        a.displayPopulation("city", "Amsterdam");

        System.out.println(" ");
        System.out.println("Population for one district(Scotland)");
        System.out.println(" ");

        // Gets and displays district's population
        a.displayPopulation("district", "Scotland");

        // Gets and displays globa Population
        System.out.println(" ");
        System.out.println("Population for the world");
        System.out.println(" ");


        a.displayPopulation("world", "World");



        System.out.println(" ");
        System.out.println("Population for a continent(Europe)");
        System.out.println(" ");


        // Gets and displays continent's population
        a.displayPopulation("continent", "Europe");

        System.out.println(" ");
        System.out.println("Population for a region(Western Europe)");
        System.out.println(" ");

        // Gets and displays region's population
        a.displayPopulation("region", "Western Europe");

        // Disconnect from database
        a.disconnect();
    }
}
