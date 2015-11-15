package utinni.logic;

import eu.loxon.centralcontrol.CentralControl;
import eu.loxon.centralcontrol.StartGameRequest;

public class Logic {

    private CentralControl centralControl;

    public Logic(CentralControl centralControl) {
        this.centralControl = centralControl;
    }

    public void run() {
        // TODO make the application logic here
        System.out.println(centralControl.startGame(new StartGameRequest()).getResult().getType());
    }
}
