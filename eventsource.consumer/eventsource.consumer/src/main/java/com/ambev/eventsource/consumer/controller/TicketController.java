package com.ambev.eventsource.consumer.controller;

import com.ambev.eventsource.consumer.model.Ticket;
import com.ambev.eventsource.consumer.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@Slf4j
public class TicketController {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public ResponseEntity<Object> findAll() {
        log.info("GET /tickets");
        List<Ticket> listTickets = ticketService.findAll();
        return new ResponseEntity<>(listTickets, HttpStatus.OK);
    }


    @GetMapping("/tickets/events/{aggregateId}")
    public ResponseEntity<Object> findByAggregateId(@PathVariable String aggregateId) {
        log.info(String.format("GET /tickets/%s", aggregateId));

        return ResponseEntity.ok().body(eventStore.readEvents(aggregateId)
                .asStream()
                .map(this::getStringObjectMap)
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/tickets/delete/{aggregateId}")
    public ResponseEntity<Object> deleteByAggregatedId(@PathVariable String aggregateId) {
        log.info(String.format("DELETE /tickets/delete/%s", aggregateId));
        ticketService.delete(aggregateId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Map<String, Object> getStringObjectMap(org.axonframework.eventhandling.DomainEventMessage<?> s) {


        Map<String, Object> eventSourcing = new HashMap<>();
        eventSourcing.put("payload", s.getPayload());
        eventSourcing.put("timestamp", s.getTimestamp().getEpochSecond());
        eventSourcing.put("aggregateIdentifier", s.getAggregateIdentifier());
        eventSourcing.put("identifier", s.getIdentifier());
        eventSourcing.put("type", s.getType());
        eventSourcing.put("payloadType", s.getPayloadType().getCanonicalName());
        try {
            eventSourcing.put("metadata", s.getMetaData());

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return eventSourcing;
    }


}
