
package utinni.logic;

import eu.loxon.centralcontrol.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Logic {

    private Map map = new Map();
    private CentralControl centralControl;
    public static ActionCostResponse actionCostResponse;

    public Logic(CentralControl centralControl) {
        this.centralControl = centralControl;
    }

    private void getFirstInformations() {
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

        Command.setCosts(actionCostResponse);
    }
    public void run() {

        // First get the static informations
        getFirstInformations();

        if(map.isReallyFirstTick()) {
            System.out.println("First tick!");
        }
        else {
            System.out.println("Not in the starting position. It's just a continue ~ some bad things can happen");
        }

        // beginning strategy while has unit on shuttle

        while(map.hasUnitOnShuttle()) {
            System.out.println("Has unit on shuttle - tick left " + map.getTick());
            map.print();
            Integer nextUnitId = sleepWhile();
            System.out.println("NextUnitId: " + nextUnitId);
            try {
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
                radarAllPoint(nextUnitId);
            }
            catch (GameRuntimeException e) {
                System.out.println(e.getMessage());
                /*
                if(map.getLastCommonResponse().getTurnsLeft() == 0) {
                    //run();
                    return;
                }
                */
            }
        }

        // TODO make the application logic here
        System.out.println("Real logic starts here " + map.getTick());

        while(true) {

            System.out.println("Tick left " + map.getTick());
            map.print();
            System.out.println(map.getLastCommonResponse().toString());
            Integer nextUnitId = sleepWhile();
            System.out.println("NextUnitId: " + nextUnitId);

            try {
                boolean otherCommand = true;

                while(otherCommand) {
                    watch(nextUnitId);
                    map.print();

                    BuilderUnitWrapper.targets.remove(nextUnitId);

                    List<Commands> possibleCommands = map.getAllShortKnowDestination(nextUnitId).stream()
                            .filter((Commands c1) -> !BuilderUnitWrapper.targets.containsValue(c1.getLastAffectingCoordinate()))
                            .sorted((Commands c1, Commands c2) -> {
                                int points = map.getLastCommonResponse().getActionPointsLeft();

                                Integer c1Cost = c1.getCost();
                                Integer c2Cost = c2.getCost();
                                Integer c1Next = c1Cost - c1.getCostMax(points);
                                Integer c2Next = c2Cost - c2.getCostMax(points);


                                int result = 0;

                                if (result == 0) {
                                    result = c1Next.compareTo(c2Next);
                                }

                                if (result == 0 && c1Next == 0) {
                                    result = c1Cost.compareTo(c2Cost);
                                }

                                if (result == 0) {
                                    if (c1.getLastType() != c2.getLastType()) {
                                        result = c1.getLastType() == Command.Type.Tunnel
                                                || c2.getLastType() == Command.Type.Xplore ? -1 : 1;
                                    }
                                }

                                if (result == 0) {
                                    result = Coordinating.distance(c1.getLastAffectingCoordinate(), map.spaceShuttlePos).compareTo(
                                            Coordinating.distance(c2.getLastAffectingCoordinate(), map.spaceShuttlePos));
                                }

                                if (result == 0) {
                                    result = BuilderUnitWrapper.getDirectionWeight(nextUnitId, c1.getFirstCommand().getDirection()).compareTo(
                                            BuilderUnitWrapper.getDirectionWeight(nextUnitId, c2.getFirstCommand().getDirection()));
                                }

                                return result;
                            }).collect(Collectors.toList());

                    if(possibleCommands.size() == 0) {
                        otherCommand = false;
                        continue;
                    }

                    int points = map.getLastCommonResponse().getActionPointsLeft();
                    System.out.println("Possible Commands: (AP: " + points + ")");
                    for(Commands c: possibleCommands) {
                        System.out.println(c.toString() + " (Now: "+ c.getCostMax(points) + ")" );
                    }

                    System.out.println();

                    BuilderUnitWrapper.targets.put(nextUnitId, possibleCommands.get(0).getLastAffectingCoordinate());

                    for (Command command : possibleCommands.get(0).getCommands()) {
                        if (!tryDo(nextUnitId, command)) {
                            if(command.getCommandType().getCost() > map.getLastCommonResponse().getActionPointsLeft()) {
                                otherCommand = false;
                            }
                            break;
                        }
                    }
                }
                radarAllPoint(nextUnitId);
            }
            catch (GameRuntimeException e) {
                System.out.println(e.getMessage());
            }
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
            ++StrategyObserver.get().watchCount;

            return true;
        }
        return false;
    }

    public Boolean tryDo(int unitId, Command command) {
        switch(command.getCommandType()) {
            case Move:
                return tryStep(unitId, command.getDirection());
            case Tunnel:
                return tryTunnel(unitId, command.getDirection());
            case Explode:
                return tryExplode(unitId, command.getDirection());
            case Xplore:
                return tryRadar(unitId, new ArrayList<WsCoordinate>(){{ command.getAffectCoordinate(); }});
        }
        return null;
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
                    ++StrategyObserver.get().tunnelMake;

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
                    watch(unitId);
                    ++StrategyObserver.get().move;
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
                    ++StrategyObserver.get().explode;
                    return true;
                }
            }
        }
        return false;
    }

    public void radarAllPoint(int nextUnitId) {
        if (actionCostResponse.getRadar() > 0) {
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
        } else {
            tryRadar(nextUnitId, map.getCoordinatesCanRadar(nextUnitId,
                    (a) -> true,
                    (a, b) -> 0,
                    49));
        }
    }

    public boolean tryRadar(int unitId, int stepRadius) {
        List<WsCoordinate> unknownCoordinates = map.getUnknownCoordinates(
                map.getUnit(unitId).getCoordinate(), stepRadius);

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

            ++StrategyObserver.get().radarCount;
            StrategyObserver.get().radarField += coordinates.size();

            return true;
        }
        return false;
    }

    private int lastUnitIdWas = -1;
    public int sleepWhile() {
        while(true) {
            try {
                Thread.sleep(301L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }

            IsMyTurnResponse isMyTurnResponse = centralControl.isMyTurn(new IsMyTurnRequest());
            if (isMyTurnResponse.isIsYourTurn() &&
                    lastUnitIdWas != isMyTurnResponse.getResult().getBuilderUnit()) {

                try {
                    map.addInfo(isMyTurnResponse);
                } catch (GameRuntimeException e) {
                    // THAT's OK, what we expect
                }

                if(map.getLastCommonResponse().getTurnsLeft() == 0) {
                    System.exit(0);
                    // OR
                    // run();
                    // return;
                }
                ++StrategyObserver.get().canMove;
                return lastUnitIdWas = isMyTurnResponse.getResult().getBuilderUnit();
            }
        }
    }
}
