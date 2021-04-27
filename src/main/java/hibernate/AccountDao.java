package hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Account;


public class AccountDao {

    public Account getAccountByAccountId(long id) {
        Transaction transaction = null;
        Account account = new Account();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            account = session.get(Account.class, id);
            transaction.commit();
            session.close();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return account;
    }
}
