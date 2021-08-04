package com.ambev.eventsource.consumer.eventsourcing.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Getter
@Setter
public class CreateTicketCommand {

    @TargetAggregateIdentifier
    private String id;

    private String title;

    private String status;

    private String description;

}
