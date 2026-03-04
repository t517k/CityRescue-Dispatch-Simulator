import cityrescue.CityRescue;
import cityrescue.CityRescueImpl;
import cityrescue.enums.IncidentType;
import cityrescue.exceptions.InvalidSeverityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PublicEscalateTest {
    private CityRescue cr;

    @BeforeEach
    void setUp() throws Exception {
        cr = new CityRescueImpl();
        cr.initialise(10, 10);
    }

    @Test
    void Escalate() throws Exception {
        int iid = cr.reportIncident(IncidentType.FIRE, 3, 1, 1);
        assertDoesNotThrow(() -> cr.escalateIncident(iid, 4));
    }

    @Test
    void EscalateBadSeverity() throws Exception {
        int iid = cr.reportIncident(IncidentType.FIRE, 3, 1, 1);
        assertThrows(InvalidSeverityException.class, () -> cr.escalateIncident(iid, 20));
    }

    @Test
    void EscalateBadSeverity2() throws Exception {
        int iid = cr.reportIncident(IncidentType.FIRE, 3, 1, 1);
        assertThrows(InvalidSeverityException.class, () -> cr.escalateIncident(iid, -20));
    }

    @Test
    void EscalateBadState() throws Exception {
        int iid = cr.reportIncident(IncidentType.FIRE, 3, 1, 1);
        cr.cancelIncident(iid);
        assertThrows(IllegalStateException.class, () -> cr.escalateIncident(iid, 4));
    }
}
