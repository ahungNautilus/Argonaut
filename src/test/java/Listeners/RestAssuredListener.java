package Listeners;

import org.json.JSONObject;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.io.File;

public class RestAssuredListener implements ISuiteListener{

    ClassLoader classLoader;
    File endpointFile;
    JSONObject endpoints;

    @Override
    public void onStart(ISuite suite)  {
        classLoader = getClass().getClassLoader();
        endpointFile = new File(classLoader.getResource("endpoints.json").getFile());
        endpoints = new JSONObject(endpointFile);

        /*----- Transfer -----*/
        //Transfers.host = System.getProperty("hostTransfer");
        //Transfers.buildEndPoints();
        /*----- Accounts -----*/
        //Accounts.host = System.getProperty("hostAccount");
        //Accounts.buildEndPoints();

    }
}
