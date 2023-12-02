package ma.sdia.comptecqrses.commonApi.events;

import lombok.Getter;

import java.util.Date;

public class AccountCreditedEvent extends BaseEvent<String>{
    @Getter private double amount;
    @Getter private String currency;
    @Getter private Date creditedDate;
    public AccountCreditedEvent(String id, double amount, String currency, Date creditedDate) {
        super(id);
        this.amount = amount;
        this.currency = currency;
        this.creditedDate = creditedDate;
    }
}
