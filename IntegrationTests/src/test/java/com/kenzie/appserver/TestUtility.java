package com.kenzie.appserver;

import com.kenzie.appserver.controller.model.ItemCreateRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

public class TestUtility {

    private final MockMvc mvc;
    private final QueryUtility queryUtility;

    private final String ITEM_ID = "ID";
    private final String GENERIC_NAME = "GenericName";
    private final String BRAND_NAME = "BrandName";
    private final String WEIGHT = "Weight";
    private final String EXPIRATION_DATE = "Date";
    private final String FILL_LEVEL = "100";
    private final String LOCATION_1 = "Pantry";
    private final String LOCATION_2 = "Refrigerator";
    private final String LOCATION_3 = "Cabinet";

    private int counter = 1;

    // I can make this mock data more versatile if needed (i.e. different item locations to test sorting), but this is
    // a basic version.
    public TestUtility(MockMvc mvc, QueryUtility queryUtility) {
        this.mvc = mvc;
        this.queryUtility = queryUtility;
    }

    public void createTestDataSet() throws Exception {
        List<ItemCreateRequest> requestList = createRequestList();

        for (ItemCreateRequest request : requestList) {
            queryUtility.itemControllerClient.addNewItem(request);
        }

        System.out.println("Test data created!");
    }

    public void cleanUpTestDataSet() throws Exception {
        for (int i = 1; i < 11; i++) {
            queryUtility.itemControllerClient.deleteItem(ITEM_ID + i);
        }
    }

    private ItemCreateRequest createRequest() {

        ItemCreateRequest request = new ItemCreateRequest();
        request.setId(ITEM_ID + counter);
        request.setGenericName(GENERIC_NAME + counter);
        request.setBrandName(BRAND_NAME + counter);
        request.setWeight(WEIGHT);
        request.setExpirationDate(EXPIRATION_DATE);
        request.setFillLevel(FILL_LEVEL);
        request.setLocation(randomizeLocation());

        counter++;

        return request;
    }

    private List<ItemCreateRequest> createRequestList() {
        List<ItemCreateRequest> requestList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            requestList.add(createRequest());
        }

        return requestList;
    }

    private String randomizeLocation() {
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0: return LOCATION_1;
            case 1: return LOCATION_2;
            case 3: return LOCATION_3;
        }
        return LOCATION_3;
    }
}
