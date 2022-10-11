package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.QueryUtility;
import com.kenzie.appserver.TestUtility;
import com.kenzie.appserver.controller.model.ItemCreateRequest;
import com.kenzie.appserver.service.model.Item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.kenzie.appserver.utilities.ConverterUtility.createRequestFromItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemControllerTest {
    @Autowired
    private MockMvc mvc;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    private QueryUtility queryUtility;
    private TestUtility testUtil;

    @BeforeAll
    public void setup() {
        queryUtility = new QueryUtility(mvc);
        testUtil = new TestUtility(mvc, queryUtility);
    }

    @Test
    public void getById_Exists() throws Exception {
        String genericName = "testName";
        String location = "testLocation";

        Item item = new Item(genericName, location);
        queryUtility.itemControllerClient.addNewItem(createRequestFromItem(item));
        queryUtility.itemControllerClient.getItem(item.getId())
                        .andExpect(status().isOk());
    }

    @Test
    public void createExample_CreateSuccessful() throws Exception {
        ItemCreateRequest itemCreateRequest = testUtil.createSingleRequest();

        mapper.registerModule(new JavaTimeModule());

        queryUtility.itemControllerClient.addNewItem(itemCreateRequest)
                        .andExpect(jsonPath("id")
                                .exists())
                        .andExpect(jsonPath("genericName")
                                .value(is(itemCreateRequest.getGenericName())))
                        .andExpect(status().is2xxSuccessful());

    }
    
}