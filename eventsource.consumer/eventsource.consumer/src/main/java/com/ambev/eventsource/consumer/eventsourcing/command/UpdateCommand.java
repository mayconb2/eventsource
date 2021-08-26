package com.ambev.eventsource.consumer.eventsourcing.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class UpdateCommand {

    @TargetAggregateIdentifier
    private String id;

    private Object command;
}
