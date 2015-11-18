package utinni.logic;

import eu.loxon.centralcontrol.*;

public class Logic {

    private CentralControl centralControl;

    public Logic(CentralControl centralControl) {
        this.centralControl = centralControl;
    }

    public void run() {
        // TODO make the application logic here
        StartGameResponse startGameResponse = centralControl.startGame(new StartGameRequest());
        /*System.out.println(startGameResponse.getSize().getX());
        System.out.println(startGameResponse.getSize().getY());
        System.out.println(startGameResponse.getResult().getScore().getTotal());
        System.out.println(startGameResponse.getResult().getBuilderUnit());
        GetSpaceShuttlePosResponse getSpaceShuttlePosResponse =
                centralControl.getSpaceShuttlePos(new GetSpaceShuttlePosRequest());
        System.out.println(getSpaceShuttlePosResponse.getCord().getX());
        System.out.println(getSpaceShuttlePosResponse.getCord().getY());
        GetSpaceShuttleExitPosResponse getSpaceShuttleExitPosResponse = centralControl.getSpaceShuttleExitPos(new GetSpaceShuttleExitPosRequest());
        System.out.println(getSpaceShuttleExitPosResponse.getCord().getX());
        System.out.println(getSpaceShuttleExitPosResponse.getCord().getY());*/
        System.out.println(startGameResponse.getUnits().size());
        for (WsBuilderunit builderUnit : startGameResponse.getUnits()) {
            System.out.println("unit id at start: " + builderUnit.getUnitid());
        }
        /*for (int i = 0; i < 100; ++i) {
            int actionCostResponse = centralControl.getActionCost(new ActionCostRequest()).getResult().getBuilderUnit();
            System.out.println(actionCostResponse);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/


        int starUnit = 0;//centralControl.isMyTurn(new IsMyTurnRequest()).getResult().getBuilderUnit();
        int j = 0;
        for (int i = 0; i < 10000; i++) {
            try {
                Thread.sleep(301L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            IsMyTurnResponse isMyTurnResponse = centralControl.isMyTurn(new IsMyTurnRequest());
            if (!isMyTurnResponse.isIsYourTurn() || isMyTurnResponse.getResult().getBuilderUnit() != starUnit || isMyTurnResponse.getResult().getActionPointsLeft() <= 4) {
                continue;
            }
            StructureTunnelRequest structureTunnelRequest = new StructureTunnelRequest();
            structureTunnelRequest.setUnit(starUnit);
            structureTunnelRequest.setDirection(j % 2 == 0 ? WsDirection.RIGHT : WsDirection.DOWN);
            StructureTunnelResponse structureTunnelResponse = centralControl.structureTunnel(structureTunnelRequest);
            System.out.println("structureTunnel: " + structureTunnelResponse.getResult().getType());
            System.out.println("structureTunnel: " + structureTunnelResponse.getResult().getMessage());
            System.out.println("getReward: " + structureTunnelResponse.getResult().getScore().getReward());
            System.out.println("getBonus: " + structureTunnelResponse.getResult().getScore().getBonus());
            System.out.println("getPenalty: " + structureTunnelResponse.getResult().getScore().getPenalty());
            System.out.println("getTotal: " + structureTunnelResponse.getResult().getScore().getTotal());

            MoveBuilderUnitRequest moveBuilderUnitRequest = new MoveBuilderUnitRequest();
            moveBuilderUnitRequest.setUnit(starUnit);
            moveBuilderUnitRequest.setDirection(j % 2 == 0 ? WsDirection.RIGHT : WsDirection.DOWN);
            MoveBuilderUnitResponse moveBuilderUnitResponse = centralControl.moveBuilderUnit(moveBuilderUnitRequest);
            System.out.println("move: " + moveBuilderUnitResponse.getResult().getType());
            System.out.println("move: " + moveBuilderUnitResponse.getResult().getMessage());
            System.out.println("getActionPointsLeft: " + moveBuilderUnitResponse.getResult().getActionPointsLeft());
            System.out.println("getBuilderUnit: " + moveBuilderUnitResponse.getResult().getBuilderUnit());

            WatchRequest watchRequest = new WatchRequest();
            watchRequest.setUnit(starUnit);
            WatchResponse watchResponse = centralControl.watch(watchRequest);
            System.out.println("watchResponse: " + watchResponse.getResult().getType());
            System.out.println("watchResponse: " + watchResponse.getResult().getMessage());
            for (Scouting scouting : watchResponse.getScout()) {
                System.out.println("scouting.getCord().getX(): " + scouting.getCord().getX());
                System.out.println("scouting.getCord().getY(): " + scouting.getCord().getY());
                System.out.println("scouting.getTeam(): " + scouting.getTeam());
                System.out.println("scouting.getObject(): " + scouting.getObject());
            }

            System.out.println();

            ++j;
        }

        /*for (int i = 0; i < 100; ++i) {
            IsMyTurnResponse isMyTurnResponse = centralControl.isMyTurn(new IsMyTurnRequest());
            System.out.println("isIsYourTurn(): " + isMyTurnResponse.isIsYourTurn());
            System.out.println("getBuilderUnit(): " + isMyTurnResponse.getResult().getBuilderUnit());
            System.out.println("getActionPointsLeft(): " + isMyTurnResponse.getResult().getActionPointsLeft());
            System.out.println("getTotal(): " + isMyTurnResponse.getResult().getScore().getTotal());

            ActionCostResponse actionCostResponse = centralControl.getActionCost(new ActionCostRequest());
            System.out.println("getDrill(): " + actionCostResponse.getDrill());
            System.out.println("getExplode(): " + actionCostResponse.getExplode());
            System.out.println("getAvailableExplosives(): " + actionCostResponse.getAvailableExplosives());

            RadarRequest radarRequest = new RadarRequest();
            radarRequest.setUnit(isMyTurnResponse.getResult().getBuilderUnit());
            for (int j = 2; j <= 4; ++j) {
                for (int k = 15; k <= 17; ++k) {
                    WsCoordinate wsCoordinate = new WsCoordinate();
                    wsCoordinate.setX(j);
                    wsCoordinate.setY(k);
                    radarRequest.getCord().add(wsCoordinate);
                }
            }
            RadarResponse radarResponse = centralControl.radar(radarRequest);
            System.out.println("radarResponse.getCode(): " + radarResponse.getResult().getType());
            System.out.println("radarResponse.getMessage(): " + radarResponse.getResult().getMessage());
            for (Scouting scouting : radarResponse.getScout()) {
                System.out.println("scouting.getCord().getX(): " + scouting.getCord().getX());
                System.out.println("scouting.getCord().getY(): " + scouting.getCord().getY());
                System.out.println("scouting.getTeam(): " + scouting.getTeam());
                System.out.println("scouting.getObject(): " + scouting.getObject());
            }

            System.out.println();

            try {
                Thread.sleep(301L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
