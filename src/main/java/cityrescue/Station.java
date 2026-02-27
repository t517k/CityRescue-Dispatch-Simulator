package cityrescue;

public class Station {
    String stationName;
    int xCoord;
    int yCoord;
    int[] stationedUnits;
    int stationID;
    static int numOfStations = 0;

    public Station(int xCoord, int yCoord, String stationName) {
        this.stationID = ++numOfStations;
        this.stationName = stationName;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }



    // Methods...
    // add unit(s)
    // dispatch unit(s)
    // how many units are in the station
}
