package com.hfad.preguntasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private final String EMAIL = "grupo6@ug.com";
    private final String PASSWORD = "secret";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get email and password EditText ref
        EditText emailEditText = findViewById(R.id.input_email);
        EditText passwordEditText = findViewById(R.id.input_password);

        Button loginButton = findViewById(R.id.login_btn);
        Button registerButton = findViewById(R.id.go_to_register_btn);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(emailEditText.getText());
                String password = String.valueOf(passwordEditText.getText());
                // check empty values
                if ( email.isEmpty() || password.isEmpty() ) {
                    fireToast("Completar los campos, email y contraseña");
                    return;
                }

                if (!email.equals(EMAIL) || !password.equals(PASSWORD)) {
                    fireToast("El email o la contraseña son incorrectos");
                    return;
                }

                // navigate to preguntas activity
                Intent mainActivity = new Intent(view.getContext(), MainActivity.class);
                mainActivity.putExtra("email", email);
                mainActivity.putExtra("password", password);
                startActivity( mainActivity );
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerActivity = new Intent(view.getContext(), Register.class);
                startActivity( registerActivity );
            }
        });


    }

    private void fireToast( String message ) {
        Toast.makeText(
                getApplicationContext(),
                message,
                Toast.LENGTH_SHORT
        ).show();
    }
}