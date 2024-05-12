package com.glowingsoft.carplaterecognizer.ui;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.UUID;


public class CarPlate implements Serializable {
    private String plate;
    private String region;
    private String car;
    private String id;

    private String imagePath; // Assuming imagePath is the field storing the URL/path of the image


    // Konstruktor default diperlukan untuk panggilan DataSnapshot.getValue(CarPlate.class)
    public CarPlate() {
    }

    public CarPlate(String plate, String region, String car) {
        this.id = UUID.randomUUID().toString(); // Generate unique ID
        this.plate = plate;
        this.region = region;
        this.car = car;
    }

    public String getId() {
        return id;
    }

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

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    // Metode untuk memuat gambar ke ImageView menggunakan Picasso
    public void loadImage(Context context, ImageView imageView, String imagePath) {
        // Memuat gambar menggunakan Picasso
        Picasso.with(context).load(imagePath).into(imageView);
    }

    public String getImagePath() {
        return imagePath;
    }

}
