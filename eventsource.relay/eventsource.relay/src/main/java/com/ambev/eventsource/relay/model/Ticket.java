package com.ambev.eventsource.relay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class Ticket {

    @JsonProperty
    private String id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String status;

    @JsonProperty
    private String description;

}
