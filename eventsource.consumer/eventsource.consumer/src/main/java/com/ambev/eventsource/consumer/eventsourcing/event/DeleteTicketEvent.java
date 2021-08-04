package com.ambev.eventsource.consumer.eventsourcing.event;

import lombok.Value;

@Value
public class DeleteTicketEvent {

    String id;
}
