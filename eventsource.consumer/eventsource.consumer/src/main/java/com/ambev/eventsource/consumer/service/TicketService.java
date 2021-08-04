package com.ambev.eventsource.consumer.service;

import com.ambev.eventsource.consumer.model.Ticket;
import com.ambev.eventsource.consumer.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
