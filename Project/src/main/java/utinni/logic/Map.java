package utinni.logic;

import eu.loxon.centralcontrol.*;
import utinni.App;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Map {

    WsCoordinate mapSize;
    WsCoordinate spaceShuttlePos;
    WsCoordinate spaceShuttleExitPos;
    HashMap<WsCoordinate, Field> knownCoordinates = new HashMap<>();
    HashMap<Integer, BuilderUnitWrapper> ourUnits = new HashMap<>();
    Runnable runAfterOk;
    Runnable runAfterNOk;

    public void acceptCache(CommonResp commonResp) {
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
    }

    public void addInfo(StartGameResponse startGameResponse) {
        mapSize = startGameResponse.getSize();
        for(WsBuilderunit wsBuilderunit : startGameResponse.getUnits()) {
            BuilderUnitWrapper builderUnitWrapper =
                    new BuilderUnitWrapper(wsBuilderunit, getTick());
            ourUnits.put(wsBuilderunit.getUnitid(), builderUnitWrapper);
            knownCoordinates.put(wsBuilderunit.getCord(), builderUnitWrapper);
        }
    }

    public void addInfo(RadarResponse radarResponse) {
        setScouts(radarResponse.getScout());
    }

    public void addInfo(WatchResponse watchResponse) {
        setScouts(watchResponse.getScout());
    }

    public void addInfo(GetSpaceShuttlePosResponse getSpaceShuttlePosResponse) {
        spaceShuttlePos = getSpaceShuttlePosResponse.getCord();

        knownCoordinates.put(getSpaceShuttlePosResponse.getCord(),
                new Field(getSpaceShuttlePosResponse.getCord(),
                        ObjectType.SHUTTLE, App.user, getTick()));
    }

    public void addInfo(GetSpaceShuttleExitPosResponse getSpaceShuttleExitPosResponse) {
        this.spaceShuttleExitPos = getSpaceShuttleExitPosResponse.getCord();
    }

    public void addCache(StructureTunnelRequest structureTunnelRequest) {
        WsCoordinate nextField = Coordinating.getNextCoordinate(
                getUnit(structureTunnelRequest.getUnit()).getCoordinate(),
                structureTunnelRequest.getDirection());

        runAfterOk = () -> knownCoordinates.put(nextField,
                new Field(nextField, ObjectType.TUNNEL, App.user, getTick()));
        runAfterNOk = () -> knownCoordinates.remove(nextField);
    }

    public void addCache(ExplodeCellRequest explodeCellRequest) {
        WsCoordinate nextField = Coordinating.getNextCoordinate(
                getUnit(explodeCellRequest.getUnit()).getCoordinate(),
                explodeCellRequest.getDirection());

        runAfterOk = () -> knownCoordinates.put(nextField,
                new Field(nextField, ObjectType.ROCK, null, getTick()));
        runAfterNOk = () -> knownCoordinates.remove(nextField);
    }

    public void addCache(MoveBuilderUnitRequest moveBuilderUnitRequest) {
        BuilderUnitWrapper unit = (BuilderUnitWrapper) getUnit(moveBuilderUnitRequest.getUnit());
        WsCoordinate nextField = Coordinating.getNextCoordinate(
                unit.getCoordinate(),
                moveBuilderUnitRequest.getDirection());

        runAfterOk = () -> {
            knownCoordinates.put(unit.getCoordinate(), new Field(unit.getCoordinate(),
                    ObjectType.TUNNEL, App.user, getTick()));

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

    public static int getTick() {
        return 0; // TODO get global tick
    }

    public Field getUnit(int unit) {
        return ourUnits.get(unit);
    }

    public boolean isUnitOnShuttle(int unit) {
        return getUnit(unit).getCoordinate() == spaceShuttlePos;
    }

    Set<Integer> getMyUnitIds() {
        return ourUnits.keySet();
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
        return Coordinating.getCoordinatesToRadius(from, maxDistance).stream()
                .filter((WsCoordinate wsCoordinate) -> !knownCoordinates.containsKey(wsCoordinate))
                .collect(Collectors.toList());
    }

    void print() {
        print(System.out);
    }

    void print(PrintStream out) {
        WsCoordinate wsCoordinate = new WsCoordinate();
        for(int x = 0; x < mapSize.getX(); ++x) {
            wsCoordinate.setX(x);
            for(int y = 0; y < mapSize.getY(); ++y) {

                wsCoordinate.setY(y);
                if(knownCoordinates.containsKey(wsCoordinate)) {
                    out.print(knownCoordinates.get(wsCoordinate).getScouting().getObject().value().charAt(0));
                }
                else {
                    out.print('?');
                }
            }
            out.println();
        }
    }
}