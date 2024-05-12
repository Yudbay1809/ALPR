package com.glowingsoft.carplaterecognizer.ui;

public class ImageModel {
    private String plate;
    private String region;
    private String vehicle;
    private String imagePath;
    private String timestamp;

    public ImageModel(String plate, String region, String vehicle, String imagePath, String timestamp) {
        this.plate = plate;
        this.region = region;
        this.vehicle = vehicle;
        this.imagePath = imagePath;
        this.timestamp = timestamp;
    }

    // Getters and setters for each field

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

