package com.ambev.eventsource.consumer.model.eventsource.update;

import lombok.Value;

@Value
public class UpdateTicketEvent {

    String id;

    String title;

    String status;

    String description;
}
