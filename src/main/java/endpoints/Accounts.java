package endpoints;

public class Accounts {
    public static String host = "";
    public static String accountApi = "/accounts/";
    /*----- GET -----*/
    public static String getUserById = "";

    public static void buildEndPoints(){
        getUserById = host.concat(accountApi).concat("%d");
    }
}
