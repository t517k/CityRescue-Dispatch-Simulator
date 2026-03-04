package cityrescue;

import cityrescue.enums.UnitType;
import cityrescue.enums.UnitStatus;

/**
 * Ambulance
 * This class extends the unit class and is specialised at dealing with incidents of the MEDICAL type.
 * All attributes are inherited from Unit and the constructor assigns these values to the AMBULANCE type.
 */
public class Ambulance extends Unit {
    /**
     * This constructs the unit of the Ambulance type
     * @param type This value is an enum value from UnitType enum
     * @param status This value is an enum value from UnitStatus enum
     */
    public  Ambulance(UnitType type, UnitStatus status) {
        this.unitID = ++numberOfUnits;
        this.type = UnitType.AMBULANCE;
        this.status = UnitStatus.IDLE;
    }
}
