package com.ambev.eventsource.consumer.model;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@XStreamAlias("Ticket")
public class Ticket {

    @Id
    private String id;

    private String title;

    private String status;

    private String description;

    @XStreamImplicit
    private List<Ticket> tickets;

    private Ticket ticket;

    public Ticket(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(title, ticket.title) && Objects.equals(status, ticket.status) && Objects.equals(description, ticket.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, status, description);
    }
}
