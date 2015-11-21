
package utinni.logic;

import eu.loxon.centralcontrol.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class Logic {

    private Map map = new Map();
    private CentralControl centralControl;
    private ActionCostResponse actionCostResponse;

    public Logic(CentralControl centralControl) {
        this.centralControl = centralControl;
    }

    public void run() {

        // First get the static informations
        StartGameResponse startGameResponse = centralControl.startGame(new StartGameRequest());
        map.addInfo(startGameResponse);

        System.out.println("Units size: " + startGameResponse.getUnits().size());

        GetSpaceShuttlePosResponse getSpaceShuttlePosResponse =
                centralControl.getSpaceShuttlePos(new GetSpaceShuttlePosRequest());

        map.addInfo(getSpaceShuttlePosResponse);

        GetSpaceShuttleExitPosResponse getSpaceShuttleExitPosResponse =
                centralControl.getSpaceShuttleExitPos(new GetSpaceShuttleExitPosRequest());

        map.addInfo(getSpaceShuttleExitPosResponse);

        actionCostResponse = centralControl.getActionCost(new ActionCostRequest());

        map.addInfo(actionCostResponse);


        // beginning strategy
        if(map.isReallyFirstTick()) {
            System.out.println("First tick!");
        }
        else {
            System.out.println("Not in the starting position. It's just a continue ~ some bad things can happen");
        }

        while(map.hasUnitOnShuttle()) {
            System.out.println("Has unit on shuttle - tick " + Map.getTick());
            Set<Integer> ids = map.getMyUnitIds();
            while(ids.size() > 0) {
                map.print();
                Integer nextUnitId = sleepWhile(ids);
                ids.remove(nextUnitId);
                System.out.println("NextUnitId: " + nextUnitId);

                watch(nextUnitId);
                if (map.isUnitOnShuttle(nextUnitId)) {
                    if (tryExplode(nextUnitId, map.getDirectionFromShuttle())) {
                        // ...
                    }

                    if (tryTunnel(nextUnitId, map.getDirectionFromShuttle())) {

                    } else if (tryStep(nextUnitId, map.getDirectionFromShuttle())) {
                        watch(nextUnitId);
                    }
                }
                // not else!
                if (!map.isUnitOnShuttle(nextUnitId)) {
                    boolean hasSomeCommand = false;
                    do {
                        hasSomeCommand = false;
                        WsCoordinate unitCoordinate = map.getUnit(nextUnitId).getCoordinate();
                        List<WsDirection> movableDir = map.getKnownSteppableDirections(unitCoordinate);

                        for (WsDirection wsDirection : movableDir) {
                            WsCoordinate nextCoordinate = Coordinating.getNextCoordinate(unitCoordinate, wsDirection);
                            if (Coordinating.distance(map.spaceShuttleExitPos, unitCoordinate) <
                                    Coordinating.distance(map.spaceShuttleExitPos, nextCoordinate)) {
                                if (tryStep(nextUnitId, wsDirection)) {
                                    hasSomeCommand = true;
                                    watch(nextUnitId);
                                    break;
                                }
                            }
                        }

                        if (!hasSomeCommand) {
                            List<WsDirection> tunnelableDir = map.getKnownTunnelableDirections(unitCoordinate);

                            for (WsDirection wsDirection : tunnelableDir) {
                                WsCoordinate nextCoordinate = Coordinating.getNextCoordinate(unitCoordinate, wsDirection);
                                if (Coordinating.distance(map.spaceShuttleExitPos, unitCoordinate) <
                                        Coordinating.distance(map.spaceShuttleExitPos, nextCoordinate)) {
                                    if (tryTunnel(nextUnitId, wsDirection)) {
                                        hasSomeCommand = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (!hasSomeCommand) {
                            List<WsDirection> explodableDir = map.getKnownExplodableDirections(unitCoordinate);

                            for (WsDirection wsDirection : explodableDir) {
                                WsCoordinate nextCoordinate = Coordinating.getNextCoordinate(unitCoordinate, wsDirection);
                                if (Coordinating.distance(map.spaceShuttleExitPos, unitCoordinate) <
                                        Coordinating.distance(map.spaceShuttleExitPos, nextCoordinate)) {
                                    if (tryExplode(nextUnitId, wsDirection)) {
                                        hasSomeCommand = true;
                                        break;
                                    }
                                }
                            }
                        }
                    } while (hasSomeCommand);
                }
                for (int i = 3; i > 0; --i) {
                    if (tryRadar(nextUnitId, i)) {
                        break;
                    }
                }

                if(actionCostResponse.getRadar() > 0) {
                    int maxCanRadar = map.getLastCommonResponse().getActionPointsLeft() / actionCostResponse.getRadar();
                    if (maxCanRadar > 0) {
                        WsCoordinate unitCoordinate = map.getUnit(nextUnitId).getCoordinate();
                        List<WsCoordinate> otherUnknowns =
                                map.getCoordinatesCanRadar(nextUnitId,
                                        (Field field) -> field == null,
                                        (WsCoordinate c1, WsCoordinate c2) ->
                                                Coordinating.distance(c1, unitCoordinate).compareTo(
                                                        Coordinating.distance(c2, unitCoordinate)),
                                        maxCanRadar);
                        tryRadar(nextUnitId, otherUnknowns);
                    }
                }
                else {
                    tryRadar(nextUnitId, map.getCoordinatesCanRadar(nextUnitId,
                            (a) -> true,
                            (a, b) -> 0,
                            49));
                }
            }
            Map.advanceTick();

        }

        map.print();

        // TODO make the application logic here

        while(true) {
            // now is some dummy moving

            System.out.println("Tick " + Map.getTick());
            Set<Integer> ids = map.getMyUnitIds();
            while(ids.size() > 0) {
                map.print();
                System.out.println(map.getLastCommonResponse().toString());
                Integer nextUnitId = sleepWhile(ids);
                ids.remove(nextUnitId);
                System.out.println("NextUnitId: " + nextUnitId);
                watch(nextUnitId);

                boolean hasSomeCommand = false;
                do {
                    hasSomeCommand = false;
                    WsCoordinate unitCoordinate = map.getUnit(nextUnitId).getCoordinate();
                    List<WsDirection> movableDir = map.getKnownSteppableDirections(unitCoordinate);

                    if (!hasSomeCommand) {
                        List<WsDirection> tunnelableDir = map.getKnownTunnelableDirections(unitCoordinate);

                        for (WsDirection wsDirection : tunnelableDir) {
                            WsCoordinate nextCoordinate = Coordinating.getNextCoordinate(unitCoordinate, wsDirection);
                            if (Coordinating.distance(map.spaceShuttleExitPos, unitCoordinate) <
                                    Coordinating.distance(map.spaceShuttleExitPos, nextCoordinate)) {
                                if (tryTunnel(nextUnitId, wsDirection)) {
                                    hasSomeCommand = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!hasSomeCommand) {
                        List<WsDirection> explodableDir = map.getKnownExplodableDirections(unitCoordinate);

                        for (WsDirection wsDirection : explodableDir) {
                            WsCoordinate nextCoordinate = Coordinating.getNextCoordinate(unitCoordinate, wsDirection);
                            if (Coordinating.distance(map.spaceShuttleExitPos, unitCoordinate) <
                                    Coordinating.distance(map.spaceShuttleExitPos, nextCoordinate)) {
                                if (tryExplode(nextUnitId, wsDirection)) {
                                    hasSomeCommand = true;
                                    break;
                                }
                            }
                        }
                    }

                    for (WsDirection wsDirection : movableDir) {
                        WsCoordinate nextCoordinate = Coordinating.getNextCoordinate(unitCoordinate, wsDirection);
                        if (Coordinating.distance(map.spaceShuttleExitPos, unitCoordinate) <
                                Coordinating.distance(map.spaceShuttleExitPos, nextCoordinate)) {
                            if (tryStep(nextUnitId, wsDirection)) {
                                hasSomeCommand = true;
                                watch(nextUnitId);
                                break;
                            }
                        }
                    }
                } while (hasSomeCommand);
            }

            Map.advanceTick();
        }
    }

    public boolean watch(int unitId) {

        List<WsCoordinate> unknownCoordinates = map.getUnknownCoordinates(
                map.getUnit(unitId).getCoordinate(), 1);

        if(actionCostResponse.getWatch() >
                unknownCoordinates.size() * actionCostResponse.getRadar()) {
            return tryRadar(unitId, 1);
        }
        else if(map.getLastCommonResponse().getActionPointsLeft() >=
                actionCostResponse.getWatch()) {

            WatchRequest watchRequest = new WatchRequest();
            watchRequest.setUnit(unitId);

            System.out.println(watchRequest);
            WatchResponse watchResponse = centralControl.watch(watchRequest);
            System.out.println(watchResponse);

            map.addInfo(watchResponse);

            return true;
        }
        return false;
    }

    public boolean tryTunnel(int unitId, WsDirection wsDirection) {
        Field tryTunnelField = map.getFieldFromUnit(unitId, wsDirection);
        if(tryTunnelField.isTunnelable()) {
            // if we have enough energy to tunnel
            if(map.getLastCommonResponse().getActionPointsLeft() >=
                    actionCostResponse.getDrill()) {

                StructureTunnelRequest structureTunnelRequest = new StructureTunnelRequest();
                structureTunnelRequest.setUnit(unitId);
                structureTunnelRequest.setDirection(wsDirection);

                map.addCache(structureTunnelRequest);

                StructureTunnelResponse structureTunnelResponse
                        = centralControl.structureTunnel(structureTunnelRequest);

                if(map.acceptCache(structureTunnelResponse.getResult())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean tryStep(int unitId, WsDirection wsDirection) {
        Field tryTunnelField = map.getFieldFromUnit(unitId, wsDirection);
        if(tryTunnelField.isSteppable()) {
            // if we have enough energy to tunnel
            if(map.getLastCommonResponse().getActionPointsLeft() >=
                    actionCostResponse.getMove()) {

                MoveBuilderUnitRequest moveBuilderUnitRequest = new MoveBuilderUnitRequest();
                moveBuilderUnitRequest.setUnit(unitId);
                moveBuilderUnitRequest.setDirection(wsDirection);

                map.addCache(moveBuilderUnitRequest);

                MoveBuilderUnitResponse moveBuilderUnitResponse
                        = centralControl.moveBuilderUnit(moveBuilderUnitRequest);


                if(map.acceptCache(moveBuilderUnitResponse.getResult())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean tryExplode(int unitId, WsDirection wsDirection) {
        Field tryExplodeField = map.getFieldFromUnit(unitId, wsDirection);
        if(tryExplodeField.isExpodable()) {
            // if we have enough energy to tunnel
            if(map.getLastCommonResponse().getActionPointsLeft() >=
                    actionCostResponse.getExplode() &&
                    map.getLastCommonResponse().getExplosivesLeft() > 0) {

                ExplodeCellRequest explodeCellRequest = new ExplodeCellRequest();
                explodeCellRequest.setUnit(unitId);
                explodeCellRequest.setDirection(wsDirection);

                map.addCache(explodeCellRequest);

                ExplodeCellResponse explodeCellResponse
                        = centralControl.explodeCell(explodeCellRequest);

                if(map.acceptCache(explodeCellResponse.getResult())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean tryRadar(int unitId, int radius) {
        List<WsCoordinate> unknownCoordinates = map.getUnknownCoordinates(
                map.getUnit(unitId).getCoordinate(), radius);

        return tryRadar(unitId, unknownCoordinates);
    }

    public boolean tryRadar(int unitId, List<WsCoordinate> coordinates) {
        // if we have enough energy to radar some cells
        if(map.getLastCommonResponse().getActionPointsLeft() >=
                coordinates.size() * actionCostResponse.getRadar()) {

            if(coordinates.size() == 0) {
                return true;
            }

            RadarRequest radarRequest = new RadarRequest();
            radarRequest.setUnit(unitId);
            for(WsCoordinate wsCoordinate : coordinates) {
                radarRequest.getCord().add(wsCoordinate);
            }
            System.out.println(radarRequest);
            RadarResponse radarResponse = centralControl.radar(radarRequest);
            System.out.println(radarResponse);

            map.addInfo(radarResponse);

            return true;
        }
        return false;
    }

    private int lastUnitIdWas = -1;
    public int sleepWhile(Set<Integer> validUnitIds) {
        while(true) {
            IsMyTurnResponse isMyTurnResponse = centralControl.isMyTurn(new IsMyTurnRequest());
            if (isMyTurnResponse.isIsYourTurn() &&
                    validUnitIds.contains(isMyTurnResponse.getResult().getBuilderUnit()) &&
                    lastUnitIdWas != isMyTurnResponse.getResult().getBuilderUnit()) {

                map.addInfo(isMyTurnResponse);

                return lastUnitIdWas = isMyTurnResponse.getResult().getBuilderUnit();
            }

            try {
                Thread.sleep(301L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
