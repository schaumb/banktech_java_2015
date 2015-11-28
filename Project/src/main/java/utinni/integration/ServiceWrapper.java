package utinni.integration;

import eu.loxon.centralcontrol.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Wraps the {@link CentralControl} and extend it with utility functions.
 * Use the {@link #create(CentralControl)} to initialze, then you can use the
 * {@link #get()}.
 */
public class ServiceWrapper {

    private static final ServiceWrapper INSTANCE = new ServiceWrapper();

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

    private ServiceWrapper() {
    }

    public static void create(CentralControl control) {
        if (INSTANCE.control == null) {
            INSTANCE.control = control;
        } else {
            throw new IllegalStateException("The ServiceWrapper already created!");
        }
    }

    public static ServiceWrapper get() {
        return INSTANCE;
    }

    private void printBean(Object bean, boolean isRequest) {
        System.out.println((isRequest ? "Request" : "Response") + " at:"
                + DATE_FORMAT.format(new Date()) + " " + bean.toString());
    }

    private Object callService(Service service, Object request) {
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
                throw new IllegalArgumentException("Unknown service: " + service);
        }
        printBean(response, false);
        return response;
    }

    public ActionCostResponse getActionCost() {
        return (ActionCostResponse) callService(Service.ACTION_COST, new ActionCostRequest());
    }

    public ExplodeCellResponse explodeCell(ExplodeCellRequest request) {
        return (ExplodeCellResponse) callService(Service.EXPLODE_CELL, request);
    }

    public GetSpaceShuttleExitPosResponse getSpaceShuttleExitPos() {
        return (GetSpaceShuttleExitPosResponse)
                callService(Service.GET_SHUTTLE_EXIT_POS, new GetSpaceShuttleExitPosRequest());
    }

    public GetSpaceShuttlePosResponse getSpaceShuttlePos() {
        return (GetSpaceShuttlePosResponse)
                callService(Service.GET_SHUTTLE_POS, new GetSpaceShuttlePosRequest());
    }

    public IsMyTurnResponse isMyTurn() {
        return (IsMyTurnResponse) callService(Service.IS_MY_TURN, new IsMyTurnRequest());
    }

    public MoveBuilderUnitResponse moveBuilder(MoveBuilderUnitRequest request) {
        return (MoveBuilderUnitResponse) callService(Service.MOVE_BUILDER, request);
    }

    public RadarResponse radar(RadarRequest request) {
        return (RadarResponse) callService(Service.RADAR, request);
    }

    public StartGameResponse startGame() {
        return (StartGameResponse) callService(Service.START_GAME, new StartGameRequest());
    }

    public StructureTunnelResponse structureTunnel(StructureTunnelRequest request) {
        return (StructureTunnelResponse) callService(Service.STRUCTURE_TUNNEL, request);
    }

    public WatchResponse watch(WatchRequest request) {
        return (WatchResponse) callService(Service.WATCH, request);
    }
}
