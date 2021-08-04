package com.ambev.eventsource.relay.controller;

import com.ambev.eventsource.relay.model.Ticket;
import com.ambev.eventsource.relay.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @PostMapping("/pushTicket")
    public ResponseEntity<Ticket> pushTicket(@RequestBody Ticket ticket) {

        if(queueService.push(ticket)) {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }

        return new ResponseEntity<>(new Ticket(), HttpStatus.OK);
    }
}
