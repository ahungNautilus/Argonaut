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

    public static JSONObject createNewEtop(String source_account_id, String source_user_id, String amount, String currency, String comments, String card_id, String reference){
        JSONObject etopBody = new JSONObject();
        etopBody.put("source_account_id",source_account_id)
                .put("source_user_id",source_user_id)
                .put("amount", amount)
                .put("currency",currency)
                .put("comments",comments)
                .put("card_id",card_id)
                .put("reference",reference)
                .put("transfer_type", "ETOP");
        return  etopBody;
    }

    public static JSONObject createNewAccount(String currency, String user_id, String ledger_account_id){
        JSONObject newAccount = new JSONObject();
        newAccount
                .put("currency",currency)
                .put("status","pending")
                .put("user_id",user_id)
                .put("ledger_account_id",ledger_account_id);

        return newAccount;
    }
}
