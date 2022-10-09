package com.kenzie.appserver;

import com.kenzie.appserver.controller.model.ItemCreateRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class QueryUtility {

    public ItemControllerClient itemControllerClient;

    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public QueryUtility(MockMvc mvc) {
        this.mvc = mvc;
        this.itemControllerClient = new ItemControllerClient();
    }

    public class ItemControllerClient {
        public ResultActions getItem(String id) throws Exception {
            return mvc.perform(get("/items/{id}", id)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions getItemsInContainer(String container) throws Exception {
            return mvc.perform(get("/items/{container}", container)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions addNewItem(ItemCreateRequest request) throws Exception {
            return mvc.perform(post("/items")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)));
        }

        public ResultActions deleteItem(String itemId) throws Exception {
            return mvc.perform(delete("/items/{itemId}", itemId)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions updateItem(ItemCreateRequest request) throws Exception {
            return mvc.perform(put("/items")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)));
        }

    }
}
