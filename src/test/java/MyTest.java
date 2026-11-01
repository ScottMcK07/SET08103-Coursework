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
    void getAllCountriesNoConnectionReturnsNull () {
        assertNull(test.getAllCountriesPopDesc());
        }

    @Test
    /*
    To test when the connection goes wrong in getallcities it should return null
     */
    void getAllCitiesNoConnectionReturnsNull() {
        assertNull(test.getAllCitiesPopDesc());
    }

    @Test
    /*
    To test when there is a null list being displayed in displaying countries which should display no countries as there is none in the list
     */
    void DisplayCountriesNullListPrintsNoCountries() {
        test.displayCountriesPopDesc(null);
        assertTrue(output().contains("No countries"));
    }


    @Test
     /*
    To test if display countries will print every field correctly essentially extreme testing
     */
    void displayCountriesPrintsEveryField() {
        Country c = new Country();
        c.CountryCode = "NLD";
        c.CountryName = "Netherlands";
        c.Continent = "Europe";
        c.Region = "Western Europe";
        c.CountryPopulation = 1586400;
        c.CapitalName = "Amsterdam";
        ArrayList<Country> list = new ArrayList<>();
        list.add(c);

        test.displayCountriesPopDesc(list);

        String o = output();
        assertTrue(o.contains("NLD"));
        assertTrue(o.contains("Netherlands"));
        assertTrue(o.contains("Europe"));
        assertTrue(o.contains("Western Europe"));
        assertTrue(o.contains("1586400"));
        assertTrue(o.contains("Amsterdam"));
    }
}


