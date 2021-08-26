package com.ambev.eventsource.consumer.eventsourcing;

import com.ambev.eventsource.consumer.eventsourcing.command.*;
import com.ambev.eventsource.consumer.eventsourcing.event.CreateEvent;
import com.ambev.eventsource.consumer.eventsourcing.event.DeleteEvent;
import com.ambev.eventsource.consumer.eventsourcing.event.UpdateEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Arrays;

@Aggregate
@Getter
@Setter
@Slf4j
public class ObjectAggregate {

    @AggregateIdentifier
    private String id;

    public ObjectAggregate(){}

    ////////////////////////////Create Event ////////////////////////////////////////////////////////////
    @CommandHandler
    public ObjectAggregate(CreateCommand createCommand) {
        this.id = createCommand.getId();
        Object command = createCommand.getCommand();
        log.info(String.format("createObjectCommand id: %s", command));

        CreateEvent createEvent = getCreateEventsFromCommand(command);

        MetaData metaData = createMetadata(createCommand, command);
        
        AggregateLifecycle.apply(createEvent, metaData);
    }

    private CreateEvent getCreateEventsFromCommand(Object createCommand) {
        CreateEvent fieldsCreated = new CreateEvent();

        Arrays.stream(createCommand.getClass().getDeclaredFields()).sequential().forEach(field ->{
            try {
                field.setAccessible(true);
                fieldsCreated.put(field.getName(), field.get(createCommand));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return fieldsCreated;
    }

    @EventSourcingHandler
    public void onCreateEvent(CreateEvent createEvent) {
        log.info("Create Handling {} event: {}", createEvent.getClass().getSimpleName(), createEvent);
        this.id = createEvent.get("id").toString();
    }

    ////////////////////////////Update Event ////////////////////////////////////////////////////////////


    @CommandHandler
    public void on(UpdateCommand updateCommand) {
        //this.id = updateCommand.getId();
        Object command = updateCommand.getCommand();
        log.info(String.format("updateCommand id: %s", command));

        UpdateEvent updateEvent = getUpdateEventsFromCommand(command);

        MetaData metaData = createMetadata(updateCommand, command);

        AggregateLifecycle.apply(updateEvent, metaData);
    }

    private UpdateEvent getUpdateEventsFromCommand(Object updateCommand) {
        UpdateEvent fieldsChanged = new UpdateEvent();

        Arrays.stream(updateCommand.getClass().getDeclaredFields()).sequential().forEach(field ->{
            try {
                field.setAccessible(true);
                if(field.get(updateCommand) != null) {
                    fieldsChanged.put(field.getName(), field.get(updateCommand));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return fieldsChanged;
    }

    @EventSourcingHandler
    public void on(UpdateEvent updateEvent) {
        log.info("Update Handling {} event: {}", updateEvent.getClass().getSimpleName(), updateEvent);
        this.id = updateEvent.get("id").toString();
    }

    ////////////////////////////Delete Event ////////////////////////////////////////////////////////////
    @CommandHandler
    public void on(DeleteCommand deleteCommand) {
        this.id = deleteCommand.getId();
        log.info(String.format("deleteObjectCommand id: %s", deleteCommand));

        DeleteEvent deleteEvent = getDeleteEventsFromCommand(deleteCommand);

        MetaData metaData = createMetadata(deleteCommand, null);

        AggregateLifecycle.apply(deleteEvent, metaData);
    }

    private DeleteEvent getDeleteEventsFromCommand(Object deleteCommand) {
        DeleteEvent objectDeleted = new DeleteEvent();

        Arrays.stream(deleteCommand.getClass().getDeclaredFields()).sequential().forEach(field ->{
            try {
                field.setAccessible(true);
                objectDeleted.put(field.getName(), field.get(deleteCommand));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return objectDeleted;
    }

    @EventSourcingHandler
    public void on(DeleteEvent deleteEvent) {
        log.info("Delete Handling {} event: {}", deleteEvent.getClass().getSimpleName(), deleteEvent);
        this.id = deleteEvent.get("id").toString();

    }

    /////////////////METADATA ////////////////////////////////////////////////////////////////////////////////////

    private MetaData createMetadata(Object command, Object object) {
        String type = command.getClass().getSimpleName();
        if(object != null){
            type += object.getClass().getSimpleName();
        }

        return MetaData
                .with("userId", "1234")
                .with("country", "BR")
                .and("eventType", type);
    }


}