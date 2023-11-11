package ma.sid.comptecqrseventsourcing.commonapi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateAccountRequestDTO {

    private double initialBalance;
    private String currency;
}
