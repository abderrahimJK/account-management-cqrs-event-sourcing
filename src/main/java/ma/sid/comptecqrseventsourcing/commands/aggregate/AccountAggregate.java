package ma.sid.comptecqrseventsourcing.commands.aggregate;

import ma.sid.comptecqrseventsourcing.commonapi.commands.CreateAccountCommand;
import ma.sid.comptecqrseventsourcing.commonapi.commands.CreditAccountCommand;
import ma.sid.comptecqrseventsourcing.commonapi.commands.DebitAccountCommand;
import ma.sid.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import ma.sid.comptecqrseventsourcing.commonapi.events.AccountActivatedEvent;
import ma.sid.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import ma.sid.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import ma.sid.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import ma.sid.comptecqrseventsourcing.commonapi.exceptions.AmountNegativeException;
import ma.sid.comptecqrseventsourcing.commonapi.exceptions.BalanceNotEnoughException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountID;
    private double balance;
    private String currency;
    private AccountStatus state;


    public AccountAggregate() {
        //Required by AXON
    }
    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        if(command.getInitialBalance() < 0) throw new RuntimeException("No ength Sold");

        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountID = event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.state = AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.ACTIVATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.state = event.getStatus();
    }

//*********************

    @CommandHandler // when the command will be sent to the Commands bus, this method will be invoked
    public void handle(CreditAccountCommand creditAccountCommand){
        // business logic
        if(creditAccountCommand.getAmount() < 0) throw new AmountNegativeException("Credit Amount can not be Negative");
        // If Business logic is fine
        // emmit an event
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        this.balance += accountCreditedEvent.getAmount();
    }

//*********************

    // Debit command
    @CommandHandler
    public void handler(DebitAccountCommand debitAccountCommand){
        if(debitAccountCommand.getAmount() < 0) throw new AmountNegativeException("Amount can't be negative");
        //if(this.balance < debitAccountCommand.getAmount()) throw new InsufficientBalanceToDebitException(String.format("Amount must be lower than the balance (%s)", this.balance));
        if(this.balance < debitAccountCommand.getAmount()) throw new BalanceNotEnoughException(String.format("Amount must be lower than the balance (%s)", this.balance));
        // emmit event
        AggregateLifecycle.apply(new AccountDebitedEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                debitAccountCommand.getCurrency(),
                new Date()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        this.balance -= accountDebitedEvent.getAmount();
    }

}
