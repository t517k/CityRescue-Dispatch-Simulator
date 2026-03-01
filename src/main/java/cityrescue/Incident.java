package cityrescue;

import cityrescue.enums.IncidentStatus;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

import java.util.ArrayList;

public class Incident {
    IncidentType type;
    IncidentStatus status;
    static int numOfIncidents = 0;
    int severity;
    int xCoord;
    int yCoord;
    int incidentID;
    Unit dispatchedUnit;
    int ticksLeftToResolve;

    public Incident(IncidentStatus status, IncidentType type, int severity, int xCoord, int yCoord) {
        this.status = status;
        this.type = type;
        this.severity = severity;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.incidentID = ++numOfIncidents;
    }

    // Methods...
    // set status
    // choose unit
}
