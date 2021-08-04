package com.ambev.eventsource.consumer.model.eventsource.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketCommand {

    @TargetAggregateIdentifier
    private String id;

    private String title;

    private String status;

    private String description;

}
