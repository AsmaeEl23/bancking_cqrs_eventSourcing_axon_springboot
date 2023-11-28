package ma.sdia.comptecqrses.commonApi.commands;

public class DebitAccountCommand extends  BaseCommand<String>{
    private double amount;
    private String currency; //dh or $ ...
    public DebitAccountCommand(String id, double amount, String currency) {
        super(id);
        this.currency=currency;
        this.amount = amount;
    }
}
