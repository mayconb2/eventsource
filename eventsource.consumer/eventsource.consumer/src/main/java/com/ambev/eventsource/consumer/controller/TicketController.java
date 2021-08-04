package com.ambev.eventsource.consumer.controller;

import com.ambev.eventsource.consumer.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    @GetMapping("/tickets/{aggregateId}")
    public ResponseEntity<Object> findByAggregateId(@PathVariable String aggregateId) {
        log.info(String.format("GET /tickets/%s", aggregateId));
        eventStore.readEvents(aggregateId);

        return ResponseEntity.ok().body(eventStore.readEvents(aggregateId)
                .asStream()
                .map(this::getStringObjectMap)
                .collect(Collectors.toList()));
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
