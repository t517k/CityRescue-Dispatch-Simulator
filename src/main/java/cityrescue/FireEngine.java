package cityrescue;

import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;

/**
 * FireEngine
 * This class extends the unit class and is specialised at dealing with incidents of the FIRE type.
 * All attributes are inherited from Unit and the constructor assigns these values to the FIRE_ENGINE type.
 */
public class FireEngine extends Unit {
    /**
     * This constructs the unit of the FireEngine type
     * @param type An enum value from UnitType enum
     * @param status An enum value from UnitStatus enum
     */
    public FireEngine(UnitType type, UnitStatus status) {
        this.unitID = ++numberOfUnits;
        this.type = UnitType.FIRE_ENGINE;
        this.status = UnitStatus.IDLE;
    }
}
