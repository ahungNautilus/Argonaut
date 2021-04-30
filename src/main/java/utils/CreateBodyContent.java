package utils;

import org.json.JSONObject;

public class CreateBodyContent {

    public static JSONObject createNewTransfer(int source_account_id, String source_account_email, int amount,
                                               String currency, int target_account_id, String target_user_id, String comment){
        JSONObject newTransfer = new JSONObject();
        newTransfer
                .put("source_account_id", source_account_id)
                .put("source_user_id", source_account_email)
                .put("amount", amount)
                .put("currency", currency)
                .put("target_account_id", target_account_id)
                .put("target_user_id", target_user_id)
                .put("comments", comment);

        return newTransfer;
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
