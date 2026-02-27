package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class Incident {
    IncidentType type;
    static int numOfIncidents = 0;
    int severity;
    int xCoord;
    int yCoord;
    int incidentID;

    public Incident(IncidentType type, int severity, int xCoord, int yCoord) {
        this.type = type;
        this.severity = severity;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.incidentID = ++numOfIncidents;
    }

    // Attributes...
    // time of report
    // time of resolution
    // status
    // incident ID

    // Methods...
    // set status
    // choose unit
}
