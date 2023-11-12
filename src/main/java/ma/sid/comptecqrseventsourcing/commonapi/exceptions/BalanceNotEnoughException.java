package ma.sid.comptecqrseventsourcing.commonapi.exceptions;

public class BalanceNotEnoughException extends RuntimeException {
    public BalanceNotEnoughException(String message) {
        super(message);
    }
}
