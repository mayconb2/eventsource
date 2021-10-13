package com.ambev.eventsource.consumer.controller;

import com.ambev.eventsource.consumer.model.Ticket;
import com.ambev.eventsource.consumer.service.TicketService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Permission;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
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

    @PostMapping("/tickets")
    public ResponseEntity<Object> insert(@RequestBody Ticket ticket) {

        ticketService.save(ticket);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity<Object> insert(@PathVariable String id, @RequestBody Ticket ticket) {

        ticket.setId(id);
        ticketService.update(ticket);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


    @GetMapping("/tickets/events/{aggregateId}")
    public ResponseEntity<Object> findByAggregateId(@PathVariable String aggregateId) {
        log.info(String.format("GET /tickets/%s", aggregateId));

        return ResponseEntity.ok().body(eventStore.readEvents(aggregateId)
                .asStream()
                .collect(Collectors.toList()));
//        return ResponseEntity.ok().body(eventStore.readEvents(aggregateId)
//                .asStream()
//                .map(this::getStringObjectMap)
//                .collect(Collectors.toList()));
    }

    @DeleteMapping("/tickets/delete/{aggregateId}")
    public ResponseEntity<Object> deleteByAggregatedId(@PathVariable String aggregateId) {
        log.info(String.format("DELETE /tickets/delete/%s", aggregateId));
        ticketService.delete(aggregateId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Map<String, Object> getStringObjectMap(org.axonframework.eventhandling.DomainEventMessage<?> s) {

        XStream xStream = new XStream();

        xStream.alias("Ticket", Ticket.class);
        xStream.addPermission (NoTypePermission.NONE);
    // clear out existing permissions and start a whitelist
        //xStream.addPermission(NoTypePermission.NONE); // allow some basics
        //xStream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.allowTypeHierarchy(Collection.class); // allow any type from the same package
        xStream.allowTypesByWildcard(new String[] { Ticket.class.getPackage().getName()+".*" });

        //XStream.setupDefaultSecurity(xStream);


        Map<String, Object> eventSourcing = new HashMap<>();
        eventSourcing.put("timestamp", s.getTimestamp().getEpochSecond());
        eventSourcing.put("aggregateIdentifier", s.getAggregateIdentifier());
        eventSourcing.put("identifier", s.getIdentifier());
        eventSourcing.put("type", s.getType());
        eventSourcing.put("payloadType", s.getPayloadType().getCanonicalName());
        eventSourcing.put("metadata", s.getMetaData());
        //xStream.fromXML(s.getPayload().toString());
        xStream.fromXML("<com.ambev.eventsource.consumer.eventsourcing.event.CreateEvent serialization=\"custom\"><unserializable-parents/><map><default><loadFactor>0.75</loadFactor><threshold>12</threshold></default><int>16</int><int>6</int><string>tickets</string><list><com.ambev.eventsource.consumer.model.Ticket><title>Ticket</title><status>OPEN</status><description>ticket</description><tickets/><ticket/></com.ambev.eventsource.consumer.model.Ticket></list><string>ticket</string><com.ambev.eventsource.consumer.model.Ticket><title>Ticket</title><status>OPEN</status><description>ticket</description><tickets/><ticket/></com.ambev.eventsource.consumer.model.Ticket><string>description</string><string>ticket</string><string>id</string><string>615b4203049151766eef2027</string><string>title</string><string>Ticket</string><string>status</string><string>OPEN</string></map></com.ambev.eventsource.consumer.eventsourcing.event.CreateEvent>");

        eventSourcing.put("payload", s.getPayload());

        return eventSourcing;
    }


}