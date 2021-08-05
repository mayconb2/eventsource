package com.ambev.eventsource.consumer.eventsourcing.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
public class DeleteTicketCommand {

    @TargetAggregateIdentifier
    private String id;

}
