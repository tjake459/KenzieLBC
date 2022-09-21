package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.ItemRepository;
import com.kenzie.appserver.repositories.model.ItemRecord;
import com.kenzie.appserver.service.model.Item;
import com.sun.tools.javac.comp.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;


    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;

    }


    public List<Item> getItemsInContainer(String location) {
        List<Item> items = new ArrayList<>();
        itemRepository
                .getItemsInContainer(location)
                .forEach(item -> items.add(new Item(item.getGenericName(), item.getLocation(), item.getWeight(),
                        item.getExpirationDate(), item.getFillLevel(), item.getBrandName())));
        return items;
    }

    public Item addItem(Item item) {
        ItemRecord itemRecord = new ItemRecord(item);
        itemRepository.save(itemRecord);
        return item;
    }

    public Item updateItem(Item item) {
        ItemRecord itemRecord = new ItemRecord(item);
        itemRepository.save(itemRecord);
        return item;
    }

    public void deleteItem(String itemId) {
        itemRepository.deleteById(itemId);
    }

    //create exception in update

    public Item getItem(String itemId) {
        Item itemFromBackEnd = itemRepository
                .findById(itemId)
                .map(item -> new Item(
                        item.getGenericName(),
                        item.getBrandName(),
                        item.getWeight(),
                        item.getExpirationDate(),
                        item.getFillLevel(),
                        item.getLocation()))
                .orElse(null);
        return itemFromBackEnd;
    }

}
