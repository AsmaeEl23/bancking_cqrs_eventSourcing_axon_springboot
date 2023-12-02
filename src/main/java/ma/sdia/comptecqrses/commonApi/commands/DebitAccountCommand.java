package ma.sdia.comptecqrses.commonApi.commands;

import lombok.Getter;

public class DebitAccountCommand extends  BaseCommand<String>{
    @Getter private double amount;
    @Getter private String currency; //dh or $ ...
    public DebitAccountCommand(String id, double amount, String currency) {
        super(id);
        this.currency=currency;
        this.amount = amount;
    }
}
