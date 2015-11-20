package utinni.logic;

import eu.loxon.centralcontrol.WsCoordinate;
import eu.loxon.centralcontrol.WsDirection;

import java.util.ArrayList;
import java.util.List;

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

    public static int distance(WsCoordinate coord1, WsCoordinate coord2) {
        return Math.abs(coord1.getX() - coord2.getX()) + Math.abs(coord1.getY() - coord2.getY());
    }

    public static List<WsCoordinate> getCoordinatesToRadius(WsCoordinate wsCoordinate, int radius) {
        List<WsCoordinate> result = new ArrayList<>();

        for(int x = wsCoordinate.getX() - radius; x <= wsCoordinate.getX() + radius; ++x) {
            int maxYRadius = radius - Math.abs(x - wsCoordinate.getX());
            for(int y = wsCoordinate.getY() - maxYRadius; y <= wsCoordinate.getY() + maxYRadius; ++y) {
                WsCoordinate tempCoordinate = new WsCoordinate();
                tempCoordinate.setX(x);
                tempCoordinate.setY(y);
                result.add(tempCoordinate);
            }
        }
        return result;
    }
}
