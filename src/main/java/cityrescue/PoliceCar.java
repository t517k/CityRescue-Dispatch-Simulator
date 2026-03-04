package cityrescue;

import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;

/**
 * PoliceCar
 * This class extends the unit class and is specialised at dealing with incidents of the CRIME type.
 * All attributes are inherited from Unit and the constructor assigns these values to the POLICE_CAR type.
 */
public class PoliceCar extends Unit {
    /**
     * This constructs the unit of the PoliceCar type
     * @param type This value is an enum value from UnitType enum
     * @param status This value is an enum value from UnitStatus enum
     */
    public PoliceCar(UnitType type, UnitStatus status){
        this.unitID = ++numberOfUnits;
        this.type = UnitType.POLICE_CAR;
        this.status = UnitStatus.IDLE;
    }
}
