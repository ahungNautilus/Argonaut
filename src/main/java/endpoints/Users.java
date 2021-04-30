package endpoints;

public class Users {
    public static String host = "";
    public static String usersApi = "/accounts/";
    /*----- GET -----*/
    public static String getUserById = "";
    /*----- POST -----*/
    public static String createNewUser = "";

    public static void buildEndPoints(){
        getUserById = host.concat(usersApi).concat("%d");
        createNewUser = host.concat(usersApi);
    }
}
