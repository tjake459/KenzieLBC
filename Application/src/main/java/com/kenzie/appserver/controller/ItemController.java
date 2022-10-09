package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ItemCreateRequest;
import com.kenzie.appserver.controller.model.ItemResponse;
import com.kenzie.appserver.service.ItemService;

import com.kenzie.appserver.service.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.kenzie.appserver.utilities.ConverterUtility.convertItemIntoResponse;
import static com.kenzie.appserver.utilities.ConverterUtility.convertRequestIntoItem;

@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable("id") String id) {

        Item item = itemService.getItem(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertItemIntoResponse(item));

    }

    @GetMapping("/containers/{container}")
    public ResponseEntity<List<ItemResponse>> getItemsInContainer(@PathVariable("container") String container) {

        //Get a list of Items in the identified container using the ItemService
        List<Item> items = itemService.getItemsInContainer(container);
        if (items == null || items.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        // Transform the List of Items into a List of ItemResponses
        List<ItemResponse> response = new ArrayList<>();
        items.forEach(item -> { response.add(convertItemIntoResponse(item)); });
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> addNewItem(@RequestBody ItemCreateRequest itemCreateRequest) {
        //Convert the ItemCreateRequest into an Item
        Item item = convertRequestIntoItem(itemCreateRequest);
        try {
            itemService.addItem(item);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        //Convert the Item into an ItemResponse
        ItemResponse itemResponse = convertItemIntoResponse(item);
//        return ResponseEntity.created(URI.create("/item/" + itemResponse.getId())).body(itemResponse);
        return ResponseEntity.ok().body(itemResponse);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity deleteItem(@PathVariable("itemId") String itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity updateItem(@RequestBody ItemCreateRequest request) {
        ItemResponse response = convertItemIntoResponse(itemService.updateItem(convertRequestIntoItem(request)));
        return ResponseEntity.ok(response);
    }

}
