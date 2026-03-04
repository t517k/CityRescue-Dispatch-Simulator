import cityrescue.CityRescue;
import cityrescue.CityRescueImpl;
import cityrescue.Unit;
import cityrescue.enums.UnitType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PublicOutOfServiceTest {
    private CityRescue cr;

    @BeforeEach
    void setUp() throws Exception {
        cr = new CityRescueImpl();
        cr.initialise(10, 10);
    }

    @Test
    void outOfServiceTest() throws Exception {
        int sid = cr.addStation("A", 1, 1);
        int uid = cr.addUnit(sid, UnitType.AMBULANCE);
        assertDoesNotThrow(() -> cr.setUnitOutOfService(uid, true));
        assertDoesNotThrow(() -> cr.setUnitOutOfService(uid, false));

    }
}
