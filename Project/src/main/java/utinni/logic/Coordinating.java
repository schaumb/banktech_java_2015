package utinni.logic;

import eu.loxon.centralcontrol.WsCoordinate;
import eu.loxon.centralcontrol.WsDirection;

public class Coordinating {

    public static WsCoordinate getNextCoordinate(WsCoordinate wsCoordinate, WsDirection wsDirection) {
        WsCoordinate result = new WsCoordinate();
        result.setX(wsCoordinate.getX());
        result.setY(wsCoordinate.getY());

        switch (wsDirection) {
            case UP:
                result.setY(result.getY() + 1);
                break;
            case DOWN:
                result.setY(result.getY() - 1);
                break;
            case LEFT:
                result.setX(result.getX() - 1);
                break;
            case RIGHT:
                result.setX(result.getX() + 1);
                break;
        }
        return result;
    }
}
