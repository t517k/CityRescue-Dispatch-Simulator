package cityrescue;

public class Station {
    String stationName;
    int[] stationGridRef = new int[2];
    int[] stationedUnits;
    int stationID;
    int maxUnits;

    public Station(int[] stationedUnits, int[] stationGridRef, int stationID, String stationName) {
        this.stationGridRef = stationGridRef;
        this.stationedUnits = stationedUnits;
        this.stationID = stationID;
        this.stationName = stationName;
    }


    // currently based units


    // Methods...
    // add unit(s)
    // dispatch unit(s)
    // how many units are in the station
}
