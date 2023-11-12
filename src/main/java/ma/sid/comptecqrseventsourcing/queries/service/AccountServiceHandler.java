package ma.sid.comptecqrseventsourcing.queries.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.sid.comptecqrseventsourcing.commonapi.events.AccountActivatedEvent;
import ma.sid.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import ma.sid.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import ma.sid.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import ma.sid.comptecqrseventsourcing.commonapi.queries.GetAccountByIdQuery;
import ma.sid.comptecqrseventsourcing.commonapi.queries.GetAllAccountQuery;
import ma.sid.comptecqrseventsourcing.queries.entities.Account;
import ma.sid.comptecqrseventsourcing.queries.entities.Operation;
import ma.sid.comptecqrseventsourcing.queries.entities.OperationType;
import ma.sid.comptecqrseventsourcing.queries.repository.AccountRepository;
import ma.sid.comptecqrseventsourcing.queries.repository.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AccountServiceHandler {

    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    //
    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("**********************************************");
        log.info("[ Handling AccountCreatedEvent {} ]", event.getId());
        Account account = new Account();
        account.setId(event.getId());
        account.setBalance(event.getInitialBalance());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        accountRepository.save(account);

    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("**********************************************");
        log.info("[ Handling AccountActivatedEvent {} ]", event.getId());
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("**********************************************");
        log.info("[ Handling AccountCreditedEvent {} ]", event.getId());
        Account account = accountRepository.findById(event.getId()).get();

        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setType(OperationType.CREDIT);
        operation.setDate(event.getDate());
        operation.setAccount(account);

        operationRepository.save(operation);

        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("**********************************************");
        log.info("[ Handling AccountDebitedEvent {} ]", event.getId());

        Account account = accountRepository.findById(event.getId()).get();

        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setType(OperationType.DEBIT);
        operation.setDate(event.getDate());
        operation.setAccount(account);

        operationRepository.save(operation);

        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);
    }

    @QueryHandler
    public List<Account> getAllAccounts(GetAllAccountQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account getAccount(GetAccountByIdQuery query){
        return accountRepository.findById(query.getId()).get();
    }

}
