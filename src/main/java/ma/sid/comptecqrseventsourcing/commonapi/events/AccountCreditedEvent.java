package ma.sid.comptecqrseventsourcing.commonapi.events;

import lombok.Getter;

import java.util.Date;
@Getter
public class AccountCreditedEvent extends BaseEvent<String>{

    private double amount;
    private String currency;
    private Date date;

    public AccountCreditedEvent(String id, double amount, String currency, Date date) {
        super(id);
        this.amount = amount;
        this.currency = currency;
        this.date = date;
    }
}
