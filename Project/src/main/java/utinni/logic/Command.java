package utinni.logic;

import eu.loxon.centralcontrol.ActionCostResponse;
import eu.loxon.centralcontrol.WsCoordinate;
import eu.loxon.centralcontrol.WsDirection;

import java.util.HashMap;

public class Command {

    public enum Type {
        Move,
        Tunnel,
        Explode,
        Xplore;

        public Integer getCost() {
            return Command.costs.get(this);
        }
    }

    private static HashMap<Type, Integer> costs = new HashMap<>();

    public static void setCosts(ActionCostResponse actionCostResponse) {
        setCost(Type.Move, actionCostResponse.getMove());
        setCost(Type.Tunnel, actionCostResponse.getDrill());
        setCost(Type.Explode, actionCostResponse.getExplode());
        setCost(Type.Xplore, actionCostResponse.getRadar());
    }

    private static void setCost(Type type, Integer cost) {
        costs.put(type, cost);
    }

    public static Type getType(Field field) {

        if(field == null) {
            return Type.Xplore;
        }
        if(field.isSteppable()) {
            return Type.Move;
        }
        else if(field.isTunnelable()) {
            return Type.Tunnel;
        }
        else if(field.isExpodable()) {
            return Type.Explode;
        }
        return null;
    }

    Type commandType;
    WsCoordinate fieldFrom;

    WsDirection direction;

    public Command(WsCoordinate fieldFrom, WsDirection direction) {
        this.fieldFrom = fieldFrom;
        this.direction = direction;
    }

    public Type getCommandType() {
        return commandType;
    }

    public void setCommandType(Type commandType) {
        this.commandType = commandType;
    }

    public WsCoordinate getAffectCoordinate() {
        return Coordinating.getNextCoordinate(fieldFrom, direction);
    }

    public boolean isValid() {
        return commandType != null;
    }

    public Integer getCost() {
        return commandType.getCost();
    }

    public WsDirection getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return commandType.name().charAt(0) + "" + direction.name().charAt(0);
    }

    @Override
    public boolean equals(Object rawOther) {
        if (rawOther == null) {
            return false;
        }
        if (this == rawOther) {
            return true;
        }
        if (!(rawOther instanceof Command)) {
            return false;
        }

        Command other = (Command) rawOther;

        return this.commandType == other.commandType
                && (this.fieldFrom == null ? other.fieldFrom == null : this.fieldFrom.equals(other.fieldFrom))
                && (this.direction == null ? other.direction == null : this.direction.equals(other.direction));
    }
}
