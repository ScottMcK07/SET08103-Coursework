import com.napier.sem.App;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


class MyTest
{

    @Test
    /*
    This test is to test the exceptional boundaries of user input so things that lie outside the ability of the function ie galaxy searching population, and it should return -1 and say that it is wrong
     */
    void getPopInvalidChoiceReturnsMinusOne(){
        App test = new App();

        long result = test.getPopulation("galaxy", "Milky-way");

        assertEquals(-1, result);


    }
}
