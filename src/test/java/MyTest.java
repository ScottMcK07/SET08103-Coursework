import com.napier.sem.App;
import com.napier.sem.City;
import com.napier.sem.Country;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


class AppTest {
    static App test;


    @BeforeAll
    static void init(){
        test = new App();


    }

    @AfterAll
    static void connectDone(){

    }


    // getPopulation Tests
    @Test
    /*
    This test is to test the exceptional boundaries of user input so things that lie outside the ability of the function ie galaxy searching population, and it should return -1 and say that it is wrong
     */
    void getPopInvalidChoiceReturnsMinusOne() {
        assertEquals(-1, test.getPopulation("galaxy", "Milk way"));
    }

    @Test
     /*
    This test should return minus one and say could not get a population for world because the database hasn't been connected essentially just checking the database
     */
    void worldPopulationNoConnectionHitsCatchAndReturnsMinusOne() {
        assertEquals(-1, test.getPopulation("world", ""));
    }

    @Test
      /*
    This test should return minus one and say could not get a population for  country because the database hasn't been connected essentially just checking the database
     */
    void countryPopulationNoConnectionReturnsMinusOne() {
        assertEquals(-1, test.getPopulation("country", "Netherlands"));
    }


    /*
    This test is for checking if blank input is not allowed for the choice
     */
    void getPopBlankChoiceReturnsMinusOne() {
        assertEquals(-1, test.getPopulation("", "world"));
    }

    // getAllCountriesPopDesc Tests



    @Test
     /*
    To test when the connection goes wrong in getallcountries it should return null
     */
    void getAllCountriesDescPopNoConnectionReturnsNull () {
        assertNull(test.getAllCountriesPopDesc());
        }


    //  getAllCitiesPopDesc Tests


    @Test
    /*
    To test when the connection goes wrong in getallcities it should return null
     */
    void getAllCitiesDescPopNoConnectionReturnsNull() {
        assertNull(test.getAllCitiesPopDesc());
    }

    //  DisplayCountriesPopDesc Tests

    @Test
    /*
    To test when there is a null list being displayed in displaying countries which should display no countries as there is none in the list
     */
    void DisplayCountriesPopDescNullListPrintsNoCountries() {
        test.displayCountriesPopDesc(null);
    }


    @Test
     /*
    To test if display countries will print every field correctly essentially extreme testing
     */
    void displayCountriesDescPopPrintsEveryField() {
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
    }


    //  DisplayCitiesDescPopNull Tests

    @Test
    /*
    To test when there is a null list being used when in displaying cities which should display no cities as there is nothing in the list
     */
    void displayCitiesDescPopNullListPrintsNoCities() {
        test.displayCitiesPopDesc(null);
    }

    @Test
    /*
     To test if display cities will print every field correctly essentially extreme testing using Edinburgh as an example
     */
    void displayCitiesPrintsEveryField() {
        City c = new City();
        c.CityName = "Edinburgh";
        c.CityCountry = "United Kingdom";
        c.District = "Scotland";
        c.CityPopulation = 450180;
        ArrayList<City> list = new ArrayList<>();
        list.add(c);

        test.displayCitiesPopDesc(list);

    }
}


