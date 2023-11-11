package ma.sid.comptecqrseventsourcing.commonapi.events;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class BaseEvent<T> {

    private T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}
