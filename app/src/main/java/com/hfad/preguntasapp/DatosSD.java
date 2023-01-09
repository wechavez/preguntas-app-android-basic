package com.hfad.preguntasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DatosSD extends AppCompatActivity {

    // Get text views
    TextView firstNameText;
    TextView lastNameText;
    TextView emailText;
    TextView passwordText;
    TextView phoneText;
    TextView genreText;
    TextView countryText;
    TextView birthDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_sd);

        Button goBackRegisterButton = findViewById(R.id.go_back_register);

        // Get text views
        firstNameText = findViewById(R.id.firstname);
        lastNameText = findViewById(R.id.lastname);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        phoneText = findViewById(R.id.phone);
        genreText = findViewById(R.id.genre);
        countryText = findViewById(R.id.country);
        birthDateText = findViewById(R.id.date);

        goBackRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerActivity = new Intent(view.getContext(), Register.class);
                startActivity( registerActivity );
            }
        });

        loadFromSD();
    }

    // Load from SD
    private void loadFromSD() {
        String filename = "form.txt";

        FileReader fr = null;
        File myExternalFile = new File(getExternalFilesDir(null), filename);

        StringBuilder stringBuilder = new StringBuilder();

        try {
            fr = new FileReader(myExternalFile);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while(line != null){
                stringBuilder.append(line).append('\n');
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ( stringBuilder.toString().isEmpty() ) return;

            String fileContents = stringBuilder.toString();
            String[] fileContentsArray = fileContents.split(", ");

            for (String s : fileContentsArray) {
                String[] keyValue = s.split("=");

                String key = keyValue[0];
                String value = keyValue[1];

                fillContent(key, value);
            }
        }
    }

    private void fillContent(String key, String value) {
        switch (key) {
            case "firstname":
                firstNameText.setText("Nombre: " +value);
                break;
            case "lastname":
                lastNameText.setText("Apellido: " +value);
                break;
            case "email":
                emailText.setText("Email: " +value);
                break;
            case "password":
                passwordText.setText("Contraseña: " +value);
                break;
            case "phone":
                phoneText.setText("Teléfono: " +value);
                break;
            case "genre":
                genreText.setText("Género: " +value);
                break;
            case "country":
                countryText.setText("País: " +value);
                break;
            case "date":
                birthDateText.setText("Fecha de nacimiento: " +value);
                break;
        }
    }
}