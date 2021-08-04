package com.ambev.eventsource.consumer.model.eventsource.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTicketCommand {

    @TargetAggregateIdentifier
    private String id;

    private String title;

    private String status;

    private String description;

}


