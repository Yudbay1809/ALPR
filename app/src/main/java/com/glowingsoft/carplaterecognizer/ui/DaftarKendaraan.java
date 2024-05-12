package com.glowingsoft.carplaterecognizer.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.glowingsoft.carplaterecognizer.R;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;


public class DaftarKendaraan extends AppCompatActivity implements CustomAdapter.CarPlateClickListener {

    private ListView listView;
    private DatabaseReference databaseReference;
    private ArrayList<CarPlate> carPlateList;
    private CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kendaraan);

        listView = findViewById(R.id.listView);
        databaseReference = FirebaseDatabase.getInstance().getReference("plates");
        carPlateList = new ArrayList<>();
        customAdapter = new CustomAdapter(this, carPlateList, this);
        listView.setAdapter(customAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carPlateList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CarPlate carPlate = snapshot.getValue(CarPlate.class);
                    carPlateList.add(carPlate);
                }
                customAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DaftarKendaraan.this, "Failed to read data.", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle item click events for edit and delete
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            CarPlate carPlate = carPlateList.get(position);
            // Handle edit and delete operations here
            // For example, show a dialog with options to edit or delete
            showEditDeleteDialog(carPlate, position);
        });
    }
    private void showEditDeleteDialog(CarPlate carPlate, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an action")
                .setItems(new CharSequence[]{"Edit", "Delete"}, (dialog, which) -> {
                    if (which == 0) {
                        // Edit option selected
                        // Launch edit activity with data
                        launchEditActivity(carPlate);
                    } else {
                        // Delete option selected
                        // Show confirmation dialog before deleting
                        showDeleteConfirmationDialog(carPlate, position);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void showDeleteConfirmationDialog(CarPlate carPlate, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Delete option selected
                    // Delete item from Firebase Realtime Database
                    deleteItem(carPlate, position);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }


    private void deleteItem(CarPlate carPlate, int position) {
        String carPlateId = carPlate.getId();
        if (carPlateId != null) {
            // Delete item from Firebase Realtime Database
            databaseReference.child(carPlateId).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        Log.d("DaftarKendaraan", "Item deleted successfully");
                        // Delete item from local list
                        carPlateList.remove(position);
                        // Update view by calling notifyDataSetChanged() on the adapter
                        customAdapter.notifyDataSetChanged();
                        Toast.makeText(DaftarKendaraan.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("DaftarKendaraan", "Failed to delete item", e);
                        Toast.makeText(DaftarKendaraan.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "CarPlate ID is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchEditActivity(CarPlate carPlate) {
        Intent intent = new Intent(this, EditKendaraan.class);
        intent.putExtra("carPlate", carPlate); // Mengirim objek CarPlate
        startActivity(intent);
    }



    @Override
    public void onEditClick(int position) {
        CarPlate carPlate = carPlateList.get(position);
        // Handle edit click action, such as launching edit activity with data
        Intent intent = new Intent(this, EditKendaraan.class);
        intent.putExtra("carPlate", carPlate); // Pass the CarPlate object to the edit activity
        startActivity(intent);
    }


    @Override
    public void onDeleteClick(int position) {
        CarPlate carPlate = carPlateList.get(position);
        // Periksa apakah item ada di dalam daftar
        if (carPlate != null) {
            // Ambil ID dari DataSnapshot yang sesuai dengan item yang dipilih
            String carPlateId = carPlateList.get(position).getId();
            if (carPlateId != null) {
                // Hapus data dari Firebase Realtime Database
                databaseReference.child(carPlateId).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(DaftarKendaraan.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(DaftarKendaraan.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "Invalid item", Toast.LENGTH_SHORT).show();
            }
        }
    }
}