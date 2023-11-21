package com.example.examenfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Agregar extends AppCompatActivity {

    EditText marca, retiro, estado, imgURL;
    Button btnAdd, btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        marca = findViewById(R.id.marcaText);
        retiro = findViewById(R.id.retiroText);
        estado = findViewById(R.id.estadoText);
        imgURL = findViewById(R.id.img1);

        btnAdd = findViewById(R.id.btn_agregar);
        btnAtras = findViewById(R.id.btn_volver);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDatos();
            }
        });
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void insertarDatos() {
        Map<String, Object> map = new HashMap<>();
        map.put("Marca", marca.getText().toString());
        map.put("Retiro", retiro.getText().toString());
        map.put("Estado", estado.getText().toString());
        map.put("imgURL", imgURL.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Productos").push()
                .setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Agregar.this, "Insertado Correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Agregar.this, "Error al Insertar", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}