package com.kenzie.appserver.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Container {
    private final String containerName;
    private List<Item> itemList;

    public Container(String containerName) {
        this.containerName = containerName;
        this.itemList = new ArrayList<>();
    }

    public Container(String containerName, List<Item> itemList){
        this.containerName = containerName;
        this.itemList = itemList;
    }

    public String getContainerName() {
        return containerName;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    // I am not sure if the below methods will remain here or be put elsewhere (i.e. ItemService), but
    // for now they're here
    public List<Item> addItem(Item item) {
        itemList.add(item);
        return itemList;
    }

    public List<Item> removeItem(Item item) {
        itemList.remove(item);
        return itemList;
    }

    public List<Item> updateItem(Item item) {
        for (Item i : itemList) {
            if (Objects.equals(i.getId(), item.getId())) {
                itemList.set(itemList.indexOf(i), item);
            }
        }

        return itemList;
    }
}
