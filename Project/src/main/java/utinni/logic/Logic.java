
package utinni.logic;

import eu.loxon.centralcontrol.*;
import utinni.status.GameStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
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
            System.out.println("Has unit on shuttle - tick left " + GameStatus.get().tick);
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
                radarAllUnknownPoint(nextUnitId);
                radarRefreshDangerousPoint(nextUnitId);
            }
            catch (GameRuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        // TODO make the application logic here
        System.out.println("Real logic starts here " + GameStatus.get().tick);

        while(true) {

            System.out.println("Tick left " + GameStatus.get().tick);
            map.print();
            System.out.println(GameStatus.get());
            Integer nextUnitId = sleepWhile();
            System.out.println("NextUnitId: " + nextUnitId);

            try {
                boolean otherCommand = true;
                //map.hackingEnemyBuilderUnits();
                watch(nextUnitId);
                while(otherCommand) {
                    map.print();

                    BuilderUnitWrapper.targets.remove(nextUnitId);
                    boolean containExplode = GameStatus.get().explosionsLeft > 0;
                    List<Commands> possibleCommands = map.getAllShortKnowDestination(nextUnitId).stream()
                            .filter((Commands c1) -> !BuilderUnitWrapper.targets.containsValue(c1.getLastAffectingCoordinate()))
                            .filter((Commands c1) -> containExplode || c1.noExplode())
                            .sorted((Commands c1, Commands c2) -> {
                                int points = GameStatus.get().actionPointsLeft;

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
                                        result = c1.getLastType() == Command.Type.Explode
                                                || c2.getLastType() == Command.Type.Xplore ? -1 : 1;
                                    }
                                }

                                // TODO here not important that drill when neighbours contains 1 other's tunnel
                                //  if we don't need (maybe got worst logic) just comment it
                                if (result == 0) {
                                    if(c1.getLastType() == Command.Type.Tunnel) {
                                        Long enemies1 = Coordinating.getCoordinatesToRadius(c1.getLastAffectingCoordinate(), 1).stream()
                                                .filter((WsCoordinate wc) -> map.getField(wc) != null && map.getField(wc).isEnemy())
                                                .count();

                                        Long enemies2 = Coordinating.getCoordinatesToRadius(c2.getLastAffectingCoordinate(), 1).stream()
                                                .filter((WsCoordinate wc) -> map.getField(wc) != null && map.getField(wc).isEnemy())
                                                .count();

                                        result = enemies1.compareTo(enemies2);
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

                    int points = GameStatus.get().actionPointsLeft;
                    System.out.println("Possible Commands: (AP: " + points + ")");
                    for(Commands c: possibleCommands) {
                        System.out.println(c.toString() + " (Now: "+ c.getCostMax(points) + ")" );
                    }

                    System.out.println();

                    BuilderUnitWrapper.targets.put(nextUnitId, possibleCommands.get(0).getLastAffectingCoordinate());

                    for (Command command : possibleCommands.get(0).getCommands()) {
                        if (!tryDo(nextUnitId, command)) {
                            if(command.getCommandType().getCost() > GameStatus.get().actionPointsLeft) {
                                otherCommand = false;
                            }
                            break;
                        }
                    }
                }
                radarAllUnknownPoint(nextUnitId);
                radarRefreshDangerousPoint(nextUnitId);
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
        else if(GameStatus.get().actionPointsLeft >=
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
        assert(tryTunnelField.isTunnelable());
        if(tryTunnelField.isTunnelable()) {
            // if we have enough energy to tunnel
            if(GameStatus.get().actionPointsLeft >=
                    actionCostResponse.getDrill()) {

                StructureTunnelRequest structureTunnelRequest = new StructureTunnelRequest();
                structureTunnelRequest.setUnit(unitId);
                structureTunnelRequest.setDirection(wsDirection);

                map.addCache(structureTunnelRequest);

                StructureTunnelResponse structureTunnelResponse
                        = centralControl.structureTunnel(structureTunnelRequest);

                if(map.acceptCache(structureTunnelResponse.getResult().getType() == ResultType.DONE)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean tryStep(int unitId, WsDirection wsDirection) {
        Field tryStepField = map.getFieldFromUnit(unitId, wsDirection);
        assert(tryStepField.isSteppable());
        if(tryStepField.isSteppable()) {
            // if we have enough energy to tunnel
            if(GameStatus.get().actionPointsLeft >=
                    actionCostResponse.getMove()) {

                MoveBuilderUnitRequest moveBuilderUnitRequest = new MoveBuilderUnitRequest();
                moveBuilderUnitRequest.setUnit(unitId);
                moveBuilderUnitRequest.setDirection(wsDirection);

                map.addCache(moveBuilderUnitRequest);

                MoveBuilderUnitResponse moveBuilderUnitResponse
                        = centralControl.moveBuilderUnit(moveBuilderUnitRequest);


                if(map.acceptCache(moveBuilderUnitResponse.getResult().getType() == ResultType.DONE)) {
                    watch(unitId);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean tryExplode(int unitId, WsDirection wsDirection) {
        Field tryExplodeField = map.getFieldFromUnit(unitId, wsDirection);
        assert(tryExplodeField.isExpodable());
        if(tryExplodeField.isExpodable()) {
            // if we have enough energy to tunnel
            if(GameStatus.get().actionPointsLeft >=
                    actionCostResponse.getExplode() &&
                    GameStatus.get().explosionsLeft > 0) {

                ExplodeCellRequest explodeCellRequest = new ExplodeCellRequest();
                explodeCellRequest.setUnit(unitId);
                explodeCellRequest.setDirection(wsDirection);

                map.addCache(explodeCellRequest);

                ExplodeCellResponse explodeCellResponse
                        = centralControl.explodeCell(explodeCellRequest);

                if(map.acceptCache(explodeCellResponse.getResult().getType() == ResultType.DONE)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void radarAllUnknownPoint(int nextUnitId) {
        if (actionCostResponse.getRadar() > 0) {
            int maxCanRadar = GameStatus.get().actionPointsLeft / actionCostResponse.getRadar();
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

    public void radarRefreshDangerousPoint(int nextUnitId) {
        // TODO check that it's working fine
        if (actionCostResponse.getRadar() > 0) {
            int maxCanRadar = GameStatus.get().actionPointsLeft / actionCostResponse.getRadar();
            if (maxCanRadar > 0) {
                WsCoordinate unitCoordinate = map.getUnit(nextUnitId).getCoordinate();
                List<WsCoordinate> otherRefresh =
                        map.getCoordinatesCanRadar(nextUnitId,
                                (Field field) -> field != null &&
                                        (field.isSteppable() ||
                                                field.isTunnelable() ||
                                                field.isEnemy()) &&
                                        Coordinating.distance(field.getCoordinate(), unitCoordinate) > 1,
                                (WsCoordinate c1, WsCoordinate c2) -> {
                                    Integer weight1 = Coordinating.distance(c1, unitCoordinate) * 3;
                                    Integer weight2 = Coordinating.distance(c2, unitCoordinate) * 3;

                                    weight1 -= (int)Coordinating.getCoordinatesToRadius(c1, 1).stream()
                                        .filter((WsCoordinate c) -> map.knownCoordinates.containsKey(c) && map.getField(c).isEnemy())
                                        .count() * 2;
                                    weight2 -= (int)Coordinating.getCoordinatesToRadius(c2, 1).stream()
                                        .filter((WsCoordinate c) -> map.knownCoordinates.containsKey(c) && map.getField(c).isEnemy())
                                        .count() * 2;

                                    weight1 -= (int)Coordinating.getCoordinatesToRadius(c1, 1).stream()
                                        .filter((WsCoordinate c) -> !map.knownCoordinates.containsKey(c))
                                        .count();
                                    weight2 -= (int)Coordinating.getCoordinatesToRadius(c2, 1).stream()
                                        .filter((WsCoordinate c) -> !map.knownCoordinates.containsKey(c))
                                        .count();

                                    return weight1.compareTo(weight2);
                                },
                                maxCanRadar);

                System.out.println("Refresh Danger places:");
                System.out.println(Arrays.toString(otherRefresh.toArray()));

                tryRadar(nextUnitId, otherRefresh);
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
        if(GameStatus.get().actionPointsLeft >=
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
    public int sleepWhile() {
        while(true) {
            try {
                Thread.sleep(301L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }

            IsMyTurnResponse isMyTurnResponse = null;
            try { // TODO This will be good after the service call update.
                isMyTurnResponse = centralControl.isMyTurn(new IsMyTurnRequest());
            } catch (GameRuntimeException e) {
                // THAT's OK, what we expect
            }
            if (isMyTurnResponse != null && isMyTurnResponse.isIsYourTurn() &&
                    lastUnitIdWas != isMyTurnResponse.getResult().getBuilderUnit()) {

                if(GameStatus.get().tick == 0) {
                    System.exit(0);
                    // OR
                    // run();
                    // return;
                }
                System.out.println("Our turn started at:" + new Date());
                return lastUnitIdWas = isMyTurnResponse.getResult().getBuilderUnit();
            }
        }
    }
}
