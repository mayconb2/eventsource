package com.ambev.eventsource.consumer.repository;

import com.ambev.eventsource.consumer.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

}
