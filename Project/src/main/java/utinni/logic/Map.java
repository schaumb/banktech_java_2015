package utinni.logic;

import eu.loxon.centralcontrol.*;
import utinni.Config;
import utinni.status.GameStatus;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Map {

    public WsCoordinate mapSize;
    public WsCoordinate spaceShuttlePos;
    public WsCoordinate spaceShuttleExitPos;
    public HashMap<WsCoordinate, Field> knownCoordinates = new HashMap<>();
    public HashMap<Integer, BuilderUnitWrapper> ourUnits = new HashMap<>();
    private Runnable runAfterOk;
    private Runnable runAfterNOk;

    public boolean acceptCache(boolean isDone) {
        if (isDone) {
            if (runAfterOk != null) {
                runAfterOk.run();
            }
        } else if (runAfterNOk != null) {
            runAfterNOk.run();
        }
        runAfterNOk = null;
        runAfterOk = null;

        return isDone;
    }

    public void addInfo(StartGameResponse startGameResponse) {
        mapSize = startGameResponse.getSize();
        mapSize.setX(mapSize.getX() + 1);
        mapSize.setY(mapSize.getY() + 1);
        for (int x = 0; x < mapSize.getX(); ++x) {
            WsCoordinate coord = new WsCoordinate(x, 0);
            knownCoordinates.put(coord,
                    new Field(coord, ObjectType.OBSIDIAN, null, GameStatus.get().tick));

            coord = new WsCoordinate(x, mapSize.getY() - 1);
            knownCoordinates.put(coord,
                    new Field(coord, ObjectType.OBSIDIAN, null, GameStatus.get().tick));
        }

        for (int y = 0; y < mapSize.getY(); ++y) {
            WsCoordinate coord = new WsCoordinate(0, y);
            knownCoordinates.put(coord,
                    new Field(coord, ObjectType.OBSIDIAN, null, GameStatus.get().tick));

            coord = new WsCoordinate(mapSize.getX() - 1, y);
            knownCoordinates.put(coord,
                    new Field(coord, ObjectType.OBSIDIAN, null, GameStatus.get().tick));
        }

        System.out.println("Field size is: " + mapSize.getX() + " * " + mapSize.getY());

        for (WsBuilderunit wsBuilderunit : startGameResponse.getUnits()) {
            BuilderUnitWrapper builderUnitWrapper =
                    new BuilderUnitWrapper(wsBuilderunit, GameStatus.get().tick);
            ourUnits.put(wsBuilderunit.getUnitid(), builderUnitWrapper);
            knownCoordinates.put(wsBuilderunit.getCord(), builderUnitWrapper);

            System.out.println("My unit (id: " + wsBuilderunit.getUnitid() + ") in coord: "
                    + wsBuilderunit.getCord());
        }
    }

    public void addInfo(RadarResponse radarResponse) {
        setScouts(radarResponse.getScout());
    }

    public void addInfo(WatchResponse watchResponse) {
        setScouts(watchResponse.getScout());
    }

    public void addInfo(GetSpaceShuttlePosResponse getSpaceShuttlePosResponse) {
        spaceShuttlePos = getSpaceShuttlePosResponse.getCord();

        knownCoordinates.put(spaceShuttlePos,
                new Field(spaceShuttlePos,
                        ObjectType.SHUTTLE, GameStatus.get().userName, GameStatus.get().tick));

        System.out.println("My space shuttle coord: " + spaceShuttlePos);
    }

    public void addInfo(GetSpaceShuttleExitPosResponse getSpaceShuttleExitPosResponse) {
        spaceShuttleExitPos = getSpaceShuttleExitPosResponse.getCord();

        if (isReallyFirstTick()) {
            knownCoordinates.put(spaceShuttleExitPos,
                    new Field(spaceShuttleExitPos,
                            ObjectType.ROCK, null, GameStatus.get().tick));
        }

        System.out.println("My space shuttle exit coord: " + spaceShuttleExitPos);
    }

    public void addCache(StructureTunnelRequest structureTunnelRequest) {
        System.out.println(structureTunnelRequest);
        WsCoordinate nextField = Coordinating.getNextCoordinate(
                getUnit(structureTunnelRequest.getUnit()).getCoordinate(),
                structureTunnelRequest.getDirection());

        runAfterOk = () -> {
            knownCoordinates.put(nextField, new Field(nextField, ObjectType.TUNNEL,
                    GameStatus.get().userName, GameStatus.get().tick));
            GameStatus.get().score.setReward(
                    GameStatus.get().score.getReward() + Config.REWARD_FOR_A_TUNNEL);
        };
        runAfterNOk = () -> knownCoordinates.remove(nextField);
    }

    public void addCache(ExplodeCellRequest explodeCellRequest) {
        System.out.println(explodeCellRequest);
        WsCoordinate nextField = Coordinating.getNextCoordinate(
                getUnit(explodeCellRequest.getUnit()).getCoordinate(),
                explodeCellRequest.getDirection());

        runAfterOk = () -> knownCoordinates.put(nextField,
                new Field(nextField, ObjectType.ROCK, null, GameStatus.get().tick));
        runAfterNOk = () -> knownCoordinates.remove(nextField);
    }

    public void addCache(MoveBuilderUnitRequest moveBuilderUnitRequest) {
        System.out.println(moveBuilderUnitRequest);
        BuilderUnitWrapper unit = (BuilderUnitWrapper) getUnit(moveBuilderUnitRequest.getUnit());
        WsCoordinate nextField = Coordinating.getNextCoordinate(
                unit.getCoordinate(),
                moveBuilderUnitRequest.getDirection());

        runAfterOk = () -> {
            if (!unit.getCoordinate().equals(spaceShuttlePos)) {
                knownCoordinates.put(unit.getCoordinate(), new Field(unit.getCoordinate(),
                        ObjectType.TUNNEL, GameStatus.get().userName, GameStatus.get().tick));
            }
            unit.setWhen(GameStatus.get().tick);
            unit.getScouting().setCord(nextField);
            knownCoordinates.put(nextField, unit);
        };
        runAfterNOk = () -> knownCoordinates.remove(nextField);
    }

    private void setScouts(List<Scouting> scouts) {
        for (Scouting scouting : scouts) {
            knownCoordinates.put(scouting.getCord(),
                    new Field(scouting, GameStatus.get().tick));
        }
    }

    public Field getUnit(int unit) {
        return ourUnits.get(unit);
    }

    public Field getField(WsCoordinate wsCoordinate) {
        return knownCoordinates.get(wsCoordinate);
    }

    public Field getField(int x, int y) {
        WsCoordinate wsCoordinate = new WsCoordinate();
        wsCoordinate.setX(x);
        wsCoordinate.setY(y);
        return getField(wsCoordinate);
    }

    public Field getFieldFromUnit(int unitId, WsDirection wsDirection) {
        return getField(Coordinating.getNextCoordinate(
                getUnit(unitId).getCoordinate(), wsDirection));
    }

    public boolean isUnitOnShuttle(int unit) {
        return getUnit(unit).getCoordinate().equals(spaceShuttlePos);
    }

    public boolean isReallyFirstTick() {
        for (Integer unitId : getMyUnitIds()) {
            if (!isUnitOnShuttle(unitId)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasUnitOnShuttle() {
        for (Integer unitId : getMyUnitIds()) {
            if (isUnitOnShuttle(unitId)) {
                return true;
            }
        }
        return false;
    }

    public WsDirection getDirectionFromShuttle() {
        return Coordinating.getDirection(spaceShuttlePos, spaceShuttleExitPos);
    }

    public Field getSpaceShuttleExitField() {
        return knownCoordinates.get(spaceShuttleExitPos);
    }

    Set<Integer> getMyUnitIds() {
        return new TreeSet<>(ourUnits.keySet());
    }

    private List<WsDirection> getDirectionsWithCondition(WsCoordinate wsCoordinate, Predicate<Field> predicate) {
        List<WsDirection> result = new ArrayList<>();
        for (WsDirection wsDirection : WsDirection.values()) {
            if (predicate.test(knownCoordinates.get(Coordinating.getNextCoordinate(wsCoordinate, wsDirection)))) {
                result.add(wsDirection);
            }
        }

        return result;
    }

    public List<WsDirection> getKnownSteppableDirections(WsCoordinate wsCoordinate) {
        return getDirectionsWithCondition(wsCoordinate, (Field field) -> field != null && field.isSteppable());
    }

    public List<WsDirection> getKnownTunnelableDirections(WsCoordinate wsCoordinate) {
        return getDirectionsWithCondition(wsCoordinate, (Field field) -> field != null && field.isTunnelable());
    }

    public List<WsDirection> getKnownExplodableDirections(WsCoordinate wsCoordinate) {
        return getDirectionsWithCondition(wsCoordinate, (Field field) -> field != null && field.isExpodable());
    }

    public List<WsCoordinate> getUnknownCoordinates(WsCoordinate from, int maxDistance) {
        if (maxDistance > 3) {
            System.out.println("Max distance set bigger than 3 - reduce to 3");
            maxDistance = 3;
        }
        return Coordinating.getCoordinatesToRadius(from, maxDistance).stream()
                .filter((WsCoordinate wsCoordinate) -> !knownCoordinates.containsKey(wsCoordinate))
                .filter((WsCoordinate wsCoordinate) -> 0 <= wsCoordinate.getX() && wsCoordinate.getX() < mapSize.getX()
                        && 0 <= wsCoordinate.getY() && wsCoordinate.getY() < mapSize.getY())
                .collect(Collectors.toList());
    }

    public List<WsCoordinate> getCoordinatesCanRadar(int unitId, Predicate<Field> predicate, Comparator<WsCoordinate> comparator, int max) {
        WsCoordinate unitCoordinates = getUnit(unitId).getCoordinate();
        return Coordinating.getBoxCoordinates(unitCoordinates, 3).stream()
                .filter((WsCoordinate wsCoordinate) -> 0 <= wsCoordinate.getX() && wsCoordinate.getX() < mapSize.getX()
                        && 0 <= wsCoordinate.getY() && wsCoordinate.getY() < mapSize.getY())
                .filter((WsCoordinate wsCoordinate) -> !wsCoordinate.equals(unitCoordinates))
                .filter((WsCoordinate wsCoordinate) -> predicate.test(knownCoordinates.get(wsCoordinate)))
                .sorted(comparator)
                .limit(max)
                .collect(Collectors.toList());
    }

    void print() {
        print(System.out);
    }

    void print(PrintStream out) {
        out.println(GameStatus.get());
        for (int y = mapSize.getY() - 1; y >= 0; --y) {
            for (int x = 0; x < mapSize.getX(); ++x) {
                Field field = getField(x, y);

                if (field != null) {
                    if (field.isControllable()) {
                        for (java.util.Map.Entry entry : ourUnits.entrySet()) {
                            if (((Field) entry.getValue()).getCoordinate().equals(field.getCoordinate())) {
                                out.print(entry.getKey());
                                break;
                            }
                        }
                    } else if (field.isSteppable()) {
                        out.print(' ');
                    } else {
                        out.print(field.getScouting().getObject().value().charAt(0));
                    }
                } else {
                    out.print('?');
                }
            }
            out.println();
        }
    }

    public List<Command> getNextCommands(Commands commands) {
        return commands.getNextStep().stream()
                .filter((Command command) -> {
                    command.setCommandType(Command.getType(getField(command.getAffectCoordinate())));
                    return command.isValid();
                })
                .collect(Collectors.toList());
    }

    public List<Commands> getAllShortKnowDestination(int unitId) {
        HashSet<WsCoordinate> coordinates = new HashSet<>();

        List<Commands> result = new ArrayList<>();

        Queue<Commands> queue = new PriorityQueue<>((Commands c1, Commands c2) -> c1.getCost().compareTo(c2.getCost()));
        queue.add(new Commands(getUnit(unitId).getCoordinate()));

        while (queue.size() > 0) {
            Commands now = queue.remove();

            if (coordinates.contains(now.getLastAffectingCoordinate())) {
                continue;
            }

            coordinates.add(now.getLastAffectingCoordinate());

            if (!now.lastCommandIsNotMove()) {
                getNextCommands(now).stream()
                        .filter(nextCommand -> !coordinates.contains(nextCommand.getAffectCoordinate()))
                        .forEach(nextCommand -> {
                            Commands newCommands = new Commands(now);
                            newCommands.addCommand(nextCommand);
                            queue.add(newCommands);
                        });
            } else {
                if (now.getLastType() == Command.Type.Explode &&
                        getField(now.getLastAffectingCoordinate()).isGranite()) {
                    Command c = new Command(now.getLastStandingCoordinate(), now.getLastDirection());
                    c.setCommandType(Command.Type.Tunnel);
                    now.addCommand(c);
                }
                result.add(now);
            }
        }
        return result;
    }

    public void hackingEnemyBuilderUnits() {
        knownCoordinates.values().stream()
                .filter(f -> f.isEnemyBuilder())
                .forEach(f -> f.getScouting().setObject(ObjectType.TUNNEL));
    }
}