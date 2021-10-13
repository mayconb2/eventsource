package com.ambev.eventsource.consumer.service;

import com.ambev.eventsource.consumer.eventsourcing.command.*;
import com.ambev.eventsource.consumer.model.TaskEntity;
import com.ambev.eventsource.consumer.model.Ticket;
import com.ambev.eventsource.consumer.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CommandGateway commandGateway;

    public List<Ticket> findAll() {
        log.info("find all tickets!");
        return ticketRepository.findAll();
    }

    public Ticket findById(String id) {
        log.info("find ticket by id");
        return ticketRepository.findById(id).orElse(null);
    }

    public void save(Ticket ticket) {
        log.info("saving ticket id: " + ticket);
        try {
            Ticket t = ticketRepository.save(ticket);
            CreateCommand command = new CreateCommand(t.getId(), "br", t, "user");
            commandGateway.send(command);
            //commandGateway.send(new UpdateCommand(t.getId(), t, ));

            command = new CreateCommand(t.getId(), "br", new TaskEntity(), "user");
            commandGateway.send(command);

        } catch (Exception e) {
            log.error("Create ticket ERROR: " + e.getMessage());
        }
    }

    public void update(Ticket ticketNew) {
        log.info("updating ticket new: " + ticketNew);
        try {
            Ticket ticketOld = findById(ticketNew.getId());
            log.info("updating ticket old: " + ticketOld);

            if (!ticketNew.equals(ticketOld)) {
                ticketOld = changeOnlyChangedFields(ticketOld, ticketNew);
                log.info("updating ticket old A new: " + ticketOld);
                ticketRepository.save(ticketOld);
                commandGateway.send(new UpdateCommand(ticketNew.getId(), "br", ticketNew, "user"));
            }

        } catch (Exception e) {
            log.error("Update ticket ERROR: " + e.getMessage());
        }
    }

    private Ticket changeOnlyChangedFields(Ticket ticketOld, Ticket ticketNew) {

        Arrays.stream(ticketOld.getClass().getDeclaredFields())
                .sequential().forEach(field ->{

            try {
                field.setAccessible(true);

                if(field.get(ticketNew) != null) {
                    if(field.get(ticketNew) != field.get(ticketOld)){
                        field.set(ticketOld, field.get(ticketNew));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return ticketOld;
    }

    public void delete(String aggregateId) {
        log.info("deleting ticket id: " + aggregateId);
        try {
            ticketRepository.deleteById(aggregateId);
            DeleteCommand command = new DeleteCommand(aggregateId, "br", "user");
            commandGateway.send(command);

        } catch (Exception e) {
            log.error("Delete ticket ERROR: " + e.getMessage());
        }
    }

}