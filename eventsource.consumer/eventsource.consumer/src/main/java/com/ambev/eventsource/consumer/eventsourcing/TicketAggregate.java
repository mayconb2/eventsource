package com.ambev.eventsource.consumer.eventsourcing;

import com.ambev.eventsource.consumer.eventsourcing.command.CreateTicketCommand;
import com.ambev.eventsource.consumer.eventsourcing.command.DeleteTicketCommand;
import com.ambev.eventsource.consumer.eventsourcing.command.UpdateTicketCommand;
import com.ambev.eventsource.consumer.eventsourcing.event.CreateTicketEvent;
import com.ambev.eventsource.consumer.eventsourcing.event.DeleteTicketEvent;
import com.ambev.eventsource.consumer.eventsourcing.event.UpdateTicketEvent;
import com.ambev.eventsource.consumer.eventsourcing.event.UpdateTicketMapEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.*;

@Aggregate
@Getter
@Setter
@Slf4j
public class TicketAggregate {

    @AggregateIdentifier
    private String id;

    private String title;

    private String status;

    private String description;

    public TicketAggregate(){}

    @CommandHandler
    public TicketAggregate(CreateTicketCommand createTicketCommand) {
        //this.id = createTicketCommand.getId();
        log.info(String.format("createTicketCommand id: %s", createTicketCommand.getId()));

        CreateTicketEvent ticketCreateEvent = new CreateTicketEvent(
                createTicketCommand.getId(),
                createTicketCommand.getTitle(),
                createTicketCommand.getStatus(),
                createTicketCommand.getDescription()
        );

//        Exemple create metaData
        MetaData metaData = MetaData.with("userId", "1234")
                .and("eventType", createTicketCommand.getClass().getSimpleName());

        AggregateLifecycle.apply(ticketCreateEvent, metaData);
    }

    @EventSourcingHandler
    public void on(CreateTicketEvent createTicketEvent) {
        log.info("Handling {} event: {}", createTicketEvent.getClass().getSimpleName(), createTicketEvent);
        log.info("Done handling {} event: {}", createTicketEvent.getClass().getSimpleName(), createTicketEvent);
        this.id = createTicketEvent.getId();
        this.status = createTicketEvent.getStatus();
        this.title = createTicketEvent.getTitle();
        this.description = createTicketEvent.getDescription();
    }

    @CommandHandler
    public void on(UpdateTicketCommand updateTicketCommand) {
        //this.id = updateTicketCommand.getId();
        log.info(String.format("updateTicketCommand id: %s", updateTicketCommand.getId()));

        //UpdateTicketEvent ticketUpdateEvent = new UpdateTicketEvent(updateTicketCommand.getId(), updateTicketCommand.getTitle(), updateTicketCommand.getStatus(), updateTicketCommand.getDescription());
        UpdateTicketMapEvent ticketUpdateEvent = getMapEvents(updateTicketCommand);

        MetaData metaData = MetaData.with("userId", "1234")
                .and("eventType", updateTicketCommand.getClass().getSimpleName());

        AggregateLifecycle.apply(ticketUpdateEvent.getFieldsChanged(), metaData);
    }

    private UpdateTicketMapEvent getMapEvents(UpdateTicketCommand updateTicketCommand) {
        Map<String, Object> fieldsChanged = new HashMap();

        Arrays.stream(updateTicketCommand.getClass().getDeclaredFields())
                .sequential().forEach(field ->{

            try {
                field.setAccessible(true);
                if(field.get(updateTicketCommand) != null) {
                    fieldsChanged.put(field.getName(), field.get(updateTicketCommand));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return new UpdateTicketMapEvent(fieldsChanged);
    }

    @EventSourcingHandler
    public void on(UpdateTicketEvent updateTicketEvent) {
        log.info("Handling {} event: {}", updateTicketEvent.getClass().getSimpleName(), updateTicketEvent);
        log.info("Done handling {} event: {}", updateTicketEvent.getClass().getSimpleName(), updateTicketEvent);
        this.id = updateTicketEvent.getId();
        this.status = updateTicketEvent.getStatus();
        this.title = updateTicketEvent.getTitle();
        this.description = updateTicketEvent.getDescription();
    }

    @CommandHandler
    public void on(DeleteTicketCommand deleteTicketCommand) {
        this.id = deleteTicketCommand.getId();
        log.info(String.format("deleteTicketCommand id: %s", deleteTicketCommand.getId()));

        DeleteTicketEvent ticketDeleteEvent = new DeleteTicketEvent(deleteTicketCommand.getId());

        MetaData metaData = MetaData.with("userId", "1234")
                .and("eventType", deleteTicketCommand.getClass().getSimpleName());

        AggregateLifecycle.apply(ticketDeleteEvent, metaData);
    }

    @EventSourcingHandler
    public void on(DeleteTicketEvent deleteTicketEvent) {
        log.info("Handling {} event: {}", deleteTicketEvent.getClass().getSimpleName(), deleteTicketEvent);
        log.info("Done handling {} event: {}", deleteTicketEvent.getClass().getSimpleName(), deleteTicketEvent);
        this.id = deleteTicketEvent.getId();
    }

}