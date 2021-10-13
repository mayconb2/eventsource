package com.ambev.eventsource.consumer.eventsourcing.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class CreateCommand {

    @TargetAggregateIdentifier
    private String id;

    private String country;

    private Object command;

    private String userId;
}
