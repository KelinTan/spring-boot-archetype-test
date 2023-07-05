package com.github.kelin.archetype.repository;

import static com.github.kelin.archetype.entity.EntityCollections.CUSTOMERS;
import static com.github.kelin.archetype.entity.EntityCollections.CUSTOMER_EXTRA;
import static com.github.kelin.archetype.entity.EntityCollections.CUSTOMER_RECORDS;
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
import com.github.kelin.archetype.entity.CustomerExtra;
import com.github.kelin.archetype.entity.CustomerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@DataMongoTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();
        mongoTemplate.dropCollection(CustomerRecord.class);
        mongoTemplate.dropCollection(CustomerExtra.class);

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Alice1", "Smith1", 1));
        customers.add(new Customer("Alice2", "Smith2", 2));
        customerRepository.insert(customers);

        List<CustomerRecord> customerRecords = new ArrayList<>();
        customerRecords.add(new CustomerRecord("Alice1", "record1"));
        customerRecords.add(new CustomerRecord("Alice1", "record1-1"));
        customerRecords.add(new CustomerRecord("Alice2", "record2"));
        mongoTemplate.insertAll(customerRecords);

        List<CustomerExtra> customerExtras = new ArrayList<>();
        customerExtras.add(new CustomerExtra("Alice1", "extra1"));
        customerExtras.add(new CustomerExtra("Alice2", "extra2"));
        mongoTemplate.insertAll(customerExtras);
    }

    @Test
    public void findByFirstName() {
        List<Customer> byFirstName = customerRepository.findByFirstName("Alice1");
        assertEquals("Alice1", byFirstName.get(0).getFirstName());
    }

    @Test
    public void findByLastName() {
        Query query = query(where("lastName").is("Smith2"));
        List<Customer> byLastName = mongoTemplate.find(query, Customer.class);
        assertEquals("Smith2", byLastName.get(0).getLastName());
    }

    @Test
    public void findByLastNameAndPage() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Alice1-1", "Smith2", 3));
        customerRepository.insert(customers);

        Query query = query(where("lastName").is("Smith2")).with(Sort.by(Direction.ASC, "position"));
        List<Customer> byLastName = mongoTemplate.find(query, Customer.class);
        assertEquals("Smith2", byLastName.get(0).getLastName());
        assertEquals(2, byLastName.get(0).getPosition());
    }

    @Test
    public void findByLastNameAndSort() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Alice1-1", "Smith2"));
        customerRepository.insert(customers);

        Query query = query(where("lastName").is("Smith2")).skip(1).limit(1);
        List<Customer> byLastName = mongoTemplate.find(query, Customer.class);
        assertEquals("Smith2", byLastName.get(0).getLastName());
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
        assertEquals(2, aggregated.get(0).getRecords().size());
        assertEquals("record1", aggregated.get(0).getRecords().get(0).getRecord());
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
        assertEquals(2, aggregated.get(0).getRecords().size());
        assertEquals("record1", aggregated.get(0).getRecords().get(0).getRecord());
        assertEquals("extra1", aggregated.get(0).getExtra().getData());
    }
}
