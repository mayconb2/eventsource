package com.ambev.eventsource.relay.controller;

import com.ambev.eventsource.relay.model.Ticket;
import com.ambev.eventsource.relay.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @PostMapping("/pushTicket")
    public ResponseEntity<Ticket> pushTicket(@RequestBody Ticket ticket) {
        log.info("POST /pushTicket");

        ticket.setId(null);
        if(queueService.push(ticket)) {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }

        return new ResponseEntity<>(new Ticket(), HttpStatus.OK);
    }

    @PutMapping("/pushTicket/{id}")
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket,
        @PathVariable(value= "id") String id) {
        log.info("PUT /pushTicket/"+id);

        ticket.setId(id);
        if(queueService.push(ticket)) {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }

        return new ResponseEntity<>(new Ticket(), HttpStatus.OK);
    }
}
