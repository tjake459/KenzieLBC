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
import java.util.Optional;

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


        Item item1 = new Item("name", location);
        Item item2 = new Item("id1", "name2","brand","weight","date","50",
                "location2");
        ItemRecord record1 = createRecordFromItem(item1);
        ItemRecord record2 = createRecordFromItem(item2);
        List<ItemRecord> records = new ArrayList<>();
        records.add(record1);
        records.add(record2);


        // WHEN
        when(itemRepository.findByLocation(location)).thenReturn(records);


        // THEN
        Assertions.assertEquals(records.size(), 2);
        Assertions.assertEquals(record1.getLocation(),records.get(0).getLocation());
        Assertions.assertEquals(record1.getBrandName(),records.get(0).getBrandName());
        Assertions.assertEquals(record2.getGenericName(),records.get(1).getGenericName());
        Assertions.assertEquals(record2.getBrandName(),records.get(1).getBrandName());
        Assertions.assertEquals(record2.getWeight(),records.get(1).getWeight());
        Assertions.assertEquals(record2.getExpirationDate(),records.get(1).getExpirationDate());
        Assertions.assertEquals(record2.getFillLevel(),records.get(1).getFillLevel());
        Assertions.assertEquals(record2.getLocation(),records.get(1).getLocation());

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
        Item item = new Item("name","location");
        itemService.addItem(item);

        ArgumentCaptor<ItemRecord> itemRecordArgumentCaptor = ArgumentCaptor.forClass(ItemRecord.class);


        //WHEN
        item.setLocation("new location");
        Item returnedItem = itemService.updateItem(item);


        //THEN
        Assertions.assertNotNull(returnedItem);

        verify(itemRepository, times(2)).save(itemRecordArgumentCaptor.capture());

        ItemRecord record = itemRecordArgumentCaptor.getValue();
        System.out.println(record.getLocation());

        Assertions.assertEquals(record.getLocation(), item.getLocation(), "The item locations has been updated");

    }

    @Test
    void deleteItem() {
        //GIVEN
        Item item = new Item("name", "location");


        //WHEN
        itemService.deleteItem(item.getId());


        //THEN
        Assertions.assertFalse(itemRepository.existsById(item.getId()));
    }

    @Test
    void getItemById() {
        // GIVEN
        String itemId = randomUUID().toString();

        ItemRecord record = new ItemRecord();
        record.setId(itemId);
        record.setLocation("location");
        record.setGenericName("name");
        record.setWeight("weight");
        record.setFillLevel("50");
        record.setBrandName("brandname");
        record.setExpirationDate("date");
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(record));
        // WHEN
        Item item = itemService.getItem(itemId);

        // THEN
        Assertions.assertNotNull(item, "The item is returned");
        Assertions.assertEquals(record.getGenericName(), item.getGenericName(), "The item name matches");
        Assertions.assertEquals(record.getExpirationDate(), item.getExpirationDate(), "The item expiration date matches");
        Assertions.assertEquals(record.getLocation(), item.getLocation(), "the item location matches");
        Assertions.assertEquals(record.getWeight(), item.getWeight(), "the item weight matches");
        Assertions.assertEquals(record.getFillLevel(), item.getFillLevel(), "the item fill level matches");
        Assertions.assertEquals(record.getBrandName(), item.getBrandName(), "the item brand name matches");

    }

    private ItemRecord createRecordFromItem(Item item) {
        ItemRecord record = new ItemRecord();
        record.setId(item.getId());
        record.setGenericName(item.getGenericName());
        record.setBrandName(item.getBrandName());
        record.setWeight(item.getWeight());
        record.setFillLevel(item.getFillLevel());
        record.setExpirationDate(item.getExpirationDate());
        record.setLocation(item.getLocation());

        return record;
    }

}


