package utinni.integration;

import eu.loxon.centralcontrol.*;
import utinni.status.GameStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Wraps the {@link CentralControl} and extend it with utility functions.
 * Use the {@link #create(CentralControl)} to initialize, then you can use the
 * {@link #get()}.
 */
public class ServiceWrapper {

    private static ServiceWrapper instance;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");

    private CentralControl control;

    private enum Service {
        ACTION_COST,
        EXPLODE_CELL,
        GET_SHUTTLE_POS,
        GET_SHUTTLE_EXIT_POS,
        IS_MY_TURN,
        MOVE_BUILDER,
        RADAR,
        START_GAME,
        STRUCTURE_TUNNEL,
        WATCH
    }

    private Map<Service, Integer> doneCount = new EnumMap<>(Service.class);
    private Map<Service, Integer> invalidCount = new EnumMap<>(Service.class);
    private Map<Service, Integer> errorCount = new EnumMap<>(Service.class);
    private Map<Service, Integer> costs;

    private ServiceWrapper(CentralControl control) {
        this.control = control;
        for (Service service : Service.values()) {
            doneCount.put(service, 0);
            invalidCount.put(service, 0);
            errorCount.put(service, 0);
        }
    }

    public static void create(CentralControl control) {
        if (instance == null) {
            instance = new ServiceWrapper(control);
        } else {
            System.out.println("[ERROR] + The ServiceWrapper already created!");
        }
    }

    public static ServiceWrapper get() {
        return instance;
    }

    private void printBean(Object bean, boolean isRequest) {
        System.out.println((isRequest ? "Request" : "Response") + " at:"
                + DATE_FORMAT.format(new Date()) + " " + bean.toString());
    }

    private Object callService(Service service, Object request) {
        if (costs != null && costs.containsKey(service)
                && costs.get(service) > GameStatus.get().actionPointsLeft) {
            StringBuilder message = new StringBuilder("[WARNING] Failed to start service:");
            message.append(service).append(". Service cost: ")
                    .append(costs.get(service)).append(", action points left:")
                    .append(GameStatus.get().actionPointsLeft).append(".\n");
            System.out.println(message);
        }

        Object response;
        printBean(request, true);
        switch (service) {
            case ACTION_COST:
                response = control.getActionCost((ActionCostRequest) request);
                break;
            case EXPLODE_CELL:
                response = control.explodeCell((ExplodeCellRequest) request);
                break;
            case GET_SHUTTLE_EXIT_POS:
                response = control.getSpaceShuttleExitPos((GetSpaceShuttleExitPosRequest) request);
                break;
            case GET_SHUTTLE_POS:
                response = control.getSpaceShuttlePos((GetSpaceShuttlePosRequest) request);
                break;
            case IS_MY_TURN:
                response = control.isMyTurn((IsMyTurnRequest) request);
                break;
            case MOVE_BUILDER:
                response = control.moveBuilderUnit((MoveBuilderUnitRequest) request);
                break;
            case RADAR:
                response = control.radar((RadarRequest) request);
                break;
            case START_GAME:
                response = control.startGame((StartGameRequest) request);
                break;
            case STRUCTURE_TUNNEL:
                response = control.structureTunnel((StructureTunnelRequest) request);
                break;
            case WATCH:
                response = control.watch((WatchRequest) request);
                break;
            default:
                throw new IllegalArgumentException("[WARNING] Unknown service: " + service);
        }
        printBean(response, false);
        return response;
    }

    private void updateCount(Service service, CommonResp commonResp) {
        switch (commonResp.getType()) {
            case DONE:
                doneCount.compute(service, (key, value) -> ++value);
                break;
            case INVALID:
                System.out.println("[ERROR] " + service + " was INVALID: " + commonResp.getMessage());
                invalidCount.compute(service, (key, value) -> ++value);
                break;
            case ERROR:
                System.out.println("[ERROR] " + service + " finished with ERROR: " + commonResp.getMessage());
                errorCount.compute(service, (key, value) -> ++value);
                break;
        }
    }

    private void updateCosts(ActionCostResponse response) {
        costs = new EnumMap<>(Service.class);
        costs.put(Service.EXPLODE_CELL, response.getExplode());
        costs.put(Service.MOVE_BUILDER, response.getMove());
        costs.put(Service.RADAR, response.getRadar());
        costs.put(Service.STRUCTURE_TUNNEL, response.getDrill());
        costs.put(Service.WATCH, response.getWatch());
    }

    /**
     * @return If the server respond {@link ResultType#DONE}.
     */
    public boolean getActionCost() {
        ActionCostResponse response = (ActionCostResponse)
                callService(Service.ACTION_COST, new ActionCostRequest());

        updateCount(Service.ACTION_COST, response.getResult());
        GameStatus.get().updateFromCommonResponse(response.getResult());
        GameStatus.get().availableActionPoints = response.getAvailableActionPoints();
        GameStatus.get().availableExplosives = response.getAvailableExplosives();
        GameStatus.get().actionCostResponse = response; // TODO Temporary.
        updateCosts(response);

        return response.getResult().getType() == ResultType.DONE;
    }

    /**
     * @return If the server respond {@link ResultType#DONE}.
     */
    public boolean explodeCell(int unit, WsDirection direction) {
        ExplodeCellRequest request = new ExplodeCellRequest(unit, direction);
        ExplodeCellResponse response = (ExplodeCellResponse) callService(Service.EXPLODE_CELL, request);

        updateCount(Service.EXPLODE_CELL, response.getResult());
        GameStatus.get().updateFromCommonResponse(response.getResult());

        return response.getResult().getType() == ResultType.DONE;
    }

    /**
     * @return If the server respond {@link ResultType#DONE}.
     */
    public boolean getSpaceShuttleExitPos() {
        GetSpaceShuttleExitPosResponse response = (GetSpaceShuttleExitPosResponse)
                callService(Service.GET_SHUTTLE_EXIT_POS, new GetSpaceShuttleExitPosRequest());

        updateCount(Service.GET_SHUTTLE_EXIT_POS, response.getResult());
        GameStatus.get().updateFromCommonResponse(response.getResult());
        // TODO The map should handle this, not the status.
        GameStatus.get().shuttleExitPos = response.getCord();

        return response.getResult().getType() == ResultType.DONE;
    }

    /**
     * @return If the server respond {@link ResultType#DONE}.
     */
    public boolean getSpaceShuttlePos() {
        GetSpaceShuttlePosResponse response = (GetSpaceShuttlePosResponse)
                callService(Service.GET_SHUTTLE_POS, new GetSpaceShuttlePosRequest());

        updateCount(Service.GET_SHUTTLE_POS, response.getResult());
        GameStatus.get().updateFromCommonResponse(response.getResult());
        // TODO The map should handle this, not the status.
        GameStatus.get().shuttlePos = response.getCord();

        return response.getResult().getType() == ResultType.DONE;
    }

    /**
     * @return If it's our turn or not.
     */
    public boolean isMyTurn() {
        IsMyTurnResponse response = (IsMyTurnResponse)
                callService(Service.IS_MY_TURN, new IsMyTurnRequest());

        updateCount(Service.IS_MY_TURN, response.getResult());
        GameStatus.get().updateFromCommonResponse(response.getResult());
        GameStatus.get().isOurTurn = response.isIsYourTurn();

        return response.isIsYourTurn();
    }

    /**
     * @return If the server respond {@link ResultType#DONE}.
     */
    public boolean moveBuilder(int unit, WsDirection direction) {
        MoveBuilderUnitRequest request = new MoveBuilderUnitRequest(unit, direction);
        MoveBuilderUnitResponse response = (MoveBuilderUnitResponse)
                callService(Service.MOVE_BUILDER, request);

        updateCount(Service.MOVE_BUILDER, response.getResult());
        GameStatus.get().updateFromCommonResponse(response.getResult());

        return response.getResult().getType() == ResultType.DONE;
    }

    /**
     * @return The scouts.
     */
    public List<Scouting> radar(int unit, List<WsCoordinate> coordinates) {
        RadarRequest request = new RadarRequest(unit, coordinates);
        RadarResponse response = (RadarResponse) callService(Service.RADAR, request);

        updateCount(Service.RADAR, response.getResult());
        GameStatus.get().updateFromCommonResponse(response.getResult());

        // TODO We should update the map instead of return the coordinates.
        return response.getScout();
    }

    /**
     * @return If the server respond {@link ResultType#DONE}.
     */
    public boolean startGame() {
        StartGameResponse response = (StartGameResponse)
                callService(Service.START_GAME, new StartGameRequest());

        updateCount(Service.START_GAME, response.getResult());
        GameStatus.get().updateFromCommonResponse(response.getResult());
        GameStatus.get().builderUnits = response.getUnits();
        // TODO The map should handle this, not the status.
        GameStatus.get().mapSize = response.getSize();

        return response.getResult().getType() == ResultType.DONE;
    }

    /**
     * @return If the server respond {@link ResultType#DONE}.
     */
    public boolean structureTunnel(int unit, WsDirection direction) {
        StructureTunnelRequest request = new StructureTunnelRequest(unit, direction);
        StructureTunnelResponse response = (StructureTunnelResponse)
                callService(Service.STRUCTURE_TUNNEL, request);

        updateCount(Service.STRUCTURE_TUNNEL, response.getResult());
        GameStatus.get().updateFromCommonResponse(response.getResult());

        return response.getResult().getType() == ResultType.DONE;
    }

    /**
     * @return The scouts.
     */
    public List<Scouting> watch(int unit) {
        WatchRequest request = new WatchRequest(unit);
        WatchResponse response = (WatchResponse) callService(Service.WATCH, request);

        updateCount(Service.WATCH, response.getResult());
        GameStatus.get().updateFromCommonResponse(response.getResult());

        // TODO We should update the map instead of return the coordinates.
        return response.getScout();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\nServices count (if > 0):\n");

        result.append("DONE:\n");
        doneCount.forEach((service, count) -> {
            if (count > 0) {
                result.append(service).append(": ").append(count).append("\n");
            }
        });

        result.append("INVALID:\n");
        invalidCount.forEach((service, count) -> {
            if (count > 0) {
                result.append(service).append(": ").append(count).append("\n");
            }
        });

        result.append("ERROR:\n");
        invalidCount.forEach((service, count) -> {
            if (count > 0) {
                result.append(service).append(": ").append(count).append("\n");
            }
        });

        result.append("\n");
        return result.toString();
    }
}
