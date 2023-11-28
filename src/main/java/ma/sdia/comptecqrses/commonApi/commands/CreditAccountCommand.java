package ma.sdia.comptecqrses.commonApi.commands;

public class CreditAccountCommand extends  BaseCommand<String>{
    private double amount;
    private String currency; //dh or $ ...
    public CreditAccountCommand(String id, double amount, String currency) {
        super(id);
        this.currency=currency;
        this.amount = amount;
    }
}
