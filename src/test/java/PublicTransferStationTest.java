import cityrescue.CityRescue;
import cityrescue.CityRescueImpl;
import cityrescue.enums.UnitType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PublicTransferStationTest {
    private CityRescue cr;

    @BeforeEach
    void setUp() throws Exception {
        cr = new CityRescueImpl();
        cr.initialise(10, 10);
    }

    @Test
    void checkUnitTransferLogic() throws Exception {
        int sid1 = cr.addStation("A", 1, 1);
        int sid2 = cr.addStation("B", 2, 2);
        int uid1 = cr.addUnit(sid1, UnitType.AMBULANCE);
        cr.transferUnit(1, sid2);
        String s = cr.getStatus();
        assertTrue(cr.viewUnit(uid1).contains("LOC=(2,2)"));
    }
}
