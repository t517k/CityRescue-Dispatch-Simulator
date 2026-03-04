import cityrescue.CityRescue;
import cityrescue.CityRescueImpl;
import cityrescue.exceptions.InvalidGridException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PublicInitialiseGridTest {
    private CityRescue cr;

    @ParameterizedTest
    @CsvSource({
            "5, 0",
            "0, 5",
            "0, 0",
            "-5, 5",
            "5, -5"
    })
    void setUp(int x, int y) throws Exception {
        cr = new CityRescueImpl();
        assertThrows(InvalidGridException.class, () -> cr.initialise(x, y));
    }
}
