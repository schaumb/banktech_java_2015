package utinni;

import eu.loxon.centralcontrol.CentralControl;
import eu.loxon.centralcontrol.CentralControlServiceService;
import utinni.integration.ServiceWrapper;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

public class App {

    private static URL wsdlUrl;
    private static String userName;
    private static String password;

    public static void main(String[] args) {
        if (!processArgs(args)) {
            return;
        }

        ServiceWrapper.create(createCentralControlWithCredentials());

        // TODO
        // 0. pre-init the utils
        // 1. start the game
        // 2. init the utils
        // 3. start the logic chooser
    }

    private static boolean processArgs(String[] args) {
        if (args == null || args.length < 3) {
            System.err.println("[ERROR] Not enough argument.");
            return false;
        }

        try {
            wsdlUrl = new URL(args[0]);
        } catch (MalformedURLException e) {
            System.err.println("[ERROR] The first param is not a valid URL.");
            return false;
        }
        userName = args[1];
        password = args[2];

        return true;
    }

    private static CentralControl createCentralControlWithCredentials() {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password.toCharArray());
            }
        });

        return new CentralControlServiceService(wsdlUrl).getCentralControlPort();
    }

    public static String getMyUser() {
        return userName;
    }
}
