package ma.sdia.comptecqrses.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.sdia.comptecqrses.commonApi.enums.AccountStatus;
import ma.sdia.comptecqrses.commonApi.enums.OperationType;
import ma.sdia.comptecqrses.commonApi.events.*;
import ma.sdia.comptecqrses.commonApi.queries.GetAccountByIdQuery;
import ma.sdia.comptecqrses.commonApi.queries.GetAllAccountsQuery;
import ma.sdia.comptecqrses.query.entities.Account;
import ma.sdia.comptecqrses.query.entities.Operation;
import ma.sdia.comptecqrses.query.repositories.AccountRepository;
import ma.sdia.comptecqrses.query.repositories.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
@Transactional //if we update an account we don't need to save it, it will update by this
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    //@EventSourcingHandler is just for aggregate so here we gonna use eventhandler
    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("*********************************");
        log.info("AccountCreatedEvent received");
        Account account=new Account();
        account.setId(event.getId());
        account.setCurrency(event.getCurrency());
        account.setBalance(event.getInitialBalance());
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("*********************************");
        log.info("AccountCreditedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        account.setStatus(AccountStatus.ACTIVATED);
        //No need to add save here
        //accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("*********************************");
        log.info("AccountCreditedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation=new Operation();
        operation.setCreatedAt(event.getCreditedDate());
        operation.setAmount(event.getAmount());
        operation.setAccount(account);
        operation.setType(OperationType.CREDIT);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()+event.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("*********************************");
        log.info("AccountDebitedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation=new Operation();
        operation.setCreatedAt(event.getCreditedDate());
        operation.setAmount(event.getAmount());
        operation.setAccount(account);
        operation.setType(OperationType.DEBIT);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()-event.getAmount());
        accountRepository.save(account);
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }
    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        return accountRepository.findById(query.getId()).get();
    }
}
