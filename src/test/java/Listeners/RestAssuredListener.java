package Listeners;

import endpoints.Accounts;
import endpoints.Transfers;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class RestAssuredListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        /*----- Transfer -----*/
        Transfers.host = System.getProperty("hostTransfer");
        Transfers.buildEndPoints();
        /*----- Accounts -----*/
        Accounts.host = System.getProperty("hostAccount");
        Accounts.buildEndPoints();
    }
}
