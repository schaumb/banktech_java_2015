package utinni.logic;

import eu.loxon.centralcontrol.*;
import utinni.App;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Map {

    WsCoordinate mapSize;
    WsCoordinate spaceShuttlePos;
    WsCoordinate spaceShuttleExitPos;
    HashMap<WsCoordinate, Field> knownCoordinates = new HashMap<WsCoordinate, Field>();
    HashMap<Integer, BuilderUnitWrapper> ourUnits = new HashMap<Integer, BuilderUnitWrapper>();
    Runnable runAfterOk;

    public void acceptCache(CommonResp commonResp) {
        if(commonResp.getType() == ResultType.DONE && runAfterOk != null) {
            runAfterOk.run();
        }
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

    public void addCache(final StructureTunnelRequest structureTunnelRequest) {
        runAfterOk = new Runnable() {
            public void run() {
                WsCoordinate nextField = Coordinating.getNextCoordinate(
                        getUnit(structureTunnelRequest.getUnit()).getCoordinate(),
                        structureTunnelRequest.getDirection());

                knownCoordinates.get(nextField).setWhen(getTick());
                knownCoordinates.get(nextField).getScouting().setObject(ObjectType.TUNNEL);
            }
        };
    }

    public void addCache(final ExplodeCellRequest explodeCellRequest) {
        runAfterOk = new Runnable() {
            public void run() {
                WsCoordinate nextField = Coordinating.getNextCoordinate(
                        getUnit(explodeCellRequest.getUnit()).getCoordinate(),
                        explodeCellRequest.getDirection());

                knownCoordinates.get(nextField).setWhen(getTick());
                knownCoordinates.get(nextField).getScouting().setObject(ObjectType.ROCK);
            }
        };
    }

    public void addCache(final MoveBuilderUnitRequest moveBuilderUnitRequest) {
        runAfterOk = new Runnable() {
            public void run() {
                Field unit = getUnit(moveBuilderUnitRequest.getUnit());

                WsCoordinate nextField = Coordinating.getNextCoordinate(
                        unit.getCoordinate(),
                        moveBuilderUnitRequest.getDirection());

                knownCoordinates.remove(unit.getCoordinate());

                unit.setWhen(getTick());
                unit.getScouting().setCord(nextField);
                knownCoordinates.put(nextField, unit);
            }
        };
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

    public List<WsDirection> getKnewStepableDirections(WsCoordinate wsCoordinate) {
        List<WsDirection> result = new ArrayList<WsDirection>();
        for(WsDirection wsDirection : WsDirection.values()) {
            WsCoordinate nextCoordinate = Coordinating.getNextCoordinate(wsCoordinate, wsDirection);
            if(knownCoordinates.containsKey(nextCoordinate) &&
                    knownCoordinates.get(nextCoordinate).isSteppable()) {
                result.add(wsDirection);
            }
        }

        return result;
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