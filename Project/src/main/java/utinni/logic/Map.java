package utinni.logic;

import eu.loxon.centralcontrol.*;
import utinni.App;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Map {

    WsCoordinate mapSize;
    WsCoordinate spaceShuttlePos;
    WsCoordinate spaceShuttleExitPos;
    HashMap<WsCoordinate, Field> knownCoordinates = new HashMap<>();
    HashMap<Integer, BuilderUnitWrapper> ourUnits = new HashMap<>();
    CommonResp lastCommonResponse;
    Runnable runAfterOk;
    Runnable runAfterNOk;

    public boolean acceptCache(CommonResp commonResp) {
        lastCommonResponse = commonResp;
        System.out.println(commonResp);

        if(commonResp.getType() == ResultType.DONE) {
            if(runAfterOk != null) {
                runAfterOk.run();
            }
        }
        else if(runAfterNOk != null) {
            runAfterNOk.run();
        }
        runAfterNOk = null;
        runAfterOk = null;

        return commonResp.getType() == ResultType.DONE;
    }

    public void addInfo(StartGameResponse startGameResponse) {
        mapSize = startGameResponse.getSize();
        System.out.println("Field size is: " + mapSize.getX() + " * " + mapSize.getY());

        for(WsBuilderunit wsBuilderunit : startGameResponse.getUnits()) {
            BuilderUnitWrapper builderUnitWrapper =
                    new BuilderUnitWrapper(wsBuilderunit, getTick());
            ourUnits.put(wsBuilderunit.getUnitid(), builderUnitWrapper);
            knownCoordinates.put(wsBuilderunit.getCord(), builderUnitWrapper);

            System.out.println("My unit (id: " + wsBuilderunit.getUnitid() + ") in coord: "
                + wsBuilderunit.getCord());
        }
        lastCommonResponse = startGameResponse.getResult();
    }

    public void addInfo(RadarResponse radarResponse) {
        setScouts(radarResponse.getScout());
        lastCommonResponse = radarResponse.getResult();
    }

    public void addInfo(WatchResponse watchResponse) {
        setScouts(watchResponse.getScout());
        lastCommonResponse = watchResponse.getResult();
    }

    public void addInfo(GetSpaceShuttlePosResponse getSpaceShuttlePosResponse) {
        spaceShuttlePos = getSpaceShuttlePosResponse.getCord();

        knownCoordinates.put(spaceShuttlePos,
                new Field(spaceShuttlePos,
                        ObjectType.SHUTTLE, App.user, getTick()));

        System.out.println("My space shuttle coord: " + spaceShuttlePos);

        lastCommonResponse = getSpaceShuttlePosResponse.getResult();
    }

    public void addInfo(GetSpaceShuttleExitPosResponse getSpaceShuttleExitPosResponse) {
        spaceShuttleExitPos = getSpaceShuttleExitPosResponse.getCord();

        if(isReallyFirstTick()) {
            knownCoordinates.put(spaceShuttleExitPos,
                    new Field(spaceShuttleExitPos,
                            ObjectType.ROCK, null, getTick()));
        }

        System.out.println("My space shuttle exit coord: " + spaceShuttleExitPos);

        lastCommonResponse = getSpaceShuttleExitPosResponse.getResult();
    }

    public void addCache(StructureTunnelRequest structureTunnelRequest) {
        System.out.println(structureTunnelRequest);
        WsCoordinate nextField = Coordinating.getNextCoordinate(
                getUnit(structureTunnelRequest.getUnit()).getCoordinate(),
                structureTunnelRequest.getDirection());

        runAfterOk = () -> knownCoordinates.put(nextField,
                new Field(nextField, ObjectType.TUNNEL, App.user, getTick()));
        runAfterNOk = () -> knownCoordinates.remove(nextField);
    }

    public void addCache(ExplodeCellRequest explodeCellRequest) {
        System.out.println(explodeCellRequest);
        WsCoordinate nextField = Coordinating.getNextCoordinate(
                getUnit(explodeCellRequest.getUnit()).getCoordinate(),
                explodeCellRequest.getDirection());

        runAfterOk = () -> knownCoordinates.put(nextField,
                new Field(nextField, ObjectType.ROCK, null, getTick()));
        runAfterNOk = () -> knownCoordinates.remove(nextField);
    }

    public void addCache(MoveBuilderUnitRequest moveBuilderUnitRequest) {
        System.out.println(moveBuilderUnitRequest);
        BuilderUnitWrapper unit = (BuilderUnitWrapper) getUnit(moveBuilderUnitRequest.getUnit());
        WsCoordinate nextField = Coordinating.getNextCoordinate(
                unit.getCoordinate(),
                moveBuilderUnitRequest.getDirection());

        runAfterOk = () -> {
            if(!unit.getCoordinate().equals(spaceShuttlePos)) {
                knownCoordinates.put(unit.getCoordinate(), new Field(unit.getCoordinate(),
                        ObjectType.TUNNEL, App.user, getTick()));
            }
            unit.setWhen(getTick());
            unit.getScouting().setCord(nextField);
            knownCoordinates.put(nextField, unit);
        };
        runAfterNOk = () -> knownCoordinates.remove(nextField);
    }

    private void setScouts(List<Scouting> scouts) {
        for(Scouting scouting : scouts) {
            knownCoordinates.put(scouting.getCord(),
                    new Field(scouting, getTick()));
        }
    }

    public Field getUnit(int unit) {
        return ourUnits.get(unit);
    }
    public Field getField(WsCoordinate wsCoordinate) {
        return knownCoordinates.get(wsCoordinate);
    }
    public Field getField(int x, int y) {
        WsCoordinate wsCoordinate = new WsCoordinate();
        wsCoordinate.setX(x);
        wsCoordinate.setY(y);
        return getField(wsCoordinate);
    }

    public Field getFieldFromUnit(int unitId, WsDirection wsDirection) {
        return getField(Coordinating.getNextCoordinate(
                getUnit(unitId).getCoordinate(), wsDirection));
    }

    public boolean isUnitOnShuttle(int unit) {
        return getUnit(unit).getCoordinate().equals(spaceShuttlePos);
    }

    public boolean isReallyFirstTick() {
        for(Integer unitId : getMyUnitIds()) {
            if(!isUnitOnShuttle(unitId)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasUnitOnShuttle() {
        for(Integer unitId : getMyUnitIds()) {
            if(isUnitOnShuttle(unitId)) {
                return true;
            }
        }
        return false;
    }

    public WsDirection getDirectionFromShuttle() {
        return Coordinating.getDirection(spaceShuttlePos, spaceShuttleExitPos);
    }
    public Field getSpaceShuttleExitField() {
        return knownCoordinates.get(spaceShuttleExitPos);
    }
    Set<Integer> getMyUnitIds() {
        return new TreeSet<>(ourUnits.keySet());
    }

    private List<WsDirection> getDirectionsWithCondition(WsCoordinate wsCoordinate, Predicate<Field> predicate) {
        List<WsDirection> result = new ArrayList<>();
        for(WsDirection wsDirection : WsDirection.values()) {
            WsCoordinate nextCoordinate = Coordinating.getNextCoordinate(wsCoordinate, wsDirection);
            if(predicate.test(knownCoordinates.get(nextCoordinate))) {
                result.add(wsDirection);
            }
        }

        return result;
    }

    public List<WsDirection> getKnownSteppableDirections(WsCoordinate wsCoordinate) {
        return getDirectionsWithCondition(wsCoordinate, (Field field) -> field != null && field.isSteppable());
    }

    public List<WsDirection> getKnownTunnelableDirections(WsCoordinate wsCoordinate) {
        return getDirectionsWithCondition(wsCoordinate, (Field field) -> field != null && field.isTunnelable());
    }

    public List<WsDirection> getKnownExplodableDirections(WsCoordinate wsCoordinate) {
        return getDirectionsWithCondition(wsCoordinate, (Field field) -> field != null && field.isExpodable());
    }

    public List<WsCoordinate> getUnknownCoordinates(WsCoordinate from, int maxDistance) {
        if(maxDistance > 3) {
            System.out.println("Max distance set bigger than 3 - reduce to 3");
            maxDistance = 3;
        }
        return Coordinating.getCoordinatesToRadius(from, maxDistance).stream()
                .filter((WsCoordinate wsCoordinate) -> !knownCoordinates.containsKey(wsCoordinate))
                .collect(Collectors.toList());
    }

    public List<WsCoordinate> getCoordinatesCanRadar(int unitId, Predicate<Field> predicate, Comparator<WsCoordinate> comparator, int max) {
        WsCoordinate unitCoordinates = getUnit(unitId).getCoordinate();
        return Coordinating.getBoxCoordinates(unitCoordinates, 3).stream()
                .filter((WsCoordinate wsCoordinate) -> !wsCoordinate.equals(unitCoordinates))
                .filter((WsCoordinate wsCoordinate) -> predicate.test(knownCoordinates.get(wsCoordinate)))
                .sorted(comparator)
                .limit(max)
                .collect(Collectors.toList());
    }

    public CommonResp getLastCommonResponse() {
        return lastCommonResponse;
    }

    void print() {
        print(System.out);
    }

    void print(PrintStream out) {
        for(int x = 0; x < mapSize.getX(); ++x) {
            for(int y = 0; y < mapSize.getY(); ++y) {
                Field field = getField(x, y);

                if(field != null) {
                    if(field.isControllable()) {
                        for(java.util.Map.Entry entry : ourUnits.entrySet()) {
                            if(((Field)entry.getValue()).getCoordinate().equals(field.getCoordinate())) {
                                out.print(entry.getKey());
                                break;
                            }
                        }
                    }
                    else {
                        out.print(field.getScouting().getObject().value().charAt(0));
                    }
                }
                else {
                    out.print('?');
                }
            }
            out.println();
        }
    }

    private static int tick;
    public static int getTick() {
        return tick;
    }

    public static void advanceTick() {
        ++tick;
    }

    public void addInfo(IsMyTurnResponse isMyTurnResponse) {
        lastCommonResponse = isMyTurnResponse.getResult();
    }

    public void addInfo(ActionCostResponse actionCostResponse) {
        lastCommonResponse = actionCostResponse.getResult();
    }
}