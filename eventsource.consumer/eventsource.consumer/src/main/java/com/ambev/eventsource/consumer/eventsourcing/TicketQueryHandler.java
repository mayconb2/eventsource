package com.ambev.eventsource.consumer.eventsourcing;

import com.ambev.eventsource.consumer.eventsourcing.event.CreateTicketEvent;
import com.ambev.eventsource.consumer.model.Ticket;
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
    public void on(CreateTicketEvent event) {
        ticketRepository.storeTicket(new Ticket(event.getId(),
                event.getTitle(), event.getStatus(), event.getDescription()));
    }
}
