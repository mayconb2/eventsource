package com.ambev.eventsource.consumer.eventsourcing.event;

import lombok.Value;

@Value
public class CreateTicketEvent {

    private final String id;

    private final String title;

    private final String status;

    private final String description;
}
