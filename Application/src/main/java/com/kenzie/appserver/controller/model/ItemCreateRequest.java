package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ItemCreateRequest {

    @JsonProperty("id")
    private String id;

    @NotEmpty
    @JsonProperty("genericName")
    private String genericName;

    @JsonProperty("brandName")
    private String brandName;

    @Min(0)
    @JsonProperty("weight")
    private String weight;

    @JsonProperty("expirationDate")
    private String expirationDate;

    @JsonProperty("fillLevel")
    private String fillLevel;

    @NotEmpty
    @JsonProperty("location")
    private String location;

    public String getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(String fillLevel) {
        this.fillLevel = fillLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
