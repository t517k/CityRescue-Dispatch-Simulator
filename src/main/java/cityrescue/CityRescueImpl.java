package cityrescue;

import java.util.ArrayList;
import cityrescue.enums.*;
import cityrescue.exceptions.*;

/**
 * The main class for the dispatch application and contains all the corresponding methods within it
 */

public class CityRescueImpl implements CityRescue {

    final int MAX_STATIONS = 20;
    final int MAX_UNITS = 50;
    final int MAX_INCIDENTS = 200;
    ArrayList<Unit> unitObjArray = new ArrayList<>();
    ArrayList<Station> stationObjArray = new ArrayList<>();
    ArrayList<Incident> incidentObjArray = new ArrayList<>();
    int obstacleNum = 0;
    CityMap map;
    int tick;

    /**
     * Resets the simulation by creating a new grid with the specified dimensions, clearing all units, stations, incidents, and obstacles, resetting counters and setting the tick back to zero
     * @param width
     * @param height
     * @throws InvalidGridException
     */
    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        tick = 0;
        map = new CityMap(width, height);
        map.initialiseGrid();
        obstacleNum = 0;
        unitObjArray.clear();
        stationObjArray.clear();
        incidentObjArray.clear();
        Station.numOfStations = 0;
        Incident.numOfIncidents = 0;
        Unit.numberOfUnits = 0;
    }

    /**
     * Returns the size of the current grid
     * @return
     */
    @Override
    public int[] getGridSize() {
        return map.getSize();
    }

    /**
     * Adds an obstacle at a specific x and y coordinate on the grid
     * @param x
     * @param y
     * @throws InvalidLocationException
     */
    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        int xWidth = getGridSize()[0];
        int yHeight = getGridSize()[1];
        if (x<0 || x>=xWidth || y<0 || y>=yHeight) {
            throw new InvalidLocationException("Obstacle cannot be added as location is out of bounds");
        } else {
            Obstacle newObstacle = new Obstacle(x, y);
            map.addObstacle(x, y, newObstacle);
            ++obstacleNum;
        }
    }

    /**
     * Removes an existing obstacle at a specific x and y coordinate on the grid
     * @param x
     * @param y
     * @throws InvalidLocationException
     */
    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        int xWidth = getGridSize()[0];
        int yHeight = getGridSize()[1];
        if (x<0 || x>=xWidth || y<0 || y>=yHeight) {
            throw new InvalidLocationException("Obstacle cannot be removed as location is out of bounds");
        } else {
            map.removeObstacle(x, y);
            --obstacleNum;
        }
    }

    /**
     * Adds a station at a specific x and y coordinate on the grid
     * @param name
     * @param x
     * @param y
     * @return
     * @throws InvalidNameException
     * @throws InvalidLocationException
     * @throws CapacityExceededException
     */
    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException, CapacityExceededException {
        int xWidth = getGridSize()[0];
        int yHeight = getGridSize()[1];
        if (name.isEmpty()) {
            throw new InvalidNameException("Station name is blank");
        } else if (x<0 || x>=xWidth || y<0 || y>=yHeight) {
            throw new InvalidLocationException("Station cannot be added as location is out of bounds");
        } else if (map.isBlocked(x, y)) {
            throw new InvalidLocationException("Station cannot be added as location is blocked");
        } else if (stationObjArray.size() == MAX_STATIONS) {
            throw new CapacityExceededException("Station cannot be added as max number of stations has been reached");
        } else {
            Station newstation = new Station(x, y, name);
            stationObjArray.add(newstation);
            return newstation.stationID;
        }
    }

    /**
     * Removes an existing station at a specific x and y coordinate on the grid
     * @param stationId
     * @throws IDNotRecognisedException
     * @throws IllegalStateException
     */
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

    /**
     * Sets the maximum number of units a Station can contain to an integer
     * @param stationId
     * @param maxUnits
     * @throws IDNotRecognisedException
     * @throws InvalidCapacityException
     */
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

    /**
     * Returns an array of the Station IDs of all existing stations
     * @return
     */
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

    /**
     * Adds a new unit to an existing station if there is enough capacity
     * @param stationId
     * @param type
     * @return
     * @throws IDNotRecognisedException
     * @throws InvalidUnitException
     * @throws IllegalStateException
     * @throws CapacityExceededException
     */
    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException, CapacityExceededException {
        if (type == null) {
            throw new InvalidUnitException("Unit type can't be null");
        }
        if (unitObjArray.size() == MAX_UNITS) {
            throw new CapacityExceededException("Unit cannot be added as max number of units has been reached");
        }
        Station targetStation = null;
        for (Station s: stationObjArray) {
            if (s.stationID == stationId) {
                targetStation = s;
                break;
            }
        }
        if (targetStation == null) {
            throw new IDNotRecognisedException("Station ID doesn't exist");
        }
        if (targetStation.stationedUnits.size() >= targetStation.maxUnits) {
            throw new IllegalStateException("Station is at maximum capacity");
        }
        Unit newUnit;
        switch (type) {
            case FIRE_ENGINE:
                newUnit = new FireEngine(UnitType.FIRE_ENGINE, UnitStatus.IDLE);
                break;
            case AMBULANCE:
                newUnit = new Ambulance(UnitType.AMBULANCE, UnitStatus.IDLE);
                break;
            case POLICE_CAR:
                newUnit = new PoliceCar(UnitType.POLICE_CAR, UnitStatus.IDLE);
                break;
            default:
                throw new InvalidUnitException("Invalid Unit Type");
        }
        newUnit.homeStation = targetStation;
        newUnit.xCoord = targetStation.xCoord;
        newUnit.yCoord = targetStation.yCoord;

        targetStation.stationedUnits.add(newUnit);
        unitObjArray.add(newUnit);

        return newUnit.unitID;
    }

    /**
     * Removes the unit with the specified ID from the system if it exists and is not currently en route or at the scene
     * @param unitId
     * @throws IDNotRecognisedException
     * @throws IllegalStateException
     */
     @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        for (Unit currentUnit : unitObjArray) {
            if (currentUnit.unitID == unitId) {
                if (currentUnit.status == UnitStatus.EN_ROUTE || currentUnit.status == UnitStatus.AT_SCENE) {
                    throw new IllegalStateException("Unit cannot be decommissioned as current state does does not allow it");
                } else {
                    unitObjArray.remove(currentUnit);
                    return;
                }
            }
        }
        throw new IDNotRecognisedException("Unit cannot be decommissioned as ID cannot be found");
    }

    /**
     * Transfers an idle unit to a specified station, if both the unit and station exist and the station has available capacity
     * @param unitId
     * @param newStationId
     * @throws IDNotRecognisedException
     * @throws IllegalStateException
     */
    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        Station newStation = null;
        for (Station currentStation : stationObjArray) {
            if (currentStation.stationID == newStationId) {
                newStation = currentStation;
            }
        }
        if (newStation == null) {
            throw new IDNotRecognisedException("New station ID not found");
        }
        for (Unit currentUnit : unitObjArray) {
            if (currentUnit.unitID == unitId) {
                if (currentUnit.status == UnitStatus.IDLE) {
                    if (newStation.stationedUnits.size() >= newStation.maxUnits){
                        throw new CapacityExceededException("Unit cannot be transferred as new station capacity is at maximum");
                    } else {
                        currentUnit.homeStation.stationedUnits.remove(currentUnit);
                        currentUnit.homeStation = newStation;
                        currentUnit.homeStation.stationedUnits.add(currentUnit);
                        currentUnit.xCoord = newStation.xCoord;
                        currentUnit.yCoord = newStation.yCoord;
                        return;
                    }
                } else {
                    throw new IllegalStateException("Unit cannot be transferred as current state does not allow it");
                }
            }
        }
        throw new IDNotRecognisedException("Unit cannot be transferred as unit ID cannot be found");
    }

    /**
     * Sets a unit’s status to out of service or back to idle if the unit exists and is in a valid state for the change
     * @param unitId
     * @param outOfService
     * @throws IDNotRecognisedException
     * @throws IllegalStateException
     */
     @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        for (Unit currentUnit : unitObjArray) {
            if (currentUnit.unitID == unitId) {
                if (outOfService) {
                    if (currentUnit.status == UnitStatus.IDLE) {
                        currentUnit.status = UnitStatus.OUT_OF_SERVICE;
                        return;
                    } else {
                        throw new IllegalStateException("Cannot set out_of_service as current state is illegal");
                    }
                } else {
                    if (currentUnit.status == UnitStatus.OUT_OF_SERVICE) {
                        currentUnit.status = UnitStatus.IDLE;
                        return;
                    } else {
                        throw new IllegalStateException("Cannot set idle as current state is illegal");
                    }
                }
            }
        }
        throw new IDNotRecognisedException("Unit could not be set to out_of_service as ID has not been found");
    }

    /**
     * Returns an array of the Unit IDs of all existing Units
     * @return
     */
    @Override
    public int[] getUnitIds() {
        ArrayList<Integer> idArrayList = new ArrayList<>();
        for(Unit currentUnit : unitObjArray) {
            idArrayList.add(currentUnit.unitID);
        }
        int[] arr = new int[idArrayList.size()];
        for (int i = 0; i < idArrayList.size(); i++) {
            arr[i] = idArrayList.get(i);
        }
        return arr;
    }

    /**
     * Returns a formatted string containing the specified unit’s details including type, home station, location, status, and incident information
     * @param unitId
     * @return
     * @throws IDNotRecognisedException
     */
    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        for(Unit currentUnit : unitObjArray) {
            if (currentUnit.unitID == unitId) {
                if (currentUnit.currentIncident == null) {
                    String unitOutput = "U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%s WORK=%s";
                    return String.format(unitOutput, unitId, currentUnit.type, currentUnit.homeStation.stationID,
                            currentUnit.xCoord, currentUnit.yCoord, currentUnit.status,
                            "-", "-");
                } else {
                    String unitOutput = "U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%d WORK=%d";
                    return String.format(unitOutput, unitId, currentUnit.type, currentUnit.homeStation.stationID,
                            currentUnit.xCoord, currentUnit.yCoord, currentUnit.status,
                            currentUnit.currentIncident.incidentID, currentUnit.currentIncident.ticksLeftToResolve);
                }

            }
        }
        throw new IDNotRecognisedException("Unit cannot be viewed as ID has not been found");
    }

    /**
     * Validates the incident details and creates a new incident at the specified location and returns its ID
     * @param type
     * @param severity
     * @param x
     * @param y
     * @return
     * @throws InvalidSeverityException
     * @throws InvalidLocationException
     * @throws CapacityExceededException
     */
     @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException, CapacityExceededException {
        int xWidth = getGridSize()[0];
        int yHeight = getGridSize()[1];
        if (type == null) {
            throw new IllegalAccessError("Incident cannot be reported as type is null");
        } else if (x<0 || x>=xWidth || y<0 || y>=yHeight) {
            throw new InvalidLocationException("Incident cannot be reported as location is out of bounds");
        } else if (severity<1 || severity>5){
            throw new InvalidSeverityException("Incident cannot be reported as severity is invalid");
        } else if (map.isBlocked(x, y)) {
            throw new InvalidLocationException("Incident cannot be reported as location is blocked");
        } else if (incidentObjArray.size() == MAX_INCIDENTS) {
            throw new CapacityExceededException("Incident cannot be reported as max number of incidents has been reached");
        } else {
            Incident newIncident = new Incident(IncidentStatus.REPORTED, type, severity, x, y);
            incidentObjArray.add(newIncident);
            return newIncident.incidentID;
        }
    }

    /**
     * Cancels the specified incident if it exists and is in a cancellable state, and resets any dispatched unit to idle
     * @param incidentId
     * @throws IDNotRecognisedException
     * @throws IllegalStateException
     */
    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        for(Incident currentIncident : incidentObjArray) {
            if (currentIncident.incidentID == incidentId) {
                if (currentIncident.status == IncidentStatus.REPORTED) {
                    currentIncident.status = IncidentStatus.CANCELLED;
                    return;
                } else if (currentIncident.status == IncidentStatus.DISPATCHED){
                    currentIncident.status = IncidentStatus.CANCELLED;
                    currentIncident.dispatchedUnit.status = UnitStatus.IDLE;
                    currentIncident.dispatchedUnit = null;
                    return;
                } else {
                    throw new IllegalStateException("Incident cannot be cancelled as at current state it cannot be cancelled");
                }
            }
        }
        throw new IDNotRecognisedException("Incident cannot be cancelled as ID has not been found");
    }

    /**
     * Updates the severity of the specified incident if it exists, the new severity is valid, and the incident has not been cancelled or resolved
     * @param incidentId
     * @param newSeverity
     * @throws IDNotRecognisedException
     * @throws InvalidSeverityException
     * @throws IllegalStateException
     */
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

    /**
     * Returns an array of the Incident IDs of all existing Incidents
     * @return
     */
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

    /**
     * Returns a formatted string containing the specified incident’s details including type, severity, location, status, and dispatched unit
     * @param incidentId
     * @return
     * @throws IDNotRecognisedException
     */
    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        for(Incident currentIncident : incidentObjArray) {
            if (currentIncident.incidentID == incidentId) {
                if (currentIncident.dispatchedUnit == null) {
                    String incidentOutput = "I#%d TYPE=%s SEV=%d LOC=(%d,%d) STATUS=%s UNIT=%s";
                    return String.format(incidentOutput, incidentId, currentIncident.type, currentIncident.severity,
                            currentIncident.xCoord, currentIncident.yCoord, currentIncident.status,
                            "-");
                } else {
                    String incidentOutput = "I#%d TYPE=%s SEV=%d LOC=(%d,%d) STATUS=%s UNIT=%d";
                    return String.format(incidentOutput, incidentId, currentIncident.type, currentIncident.severity,
                            currentIncident.xCoord, currentIncident.yCoord, currentIncident.status,
                            currentIncident.dispatchedUnit.unitID);
                }
            }
        }
        throw new IDNotRecognisedException("Incident cannot be viewed as ID has not been found");
    }

    /**
     * Dispatches the closest available unit of the appropriate type to each reported incident by selecting the idle unit with the smallest Manhattan distance, and then updates both the unit and incident statuses
     */
    @Override
    public void dispatch() {
        for (Incident currentIncident : incidentObjArray) {
            if (currentIncident.status == IncidentStatus.REPORTED) {
                switch (currentIncident.type) {
                    case FIRE:
                        ArrayList<Unit> fireEngines = new ArrayList<>();
                        for (Unit currentUnit : unitObjArray) {
                            if (currentUnit.status == UnitStatus.IDLE && currentUnit.type == UnitType.FIRE_ENGINE) {
                                fireEngines.add(currentUnit);
                            }
                        }
                        Unit minUnitF = fireEngines.get(0);
                        for (Unit unit : fireEngines) {
                            unit.setManhattanDistance(currentIncident.xCoord, currentIncident.yCoord);
                        }
                        for (Unit unit : fireEngines) {
                            if (unit.manhattanDistance < minUnitF.manhattanDistance) {
                                minUnitF = unit;
                            }
                        }
                        ArrayList<Unit> equalF = new ArrayList<>();
                        for (Unit unit : fireEngines) {
                            if (unit.manhattanDistance == minUnitF.manhattanDistance) {
                                equalF.add(unit);
                            }
                        }
                        if (equalF.size() > 1) {
                            Unit minUnitID = equalF.get(0);
                            for (Unit unit : equalF) {
                                if (unit.unitID < minUnitID.unitID) {
                                    minUnitID = unit;
                                }
                            }
                            minUnitID.status = UnitStatus.EN_ROUTE;
                            currentIncident.status = IncidentStatus.DISPATCHED;
                            currentIncident.dispatchedUnit = minUnitID;
                            minUnitID.currentIncident = currentIncident;
                        } else {
                            minUnitF.status = UnitStatus.EN_ROUTE;
                            currentIncident.status = IncidentStatus.DISPATCHED;
                            currentIncident.dispatchedUnit = minUnitF;
                            minUnitF.currentIncident = currentIncident;
                        }
                        break;
                    case MEDICAL:
                        ArrayList<Unit> ambulances = new ArrayList<>();
                        for (Unit currentUnit : unitObjArray) {
                            if (currentUnit.status == UnitStatus.IDLE && currentUnit.type == UnitType.AMBULANCE) {
                                ambulances.add(currentUnit);
                            }
                        }
                        Unit minUnitA = ambulances.get(0);
                        for (Unit unit : ambulances) {
                            unit.setManhattanDistance(currentIncident.xCoord, currentIncident.yCoord);
                        }
                        for (Unit unit : ambulances) {
                            if (unit.manhattanDistance < minUnitA.manhattanDistance) {
                                minUnitA = unit;
                            }
                        }
                        ArrayList<Unit> equalA = new ArrayList<>();
                        for (Unit unit : ambulances) {
                            if (unit.manhattanDistance == minUnitA.manhattanDistance) {
                                equalA.add(unit);
                            }
                        }
                        if (equalA.size() > 1) {
                            Unit minUnitIDA = equalA.get(0);
                            for (Unit unit : equalA) {
                                if (unit.unitID < minUnitIDA.unitID) {
                                    minUnitIDA = unit;
                                }
                            }
                            minUnitIDA.status = UnitStatus.EN_ROUTE;
                            currentIncident.status = IncidentStatus.DISPATCHED;
                            currentIncident.dispatchedUnit = minUnitIDA;
                            minUnitIDA.currentIncident = currentIncident;
                        } else {
                            minUnitA.status = UnitStatus.EN_ROUTE;
                            currentIncident.status = IncidentStatus.DISPATCHED;
                            currentIncident.dispatchedUnit = minUnitA;
                            minUnitA.currentIncident = currentIncident;
                        }
                        break;
                    case CRIME:
                        ArrayList<Unit> policeCars = new ArrayList<>();
                        for (Unit currentUnit : unitObjArray) {
                            if (currentUnit.status == UnitStatus.IDLE && currentUnit.type == UnitType.POLICE_CAR) {
                                policeCars.add(currentUnit);
                            }
                        }
                        Unit minUnitC = policeCars.get(0);
                        for (Unit unit : policeCars) {
                            unit.setManhattanDistance(currentIncident.xCoord, currentIncident.yCoord);
                        }
                        for (Unit unit : policeCars) {
                            if (unit.manhattanDistance < minUnitC.manhattanDistance) {
                                minUnitC = unit;
                            }
                        }
                        ArrayList<Unit> equalC = new ArrayList<>();
                        for (Unit unit : policeCars) {
                            if (unit.manhattanDistance == minUnitC.manhattanDistance) {
                                equalC.add(unit);
                            }
                        }
                        if (equalC.size() > 1) {
                            Unit minUnitIDC = equalC.get(0);
                            for (Unit unit : equalC) {
                                if (unit.unitID < minUnitIDC.unitID) {
                                    minUnitIDC = unit;
                                }
                            }
                            minUnitIDC.status = UnitStatus.EN_ROUTE;
                            currentIncident.status = IncidentStatus.DISPATCHED;
                            currentIncident.dispatchedUnit = minUnitIDC;
                            minUnitIDC.currentIncident = currentIncident;
                        } else {
                            minUnitC.status = UnitStatus.EN_ROUTE;
                            currentIncident.status = IncidentStatus.DISPATCHED;
                            currentIncident.dispatchedUnit = minUnitC;
                            minUnitC.currentIncident = currentIncident;
                        }
                        break;
                }
            }
        }
    }

    /**
     * Checks whether the specified coordinates are within the grid boundaries and not blocked on the map
     * @param x
     * @param y
     * @return
     */
    private boolean isLegalMove(int x, int y) {

        int width = getGridSize()[0];
        int height = getGridSize()[1];

        if (x < 0 || x >= width || y < 0 || y >= height)
            return false;

        return !map.isBlocked(x, y);
    }

    /**
     * Advances the simulation by one tick by moving en-route units toward their incidents, transitioning units to at-scene when they arrive, resolving incidents based on the unit type, and resetting resolved units back to idle state
     */
    @Override
    public void tick() {
        tick++;
        unitObjArray.stream()
                .filter(u -> u.status == UnitStatus.EN_ROUTE)
                .sorted((a, b) -> Integer.compare(a.unitID, b.unitID))
                .forEach(u -> {

                    Incident inc = u.currentIncident;
                    if (inc == null) return;

                    int[][] directions = {
                            {0, -1},
                            {1, 0},
                            {0, 1},
                            {-1, 0}
                    };

                    int currentDistance =
                            Math.abs(u.xCoord - inc.xCoord) +
                                    Math.abs(u.yCoord - inc.yCoord);

                    boolean moved = false;
                    for (int[] d : directions) {

                        int newX = u.xCoord + d[0];
                        int newY = u.yCoord + d[1];

                        if (isLegalMove(newX, newY)) {

                            int newDistance =
                                    Math.abs(newX - inc.xCoord) +
                                            Math.abs(newY - inc.yCoord);

                            if (newDistance < currentDistance) {
                                u.xCoord = newX;
                                u.yCoord = newY;
                                moved = true;
                                break;
                            }
                        }
                    }
                    if (!moved) {
                        for (int[] d : directions) {

                            int newX = u.xCoord + d[0];
                            int newY = u.yCoord + d[1];

                            if (isLegalMove(newX, newY)) {
                                u.xCoord = newX;
                                u.yCoord = newY;
                                break;
                            }
                        }
                    }
                });
        for (Unit u : unitObjArray) {

            if (u.status == UnitStatus.EN_ROUTE &&
                    u.currentIncident != null &&
                    u.xCoord == u.currentIncident.xCoord &&
                    u.yCoord == u.currentIncident.yCoord) {

                u.status = UnitStatus.AT_SCENE;
                u.currentIncident.status = IncidentStatus.IN_PROGRESS;

                // Set work duration based on unit type
                switch (u.type) {
                    case AMBULANCE:
                        u.currentIncident.ticksLeftToResolve = 2;
                        break;
                    case POLICE_CAR:
                        u.currentIncident.ticksLeftToResolve = 3;
                        break;
                    case FIRE_ENGINE:
                        u.currentIncident.ticksLeftToResolve = 4;
                        break;
                }
            }
        }
        for (Unit u : unitObjArray) {

            if (u.status == UnitStatus.AT_SCENE &&
                    u.currentIncident != null &&
                    u.currentIncident.status == IncidentStatus.IN_PROGRESS) {

                u.currentIncident.ticksLeftToResolve--;

                if (u.currentIncident.ticksLeftToResolve == 0) {
                    u.currentIncident.status = IncidentStatus.RESOLVED;
                }
            }
        }
        incidentObjArray.stream()
                .sorted((a, b) -> Integer.compare(a.incidentID, b.incidentID))
                .filter(i -> i.status == IncidentStatus.RESOLVED)
                .forEach(i -> {

                    if (i.dispatchedUnit != null) {
                        i.dispatchedUnit.status = UnitStatus.IDLE;
                        i.dispatchedUnit.currentIncident = null;
                    }

                    i.dispatchedUnit = null;
                });
    }

    /**
     * Returns a formatted summary of the current simulation state, including the tick count, number of stations, units, incidents, and obstacles, and information about each incident and unit
     * @return
     */
    @Override
    public String getStatus() {
        String statusString = "TICK=%d\nSTATIONS=%d UNITS=%d INCIDENTS=%d OBSTACLES=%d\nINCIDENTS";
        String formattedStatus = String.format(statusString, tick, stationObjArray.size(), unitObjArray.size(),
                incidentObjArray.size(), obstacleNum);
        StringBuilder formattedStatusVar = new StringBuilder(formattedStatus);
        for (Incident currentIncident : incidentObjArray) {
            try {
                formattedStatusVar.append(viewIncident(currentIncident.incidentID));
            } catch (IDNotRecognisedException e) {
                throw new RuntimeException(e);
            }
        }
        formattedStatusVar.append("\nUNITS");
        for (Unit currentUnit : unitObjArray) {
            try {
                formattedStatusVar.append(viewUnit(currentUnit.unitID));
            } catch (IDNotRecognisedException e) {
                throw new RuntimeException(e);
            }
        }
        return formattedStatusVar.toString();
    }
}
