package com.ambev.eventsource.consumer.model.eventsource.create;

import lombok.Value;

@Value
public class CreateTicketEvent {

    String id;

    String title;

    String status;

    String description;
}
