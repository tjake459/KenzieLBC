package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.kenzie.appserver.service.model.Item;

import java.util.Objects;

@DynamoDBTable(tableName = "PantryTable")
public class ItemRecord {
    public static final String LOCATION_INDEX = "LocationIndex";

    private String id;
    private String genericName;
    private String brandName;
    private String weight;
    private String expirationDate;
    private String fillLevel;
    private String location;

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = LOCATION_INDEX, attributeName = "id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "genericName")
    public String getGenericName() {
        return genericName;
    }

    @DynamoDBAttribute(attributeName = "brandName")
    public String getBrandName() {
        return brandName;
    }

    @DynamoDBAttribute(attributeName = "weight")
    public String getWeight() {
        return weight;
    }

    @DynamoDBAttribute(attributeName = "expirationDate")
    public String getExpirationDate() {
        return expirationDate;
    }

    @DynamoDBAttribute(attributeName = "fillLevel")
    public String getFillLevel() {
        return fillLevel;
    }

    @DynamoDBAttribute(attributeName = "location")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = LOCATION_INDEX, attributeName = "location")
    public String getLocation() {
        return location;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setFillLevel(String fillLevel) {
        if (Integer.parseInt(fillLevel) > 100 || Integer.parseInt(fillLevel) < 0) {
            throw new IllegalArgumentException("Fill level cannot be greater than 100 or less than 0!");
        } else {
            this.fillLevel = fillLevel;
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemRecord itemRecord = (ItemRecord) o;
        return Objects.equals(id, itemRecord.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
