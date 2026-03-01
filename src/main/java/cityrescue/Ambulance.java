package cityrescue;

import cityrescue.enums.UnitType;
import cityrescue.enums.UnitStatus;

public class Ambulance extends Unit{
    public  Ambulance(UnitType type, UnitStatus status){
        this.UnitId = ++UnitId;
        this.type = UnitType.AMBULANCE;
        this.status = UnitStatus.IDLE;
    }
}
