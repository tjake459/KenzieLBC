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

@Service
public class ItemService {

    DynamoDBMapper mapper;
    // itemDao and all the calls to itemDao are temporary, if we can get the repository working; they
    // aren't necessary in addition to the repository. for now, it's what works to get things to post to the database
    ItemDao itemDao;
    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        mapper = new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
        itemDao = new ItemDao(mapper);
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
        ItemRecord itemRecord = createRecordFromItem(item);
//        itemRepository.save(itemRecord);
        itemDao.addItem(item);
        return item;
    }

    public Item updateItem(Item item) {
        ItemRecord itemRecord = createRecordFromItem(item);
        itemRepository.save(itemRecord);
        return item;
    }

    // until the repository is working correctly, this method will actually always throw an exception if
    // the call to the repository weren't commented out, because nothing is saving to the repository, and therefore
    // trying to delete something from the repository that isn't there will throw an exception
    public void deleteItem(String itemId) {
//        itemRepository.deleteById(itemId);
        itemDao.deleteItem(itemId);
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

    private Item createItemFromRecord(ItemRecord record) {
        Item item = new Item(record.getId(),
                                record.getGenericName(),
                                record.getBrandName(),
                                record.getWeight(),
                                record.getExpirationDate(),
                                record.getFillLevel(),
                                record.getLocation());

        return item;
    }

}
