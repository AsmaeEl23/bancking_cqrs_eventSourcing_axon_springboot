package ma.sdia.comptecqrses.commands.aggregates;

import ma.sdia.comptecqrses.commonApi.commands.CreateAccountCommand;
import ma.sdia.comptecqrses.commonApi.commands.CreditAccountCommand;
import ma.sdia.comptecqrses.commonApi.commands.DebitAccountCommand;
import ma.sdia.comptecqrses.commonApi.enums.AccountStatus;
import ma.sdia.comptecqrses.commonApi.events.AccountActivatedEvent;
import ma.sdia.comptecqrses.commonApi.events.AccountCreatedEvent;
import ma.sdia.comptecqrses.commonApi.events.AccountCreditedEvent;
import ma.sdia.comptecqrses.commonApi.events.AccountDebitedEvent;
import ma.sdia.comptecqrses.commonApi.exceptions.AmountNegativeException;
import ma.sdia.comptecqrses.commonApi.exceptions.BalanceNotSufficientException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

//aggregate pour exicuter la logique meties / cree une commande handler
@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;//the id identify the aggregate //in baseCommand class we have the id who will represent the accountId we used aggregate to do that
    private double balance;
    private String currency;
    private AccountStatus status;
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
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED
        ));
    }
    //evolution function
    //if we have the event this function will execute
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId=event.getId();
        this.currency= event.getCurrency();
        this.balance= event.getInitialBalance();
        this.status= AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.ACTIVATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.status=event.getStatus();
    }

    //Credit
    @CommandHandler
    public void handle(CreditAccountCommand command){
        if (command.getAmount()<0)throw new AmountNegativeException("Amount should not be negative!!");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency(),
                new Date()));
    }
    //it will be executed if we called a AccountCreditEvent class
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance+= event.getAmount();
    }
    // Debit __________________________

    @CommandHandler
    public void handle(DebitAccountCommand command){
        if (command.getAmount()<0)throw new AmountNegativeException("Amount should not be negative!!");
        if (this.balance<command.getAmount()) throw new BalanceNotSufficientException("Balance not sufficient Exception => "+balance);
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency(),
                new Date()));
    }
    //it will be executed if we called a DebitAccountCommand class
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.balance-= event.getAmount();
    }
}
