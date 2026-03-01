package cityrescue;

import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;

public class FireEngine extends Unit {
    public FireEngine(UnitType type, UnitStatus status){
        this.unitID = ++numberofUnits;
        this.type = UnitType.FIRE_ENGINE;
        this.status = UnitStatus.IDLE;
    }
}
