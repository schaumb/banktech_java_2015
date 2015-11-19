package utinni;

import eu.loxon.centralcontrol.CentralControl;
import eu.loxon.centralcontrol.CentralControlServiceService;
import utinni.logic.Logic;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

public class App {


    static public URL wsUrl;
    static public String user;
    static public String password;
    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            System.err.println("Not enough argument.");
            return;
        }

        try {
            wsUrl = new URL(args[0]);
        } catch (MalformedURLException e) {
            System.err.println("The first param is not a valid URL.");
            return;
        }
        user = args[1];
        password = args[2];

        CentralControl centralControl = createCentralControlWithCredentials(wsUrl, user, password);
        new Logic(centralControl).run();
    }

    private static CentralControl createCentralControlWithCredentials(URL wsUrl, final String user, final String pw) {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pw.toCharArray());
            }
        });

        return new CentralControlServiceService(wsUrl).getCentralControlPort();
    }
}
