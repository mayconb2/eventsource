package com.ambev.eventsource.relay.service;

import com.ambev.eventsource.relay.config.RabbitMQConfig;
import com.ambev.eventsource.relay.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class QueueService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public boolean push(Ticket ticket) {

        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, RabbitMQConfig.routingkey, ticket);
            log.info("QueueService: push ticket SUCCESS!");

        } catch (Exception e) {
            log.error("QueueService: push ticket ERROR, " + e.getMessage());
        }

        return true;
    }
}
