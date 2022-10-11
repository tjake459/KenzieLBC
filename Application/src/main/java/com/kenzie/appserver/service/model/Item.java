package com.kenzie.appserver.service.model;

public class Item {
    private String id;
    private final String genericName;
    private final String brandName;
    private final String weight;
    private final String expirationDate;
    private String fillLevel;
    private String location;

    public Item(String genericName, String location) {
        this.id = generateId(genericName);
        this.genericName = genericName;
        this.brandName = "N/A";
        this.weight = "N/A";
        this.expirationDate = "N/A";
        this.fillLevel = "100";
        this.location = location;
    }

    public Item(String id, String genericName, String brandName, String weight,
                String expirationDate, String fillLevel, String location) {
        if (id == null || id.equals("")) {
            this.id = generateId(genericName);
        } else {
            this.id = id;
        }
        this.genericName = genericName;
        this.brandName = brandName;
        this.weight = weight;
        this.expirationDate = expirationDate;
        this.location = location;

        if (Integer.parseInt(fillLevel) > 100 || Integer.parseInt(fillLevel) < 0) {
            throw new IllegalArgumentException("Fill level cannot be greater than 100 or less than 0!");
        } else {
            this.fillLevel = fillLevel;
        }
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

    public static String generateId(String genericName) {
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append((int) (Math.random() * 10));
        }
        return genericName.substring(0, 4) + id;
    }
}
