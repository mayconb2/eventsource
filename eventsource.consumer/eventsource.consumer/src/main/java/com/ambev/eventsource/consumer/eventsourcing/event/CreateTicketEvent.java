package com.ambev.eventsource.consumer.eventsourcing.event;

import lombok.Value;

@Value
public class CreateTicketEvent {

    String id;

    String title;

    String status;

    String description;
}
