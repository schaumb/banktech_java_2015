package utinni.logic;

import eu.loxon.centralcontrol.ObjectType;
import eu.loxon.centralcontrol.Scouting;
import eu.loxon.centralcontrol.WsBuilderunit;
import eu.loxon.centralcontrol.WsCoordinate;
import utinni.App;

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
}

