
package utinni.logic;

import eu.loxon.centralcontrol.*;

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
            }
            Map.advanceTick();

        }

        map.print();

        // TODO make the application logic here

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

            WatchResponse watchResponse = centralControl.watch(watchRequest);

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
        System.out.println("Try to step " + unitId + " to " + wsDirection.value());
        System.out.println("Is steppable: " + tryTunnelField.getScouting().getObject());
        System.out.print("From: " + map.getUnit(unitId).getCoordinate());
        System.out.println(" To: " + Coordinating.getNextCoordinate(map.getUnit(unitId).getCoordinate(), wsDirection));
        if(tryTunnelField.isSteppable()) {
            // if we have enough energy to tunnel
            System.out.println("Action point left: " + map.getLastCommonResponse().getActionPointsLeft()
             + " need:" + actionCostResponse.getMove());
            if(map.getLastCommonResponse().getActionPointsLeft() >=
                    actionCostResponse.getMove()) {

                MoveBuilderUnitRequest moveBuilderUnitRequest = new MoveBuilderUnitRequest();
                moveBuilderUnitRequest.setUnit(unitId);
                moveBuilderUnitRequest.setDirection(wsDirection);

                map.addCache(moveBuilderUnitRequest);

                MoveBuilderUnitResponse moveBuilderUnitResponse
                        = centralControl.moveBuilderUnit(moveBuilderUnitRequest);


                if(map.acceptCache(moveBuilderUnitResponse.getResult())) {
                    System.out.println("ALL OK");
                    return true;
                }
                System.out.println("Stg went wrong");
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
        // if we have enough energy to radar some cells
        List<WsCoordinate> unknownCoordinates = map.getUnknownCoordinates(
                map.getUnit(unitId).getCoordinate(), radius);

        if(map.getLastCommonResponse().getActionPointsLeft() >=
                        unknownCoordinates.size() * actionCostResponse.getRadar()) {

            if(unknownCoordinates.size() == 0) {
                return true;
            }

            RadarRequest radarRequest = new RadarRequest();
            radarRequest.setUnit(unitId);
            for(WsCoordinate wsCoordinate : unknownCoordinates) {
                radarRequest.getCord().add(wsCoordinate);
            }

            RadarResponse radarResponse = centralControl.radar(radarRequest);

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
