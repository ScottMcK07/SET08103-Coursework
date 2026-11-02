package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {
    //Used for integration testing which is essentially just unit testing but for ones that can be connected to the database
    // You're essentially looking to  write integration tests to ensure that your application works correctly in all cases.
    // There is an example on lab7 that shows you how to write one of these but specify it for this app of course
    // and just add similar tests that test all the pathways and conditions through the code.

    static App app;

    @BeforeAll
    static void init() {
        app = new App();
        app.connect("localhost:33060", 30000);
    }

    @AfterAll
    static void tearDown() {
        if (app != null) {
            app.disconnect();
        }
    }

    // Population  integration tests
    @Test
    void testWorldPopulation() {
        long population = app.getPopulation("world","");

        assertTrue(population > 0, "World population should be greater than zero");
    }

    @Test
    void testContinentPopulation() {
        long population = app.getPopulation("continent", "Europe");

        assertTrue(population > 0, "Europe population should be greater than zero");
    }

    @Test
    void testRegionPopulation() {
        long population = app.getPopulation("region", "Western Europe");

        assertTrue(population > 0, "Western Europe population should be greater than zero");
    }

    @Test
    void testCountryPopulation() {
        long population = app.getPopulation("country", "United Kingdom");

        assertTrue(population > 0, "United Kingdom population should be greater than zero");
    }

    @Test
    void testDistrictPopulation() {
        long population = app.getPopulation("district", "Scotland");

        assertTrue(population > 0, "Scotland population should be greater than zero");
    }

    @Test
    void testCityPopulation() {
        long population = app.getPopulation("city", "Amsterdam");

        assertTrue(population > 0, "Amsterdam population should be greater than zero");
    }

    @Test
    void testInvalidPopulationType() {
        long population = app.getPopulation("edinburgh", "Scotland");

        assertEquals(-1, population, "Invalid population type should return -1");
    }

    @Test
    void testNonExistentCountryPopulation() {
        long population = app.getPopulation("country", "NonExistentCountry");

        assertEquals(-1, population, "Non-existent country should return -1");
    }

    @Test
    void testNonExistentCityPopulation() {
        long population = app.getPopulation("city", "NonExistentCity");

        assertEquals(-1, population, "Non-existent city should return -1");
    }

}