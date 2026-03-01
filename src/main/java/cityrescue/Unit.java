package cityrescue;
import  cityrescue.enums.*;

import java.util.ArrayList;

public abstract class Unit {
    static int UnitId = 0;
    UnitType type;
    UnitStatus status;


    // Attributes...
    public int[] getUnitId(){
        ArrayList<Integer> idArrayList = new ArrayList<>();
        for(Unit UnitId : unitObjArray) {
            idArrayList.add(currentStation.stationID);
        }
        int[] arr = new int[idArrayList.size()];
        for (int i = 0; i < idArrayList.size(); i++) {
            arr[i] = idArrayList.get(i);
        }
        return arr;
    }

    // status
    // ID
    // type

    // Methods...
    // fetch ID
    // change status
}
