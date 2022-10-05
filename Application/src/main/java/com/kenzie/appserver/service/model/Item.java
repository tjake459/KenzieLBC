package com.kenzie.appserver.service.model;

import java.util.UUID;

public class Item {
    private final String id;
    private final String genericName;
    private final String brandName;
    private final String weight;
    private final String expirationDate;
    private String fillLevel;
    private String location;

    // bare minimum constructor (generating ID in constructor, item full by default). brandName, weight, &
    // expirationDate must be initialized, even if empty
    public Item(String genericName, String location) {
        this.id = UUID.randomUUID().toString();
        this.genericName = genericName;
        this.brandName = "";
        this.weight = "";
        this.expirationDate = "";
        this.fillLevel = "100";
        this.location = location;
    }

    // full constructor with all parameters including ID; if ID is null/empty, an ID will be generated
    // (this will be the default behavior for adding an item via the site)
    public Item(String id, String genericName, String brandName, String weight,
                String expirationDate, String fillLevel, String location) {
        if (id == null || id.equals("")) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
        this.genericName = genericName;
        this.brandName = brandName;
        this.weight = weight;
        this.expirationDate = expirationDate;
        this.location = location;

        // throws an exception if fillLevel input is invalid. may change this exception to a custom one later
        // (FillLevelException?)
        if (Integer.parseInt(fillLevel) > 100 || Integer.parseInt(fillLevel) < 0) {
            throw new IllegalArgumentException("Fill level cannot be greater than 100 or less than 0!");
        } else {
            this.fillLevel = fillLevel;
        }
    }

    public String getId() {
        return id;
    }

    public String getGenericName() {
        return genericName;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getWeight() {
        return weight;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(String fillLevel) {
        if (Integer.parseInt(fillLevel) > 100 || Integer.parseInt(fillLevel) < 0) {
            throw new IllegalArgumentException("Fill level cannot be greater than 100 or less than 0!");
        } else {
            this.fillLevel = fillLevel;
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
