package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



public class AppIntegrationTest {
    //Used for integration testing which is essentially just unit testing but for ones that can be connected to the database
    // You're essentially looking to  write integration tests to ensure that your application works correctly in all cases.
    // There is an example on lab7 that shows you how to write one of these but specify it for this app of course
    // and just add similar tests that test all the pathways and conditions through the code.

    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);

    }


    //Put Integration testing here
}
