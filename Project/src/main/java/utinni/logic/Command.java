package utinni.logic;

import eu.loxon.centralcontrol.WsDirection;

public class Command {

    public enum Type {
        Move,
        Tunnel,
        Explode
    }

    Type commandType;
    WsDirection direction;

    public Type getCommandType() {
        return commandType;
    }

    public void setCommandType(Type commandType) {
        this.commandType = commandType;
    }

    public WsDirection getDirection() {
        return direction;
    }

    public void setDirection(WsDirection direction) {
        this.direction = direction;
    }
}
