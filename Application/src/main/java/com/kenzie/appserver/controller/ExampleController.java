package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ExampleCreateRequest;
import com.kenzie.appserver.controller.model.ExampleResponse;
import com.kenzie.appserver.service.ItemService;

import com.kenzie.appserver.service.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/example")
public class ExampleController {

    private ItemService itemService;

    ExampleController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExampleResponse> get(@PathVariable("id") String id) {

        Item item = itemService.getItem(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        ExampleResponse exampleResponse = new ExampleResponse();
        exampleResponse.setId(item.getId());
        exampleResponse.setName(item.getGenericName());
        return ResponseEntity.ok(exampleResponse);
    }

    @PostMapping
    public ResponseEntity<ExampleResponse> addNewConcert(@RequestBody ExampleCreateRequest exampleCreateRequest) {
        Item item = new Item("testName", "testLocation");
        itemService.addItem(item);

        ExampleResponse exampleResponse = new ExampleResponse();
        exampleResponse.setGenericName(item.getGenericName());
        exampleResponse.setLocation(item.getLocation());

        return ResponseEntity.created(URI.create("/item/" + exampleResponse.getId())).body(exampleResponse);
    }
}
