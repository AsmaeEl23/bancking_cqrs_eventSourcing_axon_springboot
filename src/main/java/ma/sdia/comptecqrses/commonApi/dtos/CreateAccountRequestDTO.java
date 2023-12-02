package ma.sdia.comptecqrses.commonApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sdia.comptecqrses.commonApi.enums.AccountStatus;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateAccountRequestDTO {

    private double initialBalance;
    private String currency;
    private AccountStatus status;
}
