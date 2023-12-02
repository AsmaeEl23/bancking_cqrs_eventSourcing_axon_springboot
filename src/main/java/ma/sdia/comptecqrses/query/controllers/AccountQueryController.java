package ma.sdia.comptecqrses.query.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.sdia.comptecqrses.commonApi.queries.GetAccountByIdQuery;
import ma.sdia.comptecqrses.commonApi.queries.GetAllAccountsQuery;
import ma.sdia.comptecqrses.query.entities.Account;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/query/account")
public class AccountQueryController {
    private QueryGateway queryGateway;
    @GetMapping("allAccounts")
    public List<Account> accountList(){
        return queryGateway.query(new GetAllAccountsQuery(),ResponseTypes.multipleInstancesOf(Account.class)).join();
    }
    @GetMapping("{id}")
    public Account accountList(@PathVariable String id){
        GetAccountByIdQuery account=new GetAccountByIdQuery(id);
        return queryGateway.query(account,ResponseTypes.instanceOf(Account.class)).join();
    }
}
