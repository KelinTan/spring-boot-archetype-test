package com.github.kelin.archetype.entity;

import static com.github.kelin.archetype.entity.EntityCollections.CUSTOMER_RECORDS;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = CUSTOMER_RECORDS)
public class CustomerRecord {
    @Id
    public String id;

    public String firstName;

    public String record;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public CustomerRecord(String firstName, String record) {
        this.firstName = firstName;
        this.record = record;
    }

    public CustomerRecord() {
    }

    public CustomerRecord(String record) {
        this.record = record;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
