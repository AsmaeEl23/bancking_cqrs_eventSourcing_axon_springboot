package ma.sdia.comptecqrses.commands.aggregates;

import ma.sdia.comptecqrses.commonApi.commands.CreateAccountCommand;
import ma.sdia.comptecqrses.commonApi.enums.AccounteStatus;
import ma.sdia.comptecqrses.commonApi.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

//aggregate pour exicuter la logique meties / cree une commande handler
@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;//the id identify the aggregate //in baseCommand class we have the id who will represent the accountId we used aggregate to do that
    private double balance;
    private String currency;
    private AccounteStatus status;
    //its obligate

    public AccountAggregate() {
        //Required by AXON
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        //Required by AXON
        if (createAccountCommand.getInitialBalance()<0)throw new RuntimeException("Initial balance should be > 0 ");
        //OK
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency()
        ));
    }
    //evolution function
    //if we have the event this function will execute
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId=event.getId();
        this.currency= event.getCurrency();
        this.balance= event.getInitialBalance();
        this.status=AccounteStatus.CREATED;
    }

}
