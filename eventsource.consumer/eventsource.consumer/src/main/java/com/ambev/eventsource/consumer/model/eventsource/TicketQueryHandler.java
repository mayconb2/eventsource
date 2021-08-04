package com.ambev.eventsource.consumer.model.eventsource;

import com.ambev.eventsource.consumer.model.Ticket;
import com.ambev.eventsource.consumer.model.eventsource.create.CreateTicketEvent;
import com.ambev.eventsource.consumer.model.eventsource.update.UpdateTicketCommand;
import com.ambev.eventsource.consumer.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TicketQueryHandler {

    @Autowired
    private TicketRepository ticketRepository;

    @EventHandler
    public void on(CreateTicketEvent createTicketEvent) {
        ticketRepository.storeTicket(new Ticket(createTicketEvent.getId(), createTicketEvent.getTitle(), createTicketEvent.getStatus(), createTicketEvent.getDescription()));
    }

    @EventHandler
    public void on(UpdateTicketCommand updateTicketEvent) {
        ticketRepository.storeTicket(new Ticket(updateTicketEvent.getId(), updateTicketEvent.getTitle(), updateTicketEvent.getStatus(), updateTicketEvent.getDescription()));
    }
}
