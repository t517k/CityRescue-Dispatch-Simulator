package cityrescue;

import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;

public class PoliceCar extends Unit {
    public PoliceCar(UnitType type, UnitStatus status){
        this.unitID = ++numberofUnits;
        this.type = UnitType.POLICE_CAR;
        this.status = UnitStatus.IDLE;
    }
}
