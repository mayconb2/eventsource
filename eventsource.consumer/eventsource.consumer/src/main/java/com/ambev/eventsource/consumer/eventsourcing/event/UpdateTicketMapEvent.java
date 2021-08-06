package com.ambev.eventsource.consumer.eventsourcing.event;

import lombok.Value;

import java.util.Map;

@Value
public class UpdateTicketMapEvent {

    private final Map<String, Object> fieldsChanged;
}
