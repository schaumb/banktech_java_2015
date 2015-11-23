package utinni.logic;

public class StrategyObserver {

    private static StrategyObserver instance = new StrategyObserver();

    public static StrategyObserver get() {
        return instance;
    }

    private StrategyObserver() {}

    public static final int LOST_BONUS_AT_FINISH_START_TICK = 20;

    int canMove = 0;
    int tunnelMake = 0;
    int tunnelLost = 0;
    int move = 0;
    int explode = 0;
    int radarCount = 0;
    int radarField = 0;
    int watchCount = 0;
    int lostBonusAtFinish = 0;
    int lostBonusTurnLeft = -1;
    int badRequest = 0;
    int actionPoint = -1;

    @Override
    public String toString() {
        int usedActionPoints =
                tunnelMake * Logic.actionCostResponse.getDrill() +
                move * Logic.actionCostResponse.getMove() +
                explode * Logic.actionCostResponse.getExplode() +
                radarField * Logic.actionCostResponse.getRadar() +
                watchCount * Logic.actionCostResponse.getWatch();
        return "Action point: " + actionPoint + "\n"
                + "Can move: " + canMove + "\n"
                + "Can use action points: " + canMove * actionPoint + "\n"
                + "Used action points: " + usedActionPoints + "\n"
                + "Not used: " + (canMove * actionPoint - usedActionPoints) + "\n"
                + "Tunnel make: " + tunnelMake + "\n"
                + "Tunnel lost: " + tunnelLost + "\n"
                + "Move: " + move + "\n"
                + "Explode: " + explode + "\n"
                + "Radar: " + radarCount + "\n"
                + "Radared fields: " + radarField + "\n"
                + "Watch: " + watchCount + "\n"
                + "Lost bonus at finish: " + lostBonusAtFinish + "\n"
                + "Last time when lost bonus: " + lostBonusTurnLeft + "\n"
                + "Bad requests: " + badRequest + "\n";
    }
}
