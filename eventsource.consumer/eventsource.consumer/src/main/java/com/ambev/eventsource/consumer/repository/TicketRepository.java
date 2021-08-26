package com.ambev.eventsource.consumer.repository;

import com.ambev.eventsource.consumer.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
}
