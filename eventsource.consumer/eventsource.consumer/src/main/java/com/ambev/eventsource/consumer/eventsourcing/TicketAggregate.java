package com.ambev.eventsource.consumer.eventsourcing;

import com.ambev.eventsource.consumer.eventsourcing.command.CreateTicketCommand;
import com.ambev.eventsource.consumer.eventsourcing.command.UpdateTicketCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class TicketAggregate {

    @AggregateIdentifier
    private String id;

    private String title;

    private String status;

    private String description;

    @CommandHandler
    public TicketAggregate(CreateTicketCommand createTicketCommand) {
        this.id = createTicketCommand.getId();
        this.title = createTicketCommand.getTitle();
        this.status = createTicketCommand.getStatus();
        this.description = createTicketCommand.getDescription();

        log.info(String.format("createTicketCommand id: %s", createTicketCommand.getId()));

        CreateTicketCommand ticketCreateEvent = new CreateTicketCommand(createTicketCommand.getId(), createTicketCommand.getTitle(), createTicketCommand.getStatus(), createTicketCommand.getDescription());

        MetaData metaData = MetaData.with("id", "1234")
                .and("title", "test create title")
                .and("status", "test create status")
                .and("description", "test create description");

        AggregateLifecycle.apply(ticketCreateEvent, metaData);
    }

    @CommandHandler
    public TicketAggregate(UpdateTicketCommand updateTicketCommand) {
        this.id = updateTicketCommand.getId();
        this.title = updateTicketCommand.getTitle();
        this.status = updateTicketCommand.getStatus();
        this.description = updateTicketCommand.getDescription();

        log.info(String.format("updateTicketCommand id: %s", updateTicketCommand.getId()));

        UpdateTicketCommand ticketUpdateEvent = new UpdateTicketCommand(updateTicketCommand.getId(), updateTicketCommand.getTitle(), updateTicketCommand.getStatus(), updateTicketCommand.getDescription());

        MetaData metaData = MetaData.with("id", "12345")
                .and("title", "test update title")
                .and("status", "test update status")
                .and("description", "test update description");

        AggregateLifecycle.apply(ticketUpdateEvent, metaData);
    }

}
