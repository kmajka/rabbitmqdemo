package com.jms.example.rabbitmqdemo.model;

import java.io.Serializable;
import java.util.Objects;

public class SimpleMessage implements Serializable {

    private String owner;
    private String body;

    public SimpleMessage(String owner, String body) {
        this.owner = owner;
        this.body = body;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "SimpleMessage{" +
                "owner='" + owner + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleMessage that = (SimpleMessage) o;
        return Objects.equals(owner, that.owner) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, body);
    }
}
