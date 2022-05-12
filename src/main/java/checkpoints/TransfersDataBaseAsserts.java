package checkpoints;

import org.testng.Assert;
import hibernate.AccountDao;
import models.Account;

public class TransfersDataBaseAsserts {

    public void dataBaseAllFields(Account inputAccount) {
        AccountDao accountDao = new AccountDao();
        String defaultMsj = "THERE WAS A FAILURE IN THE DATA BASE VALIDATIONS: ";
        Account dataBaseAccount = accountDao.getAccountByAccountId(inputAccount.getAccount_id());
        Assert.assertEquals(defaultMsj, dataBaseAccount.getCurrency(), inputAccount.getCurrency());
        Assert.assertEquals(defaultMsj, dataBaseAccount.getLedger_account_id(), inputAccount.getLedger_account_id());
        Assert.assertEquals(defaultMsj, dataBaseAccount.getStatus(), inputAccount.getStatus());
        Assert.assertEquals(defaultMsj, dataBaseAccount.getUser_id(),inputAccount.getUser_id());
        System.out.println("PASÓ LAS VALIDACIONES EN BBDD");
        //PUT AN OK VALIDATION MESSAGE HERE FOR THE REPORT
    }

    public void dataBaseExistingUserId(String account_id, String user_id) {
        AccountDao accountDao = new AccountDao();
        String defaultMsj = "THERE WAS A FAILURE IN THE DATA BASE VALIDATIONS: ";
        Account dataBaseAccount = accountDao.getAccountByAccountId(Long.parseLong(account_id));
        Assert.assertEquals(defaultMsj, dataBaseAccount.getUser_id(), user_id);
        //PUT AN OK VALIDATION MESSAGE HERE FOR THE REPORT
    }
}
