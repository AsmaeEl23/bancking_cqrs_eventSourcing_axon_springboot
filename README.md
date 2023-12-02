<center><h1>An application that allows us to manage accounts respecting <color style="color: burlywood">CQRS</color> and <color style="color: burlywood">Event Sourcing</color> patterns with the <color style="color: burlywood">AXON</color> and <color style="color: burlywood">Spring Boot </color>Frameworks</h1></center>
<h4>Creating accounts using Postman</h4>
<img src="images/img.png">
<p>Consulting the database after creating two account</p>
<center><p>domain_event_entry table</p></center>
<img src="images/img_1.png">
<p>Payload : content of the event</p>
<h4>Get an account from event store </h4>
<img src="images/img_2.png">
<h4>Credit account</h4>
<p>Add a negative amount Exception</p>
<img src="images/img_3.png">
<p>Add an amount to the account that we already activate</p>
<img src="images/img_4.png">
<img src="images/img_5.png">
<h4>Debit account</h4>
<img src="images/img_6.png">
<p>I created reading part (query) by using account service handler </p>
<p>With this function i can stock all the accounts i was created before in the database</p>
<pre>package ma.sdia.comptecqrses.query.service;

@Service
@AllArgsConstructor
@Slf4j
public class AccountServiceHandler {
private AccountRepository accountRepository;
private OperationRepository operationRepository;
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
}
</pre>
<img src="images/img_7.png">
<p>Here the status is Null, cuz i forgot to add it in events, so it doesn't register, i tried to creat another account, so it works.</p>
<p>I did the same thing for the Activated, Credited and debited events</p>
<h4>Create, Credit, Debit</h4>
<h6>Create</h6>
<img src="images/img_8.png">
<h6>Credit</h6>
<img src="images/img_9.png">
<h6>debit</h6>
<img src="images/img_10.png">
<h4>Operations</h4>
<img src="images/img_11.png">
<h6>Account query controller</h6>
<img src="images/img_12.png">
