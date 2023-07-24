package com.github.kelin.archetype.repository;

import static com.github.kelin.archetype.entity.EntityCollections.CUSTOMERS;
import static com.github.kelin.archetype.entity.EntityCollections.CUSTOMER_EXTRA;
import static com.github.kelin.archetype.entity.EntityCollections.CUSTOMER_RECORDS;
import static com.github.kelin.archetype.support.TestConstants.CUSTOMER_EXTRA_BSON;
import static com.github.kelin.archetype.support.TestConstants.CUSTOMER_JSON;
import static com.github.kelin.archetype.support.TestConstants.CUSTOMER_RECORD_BSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import static org.springframework.data.mongodb.core.aggregation.LookupOperation.newLookup;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.github.kelin.archetype.entity.Customer;
import com.github.kelin.archetype.support.BaseMongoTest;
import com.github.kelin.archetype.support.MongoScript;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@MongoScript({CUSTOMER_JSON, CUSTOMER_EXTRA_BSON, CUSTOMER_RECORD_BSON})
@DataMongoTest
public class CustomerRepositoryTest extends BaseMongoTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findByFirstName() {
        List<Customer> byFirstName = customerRepository.findByFirstName("Alice1");
        assertEquals("Alice1", byFirstName.get(0).firstName);
    }

    @Test
    public void findByLastName() {
        Query query = query(where("lastName").is("Smith2"));
        List<Customer> byLastName = mongoTemplate.find(query, Customer.class);
        assertEquals("Smith2", byLastName.get(0).lastName);
    }

    @Test
    public void findByLastNameAndPage() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Alice1-1", "Smith2", 3));
        customerRepository.insert(customers);

        Query query = query(where("lastName").is("Smith2")).with(Sort.by(Direction.ASC, "position"));
        List<Customer> byLastName = mongoTemplate.find(query, Customer.class);
        assertEquals("Smith2", byLastName.get(0).lastName);
        assertEquals(2, byLastName.get(0).position);
    }

    @Test
    public void findByLastNameAndSort() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Alice1-1", "Smith2"));
        customerRepository.insert(customers);

        Query query = query(where("lastName").is("Smith2")).skip(1).limit(1);
        List<Customer> byLastName = mongoTemplate.find(query, Customer.class);
        assertEquals("Smith2", byLastName.get(0).lastName);
    }

    @Test
    public void findAggregateCustomerRecord() {
        LookupOperation lookupOperation = newLookup()
                .from(CUSTOMER_RECORDS)
                .localField("firstName")
                .foreignField("firstName")
                .as("records");
        MatchOperation matchOperation = match(where("firstName").is("Alice1"));
        Aggregation aggregation = newAggregation(matchOperation, lookupOperation);

        List<Customer> aggregated = mongoTemplate.aggregate(aggregation, CUSTOMERS, Customer.class).getMappedResults();
        assertEquals(1, aggregated.size());
        assertEquals(2, aggregated.get(0).records.size());
        assertEquals("record1", aggregated.get(0).records.get(0).record);
    }

    @Test
    public void findAggregateCustomerRecordByPage() {
        LookupOperation lookupOperation = newLookup()
                .from(CUSTOMER_RECORDS)
                .localField("firstName")
                .foreignField("firstName")
                .as("records");
        Aggregation aggregation = newAggregation(lookupOperation, skip(1L), limit(1));

        List<Customer> aggregated = mongoTemplate.aggregate(aggregation, CUSTOMERS, Customer.class).getMappedResults();
        assertEquals(1, aggregated.size());
    }

    @Test
    public void findAggregateCustomerAndExtra() {
        LookupOperation lookupOperation = newLookup()
                .from(CUSTOMER_RECORDS)
                .localField("firstName")
                .foreignField("firstName")
                .as("records");

        LookupOperation lookupOperation2 = newLookup()
                .from(CUSTOMER_EXTRA)
                .localField("firstName")
                .foreignField("firstName")
                .as("extra");

        MatchOperation matchOperation = match(where("firstName").is("Alice1"));
        Aggregation aggregation = newAggregation(matchOperation, lookupOperation, lookupOperation2, unwind("extra"));

        List<Customer> aggregated = mongoTemplate.aggregate(aggregation, CUSTOMERS, Customer.class).getMappedResults();
        assertEquals(1, aggregated.size());
        assertEquals(2, aggregated.get(0).records.size());
        assertEquals("record1", aggregated.get(0).records.get(0).record);
        assertEquals("extra1", aggregated.get(0).extra.data);
    }
}
