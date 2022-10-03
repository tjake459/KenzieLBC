package com.kenzie.appserver;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.appserver.repositories.model.ItemRecord;
import com.kenzie.appserver.service.model.Item;

// this whole class was mostly made for testing purposes.
public class ItemDao {
    private DynamoDBMapper mapper;

    public ItemDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public ItemRecord addItem(Item item) {
        ItemRecord record = new ItemRecord(item);
        mapper.save(record);
        return record;
    }

    public ItemRecord getItem(String itemId) {
        return mapper.load(ItemRecord.class, itemId);
    }

    public void deleteItem(String itemId) {
        ItemRecord itemToDelete = mapper.load(ItemRecord.class, itemId);
        mapper.delete(itemToDelete);
    }
}
