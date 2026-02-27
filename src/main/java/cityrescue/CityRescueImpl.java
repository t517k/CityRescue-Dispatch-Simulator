package cityrescue;

import java.util.ArrayList;
import cityrescue.enums.*;
import cityrescue.exceptions.*;
import com.sun.jdi.ObjectCollectedException;

/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {

    // TODO: add fields (map, arrays for stations/units/incidents, counters, tick, etc.)

    final int MAX_STATIONS = 20;
    final int MAX_UNITS = 50;
    final int MAX_INCIDENTS = 200;
    ArrayList<Unit> unitObjArray = new ArrayList<>();
    ArrayList<Station> stationObjArray = new ArrayList<>();
    ArrayList<Incident> incidentObjArray = new ArrayList<>();
    CityMap map;
    int tick;


    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        tick = 0;
        map = new CityMap(width, height);
        map.initialiseGrid();
    }

    @Override
    public int[] getGridSize() {
        return map.getSize();
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        int xWidth = getGridSize()[0];
        int yHeight = getGridSize()[1];
        if (x<0 || x>=xWidth || y<0 || y>=yHeight) {
            throw new InvalidLocationException("Obstacle cannot be added as location is out of bounds");
        } else {
            Obstacle newObstacle = new Obstacle(x, y);
            map.addObstacle(x, y, newObstacle);
        }
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        int xWidth = getGridSize()[0];
        int yHeight = getGridSize()[1];
        if (x<0 || x>=xWidth || y<0 || y>=yHeight) {
            throw new InvalidLocationException("Obstacle cannot be removed as location is out of bounds");
        } else {
            map.removeObstacle(x, y);
        }
    }

    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        int xWidth = getGridSize()[0];
        int yHeight = getGridSize()[1];
        if (name.isEmpty()) {
            throw new InvalidNameException("Station name is blank");
        } else if (x<0 || x>=xWidth || y<0 || y>=yHeight) {
            throw new InvalidLocationException("Station cannot be added as location is out of bounds");
        } else {
            Station newstation = new Station(x, y, name);
            stationObjArray.add(newstation);
            return newstation.stationID;
        }
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        for(Station currentStation : stationObjArray) {
            if (currentStation.stationID == stationId) {
                if (!currentStation.stationedUnits.isEmpty()) {
                    throw new IllegalStateException("Station cannot be removed as it still contains units");
                } else {
                    stationObjArray.remove(currentStation);
                }
            } else {
                throw new IDNotRecognisedException("Station cannot be removed as ID has not been found");
            }
        }
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getStationIds() {

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        System.out.println("TICK="+tick);
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
