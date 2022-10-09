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

    // the below 2 tests are for manual testing the successful population and deletion of test data. when we're done
    // troubleshooting the repo, we can delete them.
    @Test
    public void testData_createSuccessful() throws Exception {
        testUtil.createTestDataSet();
    }

    @Test
    public void testData_cleanUpSuccessful() throws Exception {
        testUtil.cleanUpTestDataSet();
    }

    // this test is failing, returning status 404, even though the test item is populating in dynamodb.
    // must investigate why
    @Test
    public void getById_Exists() throws Exception {
        String genericName = "testName";
        String location = "testLocation";

        Item item = new Item(genericName, location);
        queryUtility.itemControllerClient.addNewItem(createRequestFromItem(item));
        queryUtility.itemControllerClient.getItem(item.getId())
                        .andExpect(status().isOk());
    }

    // refactor to use queryutility
    @Test
    public void createExample_CreateSuccessful() throws Exception {
        String name = mockNeat.strings().valStr();

        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();


        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/items")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(itemCreateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("genericName")
                        .value(is(name)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getItemsInContainer_withValidContainer_returnsTrue(){

    }

    @Test
    public void addNewItem_withValidRequest_returnsTrue(){

    }

    @Test
    public void deleteItem_withValidItem_returnsTrue() {

    }

    @Test
    public void updateItem_withValidItem_returnsTrue(){

    }
}