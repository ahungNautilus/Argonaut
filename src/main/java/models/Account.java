package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private long account_id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "ledger_account_id")
    private String ledger_account_id;

    @Column(name = "status")
    private String status;

    @Column(name = "user_id")
    private String user_id;

    public Account() {
        super();
    }

    public Account(long account_id, String currency, String status, String ledger_account_id,  String user_id) {
        super();
        this.account_id = account_id;
        this.currency = currency;
        this.status = status;
        this.ledger_account_id = ledger_account_id;
        this.user_id = user_id;
    }


    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long id) {
        this.account_id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLedger_account_id() {
        return ledger_account_id;
    }

    public void setLedger_account_id(String ledger_account_id) {
        this.ledger_account_id = ledger_account_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
