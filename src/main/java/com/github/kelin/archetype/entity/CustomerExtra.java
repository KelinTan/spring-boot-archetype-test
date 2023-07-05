package com.github.kelin.archetype.entity;

import static com.github.kelin.archetype.entity.EntityCollections.CUSTOMER_EXTRA;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = CUSTOMER_EXTRA)
public class CustomerExtra {
    @Id
    public String id;
    public String firstName;
    public String data;

    public CustomerExtra() {
    }

    public CustomerExtra(String data) {
        this.data = data;
    }

    public CustomerExtra(String firstName, String data) {
        this.firstName = firstName;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }
}
