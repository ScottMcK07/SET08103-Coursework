import com.napier.sem.App;
import com.napier.sem.City;
import com.napier.sem.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class AppTest {

    private App test;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;


    @BeforeEach
    void setUp() {
        test = new App();
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String output() {
        return out.toString();
    }


    @Test
    /*
    This test is to test the exceptional boundaries of user input so things that lie outside the ability of the function ie galaxy searching population, and it should return -1 and say that it is wrong
     */
    void getPopInvalidChoiceReturnsMinusOne() {


        long result = test.getPopulation("galaxy", "Milky-way");

        assertEquals(-1, result);
    }
    @Test
     /*
    To test when the connection goes wrong in getallcountries it should return null
     */
    void getAllCountries_noConnection_returnsNull () {
        assertNull(test.getAllCountriesPopDesc());
        }

    @Test
    /*
    To test when the connection goes wrong in getallcities it should return null
     */
    void getAllCities_noConnection_returnsNull() {
        assertNull(test.getAllCitiesPopDesc());
    }


}


