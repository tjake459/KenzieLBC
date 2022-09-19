package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ExampleCreateRequest;
import com.kenzie.appserver.controller.model.ExampleResponse;
import com.kenzie.appserver.service.ExampleService;

import com.kenzie.appserver.service.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/example")
public class ExampleController {

    private ExampleService exampleService;

    ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExampleResponse> get(@PathVariable("id") String id) {

        Item item = exampleService.findById(id);
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
        exampleService.addNewExample(item);

        ExampleResponse exampleResponse = new ExampleResponse();
        exampleResponse.setGenericName(item.getGenericName());
        exampleResponse.setLocation(item.getLocation());

        return ResponseEntity.created(URI.create("/item/" + exampleResponse.getId())).body(exampleResponse);
    }
}
