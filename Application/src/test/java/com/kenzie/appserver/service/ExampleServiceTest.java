package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ExampleRepository;
import com.kenzie.appserver.repositories.model.ItemRecord;
import com.kenzie.appserver.service.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExampleServiceTest {
    private ExampleRepository exampleRepository;
    private ExampleService exampleService;

    @BeforeEach
    void setup() {
        exampleRepository = mock(ExampleRepository.class);
        exampleService = new ExampleService(exampleRepository);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findById() {
        // GIVEN
        String id = randomUUID().toString();

        ItemRecord record = new ItemRecord();
        record.setId(id);
        record.setGenericName("itemName");

        // WHEN
        when(exampleRepository.findById(id)).thenReturn(Optional.of(record));
        Item item = exampleService.findById(id);

        // THEN
        Assertions.assertNotNull(item, "The object is returned");
        Assertions.assertEquals(record.getId(), item.getId(), "The id matches");
        Assertions.assertEquals(record.getGenericName(), item.getGenericName(), "The generic name matches");
    }

    @Test
    void findByConcertId_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(exampleRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Item item = exampleService.findById(id);

        // THEN
        Assertions.assertNull(item, "The item is null when not found");
    }

}
