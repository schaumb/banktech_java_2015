package utinni.logic;

import eu.loxon.centralcontrol.*;
import utinni.App;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Field {
    private Scouting scouting;
    private int when;

    public Field(Scouting scouting, int when) {
        this.scouting = scouting;
        setWhen(when);
    }

    public Field(WsCoordinate wsCoordinate, ObjectType objectType, String team, int when) {
        scouting = new Scouting();
        scouting.setCord(wsCoordinate);
        scouting.setObject(objectType);
        scouting.setTeam(team);
        setWhen(when);
    }

    private ObjectType getObjectType() {
        return scouting.getObject();
    }

    private String getTeam() {
        return scouting.getTeam();
    }

    public boolean isGranite() { return getObjectType() == ObjectType.GRANITE; }
    public boolean isRock() { return getObjectType() == ObjectType.ROCK; }
    public boolean isBuilderUnit() { return getObjectType() == ObjectType.BUILDER_UNIT; }
    public boolean isTunnel() { return getObjectType() == ObjectType.TUNNEL; }
    public boolean isObsidian() { return getObjectType() == ObjectType.OBSIDIAN; }
    public boolean isShuttle() { return getObjectType() == ObjectType.SHUTTLE; }
    public boolean isEmpty() { return getObjectType() == null; }

    public boolean isOurs() {
        return App.user.equals(scouting.getTeam());
    }

    public boolean isControllable() {
        return isOurs() && isBuilderUnit();
    }

    public boolean isTunnelable() {
        return isRock();
    }

    public boolean isExpodable() {
        return isGranite() ||
                (isTunnel() && !isOurs());
    }

    public boolean isEnemy() {
        return isEnemyBuilder() || isEnemyTunnel();
    }

    public boolean isEnemyBuilder() {
        return !isOurs() && isBuilderUnit();
    }

    public boolean isEnemyTunnel() {
        return !isOurs() && isTunnel();
    }

    public boolean isSteppable() {
        return isTunnel() && isOurs();
    }

    WsCoordinate getCoordinate() {
        return scouting.getCord();
    }

    Scouting getScouting() {
        return scouting;
    }

    public int getWhen() {
        return when;
    }

    public void setWhen(int when) {
        this.when = when;
    }

}

class BuilderUnitWrapper extends Field {
    int unitId;

    public BuilderUnitWrapper (WsBuilderunit wsBuilderunit, int when) {
        super(wsBuilderunit.getCord(), ObjectType.BUILDER_UNIT, App.user, when);
        this.unitId = wsBuilderunit.getUnitid();
    }

    public int getUnitId() {
        return unitId;
    }

    public static Integer getDirectionWeight(int unitId, WsDirection dir) {
        switch (dir) {
            case UP:
                return (0 + unitId) % 4;
            case DOWN:
                return (1 + unitId) % 4;
            case LEFT:
                return (2 + unitId) % 4;
            case RIGHT:
                return (3 + unitId) % 4;
        }
        return unitId;
    }


    public static HashMap<Integer, WsCoordinate> targets = new HashMap<>();
    public static ArrayList<WsCoordinate> globalTargets = new ArrayList<>();

    public static void setGlobalTargets(WsCoordinate shuttlePos, WsCoordinate mapSize) {
        if(shuttlePos.getX() > mapSize.getX() / 2) {
            globalTargets.add(new WsCoordinate(mapSize.getX() * 4 / 6, mapSize.getY() / 2));
            globalTargets.add(new WsCoordinate(mapSize.getX() * 5 / 6, mapSize.getY() / 2));
        }
        else {
            globalTargets.add(new WsCoordinate(mapSize.getX() * 1 / 6, mapSize.getY() / 2));
            globalTargets.add(new WsCoordinate(mapSize.getX() * 2 / 6, mapSize.getY() / 2));
        }

        if(shuttlePos.getY() > mapSize.getY() / 2) {
            globalTargets.add(new WsCoordinate(mapSize.getX() / 2, mapSize.getY() * 4 / 6));
            globalTargets.add(new WsCoordinate(mapSize.getX() / 2, mapSize.getY() * 5 / 6));
        }
        else {
            globalTargets.add(new WsCoordinate(mapSize.getX() / 2, mapSize.getY() * 1 / 6));
            globalTargets.add(new WsCoordinate(mapSize.getX() / 2, mapSize.getY() * 2 / 6));
        }
        System.out.println(Arrays.toString(globalTargets.toArray()));
    }

    public static WsCoordinate getGlobalTarget(Integer unitId) {
        return globalTargets.get(unitId);
    }
}

