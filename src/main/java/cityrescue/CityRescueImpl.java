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
        } else if (map.isBlocked(x, y)) {
            throw new InvalidLocationException("Station cannot be added as location is blocked");
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
                    return;
                }
            }
        }
        throw new IDNotRecognisedException("Station capacity cannot be set as ID has not been found");
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        for(Station currentStation : stationObjArray) {
            if (currentStation.stationID == stationId) {
                if (maxUnits<=0 || maxUnits<=currentStation.stationedUnits.size()) {
                    throw new InvalidCapacityException("Station capacity cannot be set as input is not valid");
                } else {
                    currentStation.maxUnits = maxUnits;
                    return;
                }
            }
        }
        throw new IDNotRecognisedException("Station capacity cannot be set as ID has not been found");
    }

    @Override
    public int[] getStationIds() {
        ArrayList<Integer> idArrayList = new ArrayList<>();
        for(Station currentStation : stationObjArray) {
            idArrayList.add(currentStation.stationID);
        }
        int[] arr = new int[idArrayList.size()];
        for (int i = 0; i < idArrayList.size(); i++) {
            arr[i] = idArrayList.get(i);
        }
        return arr;
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        ArrayList<Integer> unitObjArray = new ArrayList<>();
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
        for (Unit currentUnit : unitObjArray) {
            if (currentUnit.unitID == unitId) {
                if (currentUnit.status == UnitStatus.IDLE) {

                } else {
                    throw new IllegalStateException("Unit could not be set to out_of_service as current state does not allow it")
                }
            }
        }
        throw new IDNotRecognisedException("Unit could not be set to out_of_service as ID has not been found");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        for(Unit currentUnit : unitObjArray) {
            if (currentUnit.unitID == unitId) {
                String unitOutput = "U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%d WORK=%d";
                return String.format(unitOutput, unitId, currentUnit.type, currentUnit.homeStation,
                        currentUnit.xCoord, currentUnit.yCoord, currentUnit.status,
                        currentUnit.currentIncident.incidentID, currentUnit.currentIncident.ticksLeftToResolve); // TODO find out what WORK means
            }
        }
        throw new IDNotRecognisedException("Incident cannot be cancelled as ID has not been found");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        int xWidth = getGridSize()[0];
        int yHeight = getGridSize()[1];
        if (type == null) {
            throw new IllegalAccessError("Incident cannot be reported as type is null");
        } else if (x<0 || x>=xWidth || y<0 || y>=yHeight) {
            throw new InvalidLocationException("Incident cannot be reported as location is out of bounds");
        } else if (severity<1 || severity>5){
            throw new InvalidSeverityException("Incident cannot be added as severity is invalid");
        } else if (map.isBlocked(x, y)){
            throw new InvalidLocationException("Incident cannot be reported as location is blocked");
        } else {
            Incident newIncident = new Incident(IncidentStatus.REPORTED, type, severity, x, y);
            incidentObjArray.add(newIncident);
            return newIncident.incidentID;
        }
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        for(Incident currentIncident : incidentObjArray) {
            if (currentIncident.incidentID == incidentId) {
                if (currentIncident.status == IncidentStatus.REPORTED) {
                    currentIncident.status = IncidentStatus.CANCELLED;
                    return;
                } else if (currentIncident.status == IncidentStatus.DISPATCHED){
                    currentIncident.status = IncidentStatus.CANCELLED;
                    // TODO add code to release units when more unit code has been created
                    return;
                } else {
                    throw new IllegalStateException("Incident cannot be cancelled as at current state it cannot be cancelled");
                }
            }
        }
        throw new IDNotRecognisedException("Incident cannot be cancelled as ID has not been found");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        for(Incident currentIncident : incidentObjArray) {
            if (currentIncident.incidentID == incidentId) {
                if (newSeverity<1 || newSeverity>5) {
                    throw new InvalidSeverityException("Incident cannot be escalated as severity is invalid");
                } else if (currentIncident.status == IncidentStatus.CANCELLED || currentIncident.status == IncidentStatus.RESOLVED) {
                    throw new IllegalStateException("Incident cannot be escalated as at current state it cannot be escalated");
                } else {
                    currentIncident.severity = newSeverity;
                    return;
                }
            }
        }
        throw new IDNotRecognisedException("Incident cannot be cancelled as ID has not been found");
    }

    @Override
    public int[] getIncidentIds() {
        ArrayList<Integer> idArrayList = new ArrayList<>();
        for(Incident currentIncident : incidentObjArray) {
            idArrayList.add(currentIncident.incidentID);
        }
        int[] arr = new int[idArrayList.size()];
        for (int i = 0; i < idArrayList.size(); i++) {
            arr[i] = idArrayList.get(i);
        }
        return arr;
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        for(Incident currentIncident : incidentObjArray) {
            if (currentIncident.incidentID == incidentId) {
                String incidentOutput = "I#%d TYPE=%s SEV=%d LOC=(%d,%d) STATUS=%s UNIT=%d";
                return String.format(incidentOutput, incidentId, currentIncident.type, currentIncident.severity,
                        currentIncident.xCoord, currentIncident.yCoord, currentIncident.status,
                        currentIncident.dispatchedUnit.unitID);
            }
        }
        throw new IDNotRecognisedException("Incident cannot be cancelled as ID has not been found");
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
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
