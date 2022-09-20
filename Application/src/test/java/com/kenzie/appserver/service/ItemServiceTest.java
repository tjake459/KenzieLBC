package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ItemRepository;
import com.kenzie.appserver.repositories.model.ItemRecord;
import com.kenzie.appserver.service.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class ItemServiceTest {
    private ItemRepository itemRepository;
    private ItemService itemService;


    @BeforeEach
    void setup() {
        itemRepository = mock(ItemRepository.class);
        itemService = new ItemService(itemRepository);
    }

    /**
     * ------------------------------------------------------------------------
     * exampleService.findById
     * ------------------------------------------------------------------------
     **/

    @Test
    void getItemsInContainer() {
        // GIVEN
        String location = randomUUID().toString();


        Item item = new Item("name", location);
        Item item2 = new Item("name2", location);
        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        // WHEN
        when(itemRepository.getItemsInContainer(location)).thenReturn(items);


        // THEN
        Assertions.assertEquals(items.size(), 2);
    }

    @Test
    void addItem() {
        //GIVEN
        Item item = new Item("name", "location");

        ArgumentCaptor<ItemRecord> itemRecordArgumentCaptor = ArgumentCaptor.forClass(ItemRecord.class);

        //WHEN
        Item returnedItem = itemService.addItem(item);

        //THEN
        Assertions.assertNotNull(returnedItem);

        verify(itemRepository).save(itemRecordArgumentCaptor.capture());

        ItemRecord record = itemRecordArgumentCaptor.getValue();

        Assertions.assertNotNull(record, "The item record has returned");
        Assertions.assertEquals(record.getGenericName(), item.getGenericName(), "The item generic name matches");
        Assertions.assertEquals(record.getLocation(), item.getLocation(), "The item locations match");

    }

    @Test
    void updateItem() {
        //GIVEN
        ItemRecord itemRecord = new ItemRecord();
        itemRecord.setLocation("location");
        itemRecord.setGenericName("name");


        Item item = new Item(itemRecord.getGenericName(),itemRecord.getLocation());

        //WHEN
        item.setLocation("new location");
        itemService.updateItem(item);



        //THEN

        Assertions.assertEquals("new location", item.getLocation(), "The item locations has been updated");

    }

    @Test
    void deleteItem() {
        //GIVEN
        Item item = new Item("name", "location");
        itemService.addItem(item);

        //WHEN
        itemService.deleteItem(item.getId());



        //THEN
        Assertions.assertFalse(itemRepository.existsById(item.getId()));



    }

}


