package utils;

import org.json.JSONObject;

public class CreateBodyContent {

    public static JSONObject createNewTransfer(){
        JSONObject newTransfer = new JSONObject();
        newTransfer
                .put("source_account_id",4)
                .put("source_user_id","user4@mail.com")
                .put("amount",1000)
                .put("currency","JMD")
                .put("target_account_id",3)
                .put("target_user_id","user3@mail.com")
                .put("comments", "QA Test");

        return newTransfer;
    }
}
