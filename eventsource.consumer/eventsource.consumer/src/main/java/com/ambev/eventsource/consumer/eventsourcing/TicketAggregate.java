package com.ambev.eventsource.consumer.eventsourcing;

import com.ambev.eventsource.consumer.eventsourcing.command.CreateTicketCommand;
import com.ambev.eventsource.consumer.eventsourcing.command.DeleteTicketCommand;
import com.ambev.eventsource.consumer.eventsourcing.command.UpdateTicketCommand;
import com.ambev.eventsource.consumer.eventsourcing.event.CreateTicketEvent;
import com.ambev.eventsource.consumer.eventsourcing.event.DeleteTicketEvent;
import com.ambev.eventsource.consumer.eventsourcing.event.UpdateTicketEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
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
    public void command(CreateTicketCommand createTicketCommand) {

        log.info(String.format("createTicketCommand id: %s", createTicketCommand.getId()));

        CreateTicketCommand ticketCreateEvent = new CreateTicketCommand(createTicketCommand.getId(), createTicketCommand.getTitle(), createTicketCommand.getStatus(), createTicketCommand.getDescription());

//        Exemple create metaData
        MetaData metaData = MetaData.with("id", "1234")
                .and("title", "test create title")
                .and("status", "test create status")
                .and("description", "test create description");

        AggregateLifecycle.apply(ticketCreateEvent, metaData);
    }

    @CommandHandler
    public void handler(UpdateTicketCommand updateTicketCommand) {

        log.info(String.format("updateTicketCommand id: %s", updateTicketCommand.getId()));

        UpdateTicketCommand ticketUpdateEvent = new UpdateTicketCommand(updateTicketCommand.getId(), updateTicketCommand.getTitle(), updateTicketCommand.getStatus(), updateTicketCommand.getDescription());

        AggregateLifecycle.apply(ticketUpdateEvent);
    }

    @CommandHandler
    public void handler(DeleteTicketCommand deleteTicketCommand) {

        log.info(String.format("deleteTicketCommand id: %s", deleteTicketCommand.getId()));

        DeleteTicketCommand ticketDeleteEvent = new DeleteTicketCommand(deleteTicketCommand.getId());

        AggregateLifecycle.apply(ticketDeleteEvent);
    }

    @EventSourcingHandler
    public void on(CreateTicketEvent createTicketEvent) {
        log.info("Handling {} event: {}", createTicketEvent.getClass().getSimpleName(), createTicketEvent);
        log.info("Done handling {} event: {}", createTicketEvent.getClass().getSimpleName(), createTicketEvent);
    }

    @EventSourcingHandler
    public void on(UpdateTicketEvent updateTicketEvent) {
        log.info("Handling {} event: {}", updateTicketEvent.getClass().getSimpleName(), updateTicketEvent);
        log.info("Done handling {} event: {}", updateTicketEvent.getClass().getSimpleName(), updateTicketEvent);
    }

    @EventSourcingHandler
    public void on(DeleteTicketEvent deleteTicketEvent) {
        log.info("Handling {} event: {}", deleteTicketEvent.getClass().getSimpleName(), deleteTicketEvent);
        log.info("Done handling {} event: {}", deleteTicketEvent.getClass().getSimpleName(), deleteTicketEvent);
    }

}
