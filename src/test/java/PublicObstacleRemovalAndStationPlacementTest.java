import cityrescue.CityRescue;
import cityrescue.CityRescueImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PublicObstacleRemovalAndStationPlacementTest {
    private CityRescue cr;

    @BeforeEach
    void setUp() throws Exception {
        cr = new CityRescueImpl();
        cr.initialise(10, 10);
    }

    @Test
    void removeObstacleAndAddStation() throws Exception {
        cr.addObstacle(1, 1);
        cr.removeObstacle(1, 1);
        assertDoesNotThrow(() -> cr.addStation("A", 1, 1));
    }
}
