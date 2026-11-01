package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



public class AppIntegrationTest {
    //Used for integration testing which is essentially just unit testing but for ones that can be connected to the database
    // You're essentially looking to  write integration tests to ensure that your application works correctly in all cases.
    // You have the template in AppIntegrationTest.java. Add similar tests that test all the pathways and conditions through your code.

    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);

    }


    //Put Integration testing here
}
