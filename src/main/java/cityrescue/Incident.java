package cityrescue;

import cityrescue.enums.IncidentStatus;
import cityrescue.enums.IncidentType;

/**
 * This class defines an incident's main attributes and also contains a constructor to pass in required values.
 */
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

    /**
     * This constructor is used to instantiate an incident.
     * @param status An enum value from the IncidentStatus enum
     * @param type An enum value from the IncidentType enum
     * @param severity Severity of the new incident
     * @param xCoord X coordinate of the new incident
     * @param yCoord Y coordinate of the new incident
     */
    public Incident(IncidentStatus status, IncidentType type, int severity, int xCoord, int yCoord) {
        this.status = status;
        this.type = type;
        this.severity = severity;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.incidentID = ++numOfIncidents;
    }
}
