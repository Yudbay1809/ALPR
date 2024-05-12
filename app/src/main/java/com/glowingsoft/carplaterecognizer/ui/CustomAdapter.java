package com.glowingsoft.carplaterecognizer.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.glowingsoft.carplaterecognizer.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<CarPlate> {
    private Context context;
    private ArrayList<CarPlate> carPlateList;

    private CarPlateClickListener carPlateClickListener;


    public interface CarPlateClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }


    public CustomAdapter(Context context, ArrayList<CarPlate> carPlateList, CarPlateClickListener listener) {
        super(context, R.layout.activity_daftar_kendaraan, carPlateList);
        this.context = context;
        this.carPlateList = carPlateList;
        this.carPlateClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_daftar_kendaraan, null);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textViewPlate = view.findViewById(R.id.textViewPlate);
        TextView textViewRegion = view.findViewById(R.id.textViewRegion);
        TextView textViewCar = view.findViewById(R.id.textViewCar);
        Button buttonEdit = view.findViewById(R.id.buttonEdit);
        Button buttonDelete = view.findViewById(R.id.buttonDelete);

        CarPlate carPlate = carPlateList.get(position);

        // Set data to views
        // imageView.setImageURI(carPlate.getImageUri());
        // Replace setImageURI with appropriate method to load image
    // Set data to views
        String imagePath = carPlate.getImagePath(); // Retrieve the image path
        carPlate.loadImage(context, imageView, imagePath); // Load image using Picasso        textViewPlate.setText("Plate Number: " + carPlate.getPlate());
        textViewPlate.setText("Plate: " + carPlate.getPlate());
        textViewRegion.setText("Region: " + carPlate.getRegion());
        textViewCar.setText("Car Type: " + carPlate.getCar());

        // Set click listeners for edit and delete buttons
        buttonEdit.setOnClickListener(v -> {
            if (carPlateClickListener != null) {
                carPlateClickListener.onEditClick(position);
            }
        });

        buttonDelete.setOnClickListener(v -> {
            if (carPlateClickListener != null) {
                carPlateClickListener.onDeleteClick(position);
            }
        });

        return view;
    }

}
