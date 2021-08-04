package com.ambev.eventsource.consumer.service;

import com.ambev.eventsource.consumer.config.RabbitMQConfig;
import com.ambev.eventsource.consumer.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TicketListener {

    @Autowired
    private TicketService ticketService;

    @RabbitListener(queues = RabbitMQConfig.queueName)
    public void listener(Ticket ticket) {
        log.info("Consumer: " + ticket.toString());
        if (ticket.getId() == null) {
            ticketService.save(ticket);
        } else {
            ticketService.update(ticket);
        }
    }

}
