package com.ambev.eventsource.consumer.repository;

import com.ambev.eventsource.consumer.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    Logger logger = LoggerFactory.getLogger(TicketRepository.class);
    HashMap<String, Ticket> ticketStore = new HashMap<>();

    public default void storeTicket(Ticket ticket) {
        ticketStore.put(ticket.getId(), ticket);
        logger.info("ticket added to repository:::: Current map values ");
        ticketStore.forEach((k, v) -> logger.info(String.format("Ticket: %s", v)));
    }

}
