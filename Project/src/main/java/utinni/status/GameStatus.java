package utinni.status;

import eu.loxon.centralcontrol.*;
import utinni.Config;
import utinni.integration.ServiceWrapper;

import java.util.List;

/**
 * The singleton which maintain our status.
 */
public class GameStatus {

    private static GameStatus instance;

    private GameStatus() {
    }

    public static GameStatus get() {
        if (instance == null) {
            instance = new GameStatus();
        }
        return instance;
    }

    public String userName;
    public int availableActionPoints = -1;
    public int availableExplosives = -1;
    public List<WsBuilderunit> builderUnits;
    public ActionCostResponse actionCostResponse; // TODO Temporary.

    // TODO Map related things, we should remove after the Map refactor.
    public WsCoordinate mapSize;
    public WsCoordinate shuttlePos;
    public WsCoordinate shuttleExitPos;

    public int tick = -1;
    public boolean isOurTurn;
    public WsScore score;
    public int builderUnit = Integer.MIN_VALUE;
    public int actionPointsLeft = -1;
    public int explosionsLeft = -1;
    public int tunnelsLost = 0;
    public int bonusLostAtFinish = 0;
    public int lastLostBonusTick = -1;

    public void updateFromCommonResponse(CommonResp commonResponse) {
        tick = commonResponse.getTurnsLeft();
        builderUnit = commonResponse.getBuilderUnit();
        actionPointsLeft = commonResponse.getActionPointsLeft();
        explosionsLeft = commonResponse.getExplosivesLeft();

        if (score != null && score.getBonus() != 0
                && commonResponse.getScore().getBonus() == 0) {

            if (lastLostBonusTick != tick
                    && Config.LOST_BONUS_AT_FINISH_START_TICK >= tick) {
                ++bonusLostAtFinish;
            }

            lastLostBonusTick = tick;
        }

        if (score != null) {
            long tunnelsLostNow = (score.getReward() - commonResponse.getScore().getReward())
                    / Config.REWARD_FOR_A_TUNNEL;
            if (tunnelsLostNow > 0) {
                tunnelsLost += tunnelsLostNow;
            }
        }

        score = commonResponse.getScore();
    }

    @Override
    public String toString() {
        return "\nTurn left: " + tick +
                "\nIs our turn: " + isOurTurn +
                "\nBuilder: " + builderUnit +
                "\nAction points left: " + actionPointsLeft +
                "\nExplosions left: " + explosionsLeft +
                "\nTunnels lost: " + tunnelsLost +
                "\nScore: " + score +
                "\nBonus lost at finish: " + bonusLostAtFinish +
                "\nLast lost tick: " + lastLostBonusTick +
                ServiceWrapper.get();
    }
}
