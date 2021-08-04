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
    HashMap<String, Ticket> mapTicket = new HashMap<>();

    public default void storeTicket(Ticket ticket) {
        mapTicket.put(ticket.getId(), ticket);
        logger.info("ticket added to repository:::: Current map values ");
        mapTicket.forEach((k, v) -> logger.info(String.format("Ticket: %s", v)));
    }

    public default void updateTicket(Ticket ticket) {
        mapTicket.put(ticket.getId(), ticket);
        logger.info("ticket updated to repository:::: Current map values ");
        mapTicket.forEach((k, v) -> logger.info(String.format("Ticket: %s", v)));
    }

    public default void removeTicket(String aggregateId) {
        mapTicket.remove(aggregateId);
        logger.info("ticket removed to repository:::: Current map values ");
        mapTicket.forEach((k, v) -> logger.info(String.format("Ticket: %s", v)));
    }

}
