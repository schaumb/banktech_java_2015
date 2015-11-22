package utinni.logic;

public class StrategyObserver {

    private static StrategyObserver instance = new StrategyObserver();

    public static StrategyObserver get() {
        return instance;
    }

    private StrategyObserver() {}

    int canMove = 0;
    int tunnelMake = 0;
    int tunnelLost = 0;
    int move = 0;
    int explode = 0;
    int radarCount = 0;
    int radarField = 0;
    int watchCount = 0;
    int lostBonusTurnLeft = -1;
    int badRequest = 0;

    @Override
    public String toString() {
        int usedActionPoints =
                tunnelMake * Logic.actionCostResponse.getDrill() +
                move * Logic.actionCostResponse.getMove() +
                explode * Logic.actionCostResponse.getExplode() +
                radarField * Logic.actionCostResponse.getRadar() +
                watchCount * Logic.actionCostResponse.getWatch();
        return "Can move: " + canMove + "\n"
                + "Can use action points: " + canMove * 14 + "\n"
                + "Used action points: " + usedActionPoints + "\n"
                + "Not used: " + (canMove * 14 - usedActionPoints) + "\n"
                + "Tunnel make: " + tunnelMake + "\n"
                + "Tunnel lost: " + tunnelLost + "\n"
                + "Move: " + move + "\n"
                + "Explode: " + explode + "\n"
                + "Radar: " + radarCount + "\n"
                + "Radared fields: " + radarField + "\n"
                + "Watch: " + watchCount + "\n"
                + "When lost bonus: " + lostBonusTurnLeft + "\n";
    }
}
