package com.kenzie.appserver.service;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.appserver.ItemDao;
import com.kenzie.appserver.repositories.ItemRepository;
import com.kenzie.appserver.repositories.model.ItemRecord;
import com.kenzie.appserver.service.model.Item;
import com.kenzie.appserver.utilities.DynamoDbClientProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.kenzie.appserver.utilities.ConverterUtility.createItemFromRecord;
import static com.kenzie.appserver.utilities.ConverterUtility.createRecordFromItem;

@Service
public class ItemService {

    DynamoDBMapper mapper;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        mapper = new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
        this.itemRepository = itemRepository;
    }

    public List<Item> getItemsInContainer(String location) {
        List<Item> items = new ArrayList<>();
        itemRepository
                .findByLocation(location)
                .forEach(item -> items.add(new Item(item.getId(), item.getGenericName(), item.getBrandName(), item.getWeight(),
                        item.getExpirationDate(), item.getFillLevel(), item.getLocation())));
        return items;
    }

    public List<Item> getAllItems() {
        List<ItemRecord> records = new ArrayList<>(itemRepository.findAll());
        List<Item> items = new ArrayList<>();
        for (ItemRecord record : records) {
            items.add(createItemFromRecord(record));
        }
        return items;
    }

    public Item addItem(Item item) {
        while (getItem(item.getId()) != null) {
            item.setId(Item.generateId(item.getGenericName()));
        }
        ItemRecord itemRecord = createRecordFromItem(item);
        itemRepository.save(itemRecord);
        return item;
    }

    public Item updateItem(Item item) {
        ItemRecord itemRecord = createRecordFromItem(item);
        itemRepository.save(itemRecord);
        return item;
    }

    public void deleteItem(String itemId) {
        itemRepository.deleteById(itemId);
    }


    public Item getItem(String itemId) {
        return itemRepository
                .findById(itemId)
                .map(item -> new Item(
                        item.getId(),
                        item.getGenericName(),
                        item.getBrandName(),
                        item.getWeight(),
                        item.getExpirationDate(),
                        item.getFillLevel(),
                        item.getLocation()))
                .orElse(null);
    }
}
