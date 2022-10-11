package com.kenzie.appserver.utilities;

import com.kenzie.appserver.controller.model.ItemCreateRequest;
import com.kenzie.appserver.controller.model.ItemResponse;
import com.kenzie.appserver.repositories.model.ItemRecord;
import com.kenzie.appserver.service.model.Item;

public class ConverterUtility {

    public static Item convertRequestIntoItem(ItemCreateRequest request) {
        return new Item(request.getId(), request.getGenericName(), request.getBrandName(), request.getWeight(),
                request.getExpirationDate(), request.getFillLevel(),
                request.getLocation());
    }

    public static ItemResponse convertItemIntoResponse(Item item) {
        ItemResponse response = new ItemResponse();
        response.setId(item.getId());
        response.setGenericName(item.getGenericName());
        response.setBrandName(item.getBrandName());
        response.setWeight(item.getWeight());
        response.setExpirationDate(item.getExpirationDate());
        response.setFillLevel(String.valueOf(item.getFillLevel()));
        response.setLocation(item.getLocation());
        return response;
    }

    public static Item createItemFromRecord(ItemRecord record) {
        return new Item(record.getId(),
                record.getGenericName(),
                record.getBrandName(),
                record.getWeight(),
                record.getExpirationDate(),
                record.getFillLevel(),
                record.getLocation());
    }

    public static ItemCreateRequest createRequestFromItem(Item item) {
        ItemCreateRequest request = new ItemCreateRequest();
        request.setId(item.getId());
        request.setGenericName(item.getGenericName());
        request.setBrandName(item.getBrandName());
        request.setWeight(item.getWeight());
        request.setExpirationDate((item.getExpirationDate()));
        request.setFillLevel(item.getFillLevel());
        request.setLocation(item.getLocation());
        return request;
    }

    public static ItemRecord createRecordFromItem(Item item) {
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
