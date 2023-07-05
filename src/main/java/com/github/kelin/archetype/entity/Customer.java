package com.github.kelin.archetype.entity;

import static com.github.kelin.archetype.entity.EntityCollections.CUSTOMERS;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = CUSTOMERS)
public class Customer {
    @Id
    public String id;

    public String firstName;
    public String lastName;
    public int position;

    @ReadOnlyProperty
    public List<CustomerRecord> records;

    @ReadOnlyProperty
    public CustomerExtra extra;

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(String firstName, String lastName, int position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
    }

    public List<CustomerRecord> getRecords() {
        return records;
    }

    public void setRecords(List<CustomerRecord> records) {
        this.records = records;
    }

    public void setExtra(CustomerExtra extra) {
        this.extra = extra;
    }

    public CustomerExtra getExtra() {
        return extra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
