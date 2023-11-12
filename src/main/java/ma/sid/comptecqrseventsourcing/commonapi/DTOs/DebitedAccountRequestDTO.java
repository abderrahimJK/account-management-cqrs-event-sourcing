package ma.sid.comptecqrseventsourcing.commonapi.DTOs;

import lombok.Data;

@Data
public class DebitedAccountRequestDTO {

    private String accountID;
    private double amount;
    private String currency;
}
