package ma.sid.comptecqrseventsourcing.commonapi.events;

import lombok.Getter;

import java.util.Date;

@Getter
public class AccountDebitedEvent extends BaseEvent<String>{

    private double amount;
    private String currency;
    private Date date;

    public AccountDebitedEvent(String id, double amount, String currency, Date date) {
        super(id);
        this.amount = amount;
        this.currency = currency;
        this.date = date;
    }
}
