package ma.sid.comptecqrseventsourcing.commands.controllers;

import lombok.AllArgsConstructor;
import ma.sid.comptecqrseventsourcing.commonapi.DTOs.CreateAccountRequestDTO;
import ma.sid.comptecqrseventsourcing.commonapi.DTOs.CreditAccountRequestDTO;
import ma.sid.comptecqrseventsourcing.commonapi.DTOs.DebitedAccountRequestDTO;
import ma.sid.comptecqrseventsourcing.commonapi.commands.CreateAccountCommand;
import ma.sid.comptecqrseventsourcing.commonapi.commands.CreditAccountCommand;
import ma.sid.comptecqrseventsourcing.commonapi.commands.DebitAccountCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("commands/account")
@AllArgsConstructor
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){

        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
    }

    @PutMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO creditAccountRequestDTO){

        return commandGateway.send(new CreditAccountCommand(
                creditAccountRequestDTO.getAccountID(),
                creditAccountRequestDTO.getAmount(),
                creditAccountRequestDTO.getCurrency()
        ));
    }

    @PutMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitedAccountRequestDTO requestDTO){
        return commandGateway.send(new DebitAccountCommand(
                requestDTO.getAccountID(),
                requestDTO.getAmount(),
                requestDTO.getCurrency()
        ));
    }

    @GetMapping(path="/eventStore/{accountID}")
    public Stream eventStore(@PathVariable  String accountID){
        return eventStore.readEvents(accountID).asStream();
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
