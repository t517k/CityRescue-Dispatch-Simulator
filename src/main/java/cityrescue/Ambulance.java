package cityrescue;

import cityrescue.enums.UnitType;
import cityrescue.enums.UnitStatus;

public class Ambulance extends Unit{
    public  Ambulance(UnitType type, UnitStatus status){
        this.unitID = ++numberOfUnits;
        this.type = UnitType.AMBULANCE;
        this.status = UnitStatus.IDLE;
    }
}
