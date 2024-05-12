package com.glowingsoft.carplaterecognizer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.glowingsoft.carplaterecognizer.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditKendaraan extends AppCompatActivity {

    private EditText editTextPlate;
    private EditText editTextRegion;
    private EditText editTextCar;
    private Button buttonSave;

    private CarPlate carPlate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kendaraan);

        Intent intent = getIntent();
        carPlate = (CarPlate) intent.getSerializableExtra("carPlate");


        // Inisialisasi UI
        editTextPlate = findViewById(R.id.editTextPlate);
        editTextRegion = findViewById(R.id.editTextRegion);
        editTextCar = findViewById(R.id.editTextCar);
        buttonSave = findViewById(R.id.buttonSave);


        // Isi UI dengan data yang ada pada objek CarPlate
        if (carPlate != null) {
            editTextPlate.setText(carPlate.getPlate());
            editTextRegion.setText(carPlate.getRegion());
            editTextCar.setText(carPlate.getCar());
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tangkap input dari elemen UI
                String plate = editTextPlate.getText().toString().trim();
                String region = editTextRegion.getText().toString().trim();
                String car = editTextCar.getText().toString().trim();

                if (TextUtils.isEmpty(plate) || TextUtils.isEmpty(region) || TextUtils.isEmpty(car)) {
                    Toast.makeText(EditKendaraan.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Update the existing CarPlate object
                if (carPlate != null) {
                    carPlate.setPlate(plate);
                    carPlate.setRegion(region);
                    carPlate.setCar(car);

                    // Simpan perubahan kembali ke database Firebase Realtime
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("plates");
                    databaseReference.child(carPlate.getId()).setValue(carPlate)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(EditKendaraan.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Tampilkan pesan gagal jika penyimpanan data gagal
                                    Toast.makeText(EditKendaraan.this, "Failed to update data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}