package ma.sid.comptecqrseventsourcing.commonapi.DTOs;

import lombok.Data;

@Data
public class CreditAccountRequestDTO {

    private String accountID;
    private double amount;
    private String currency;
}
