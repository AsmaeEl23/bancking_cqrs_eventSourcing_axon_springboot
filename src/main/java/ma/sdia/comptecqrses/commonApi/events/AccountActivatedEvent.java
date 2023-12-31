package ma.sdia.comptecqrses.commonApi.events;

import lombok.Getter;
import ma.sdia.comptecqrses.commonApi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String>{

    @Getter private AccountStatus status;
    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status=status;
    }
}
