package ma.sdia.comptecqrses.commonApi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommand<T> {
    //identifient l'aggregat que on va fait les commands
    @TargetAggregateIdentifier
    @Getter private T id;
    //des objects immuable : ya pas des setter il ya just des getters

    public BaseCommand(T id) {
        this.id = id;
    }

}
