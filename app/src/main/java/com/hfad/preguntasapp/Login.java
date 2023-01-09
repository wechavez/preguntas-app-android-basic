package com.hfad.preguntasapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar( toolbar );


        // Go to main activity if user is already logged in with shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        if ( sharedPreferences.contains("email") && sharedPreferences.contains("password") ) {
            String email = sharedPreferences.getString("email", null);
            String password = sharedPreferences.getString("password", null);

            if ( checkLogin( email, password ) ) {
                goToMainActivity( email, password );
                return;
            }
        }

        // get email and password EditText ref
        EditText emailEditText = findViewById(R.id.input_email);
        EditText passwordEditText = findViewById(R.id.input_password);

        Button loginButton = findViewById(R.id.login_btn);
        Button registerButton = findViewById(R.id.go_to_register_btn);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get email and password from EditText
                String email = String.valueOf(emailEditText.getText());
                String password = String.valueOf(passwordEditText.getText());

                // Check credentials
                if ( !checkLogin(email, password) ) return;

                // Ask user if he wants to save credentials
                new AlertDialog.Builder(Login.this)
                    .setMessage(R.string.dialog_message)
                    .setTitle(R.string.dialog_title)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();

                            saveSharedPreferences(email, password);

                            goToMainActivity(email, password);
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            goToMainActivity(email, password);
                        }
                    }).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.principal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.about_us_item:
                Intent integrantesActivityIntent = new Intent(this, Integrantes.class);
                startActivity( integrantesActivityIntent );
                return true;

            default:
                return true;
        }
    }

    private void goToMainActivity(String email, String password) {
        Intent mainActivity = new Intent(Login.this, MainActivity.class);
        mainActivity.putExtra("email", email);
        mainActivity.putExtra("password", password);
        startActivity( mainActivity );
    }

    private void saveSharedPreferences(String email, String password) {
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();
    }

    private boolean checkLogin(String email, String password) {
        if ( email.isEmpty() || password.isEmpty() ) {
            fireToast("Completar los campos, email y contraseña");
            return false;
        }

        if (!email.equals(EMAIL) || !password.equals(PASSWORD)) {
            fireToast("El email o la contraseña son incorrectos");
            return false;
        }

        return true;
    }

    private void fireToast(String message ) {
        Toast.makeText(
                getApplicationContext(),
                message,
                Toast.LENGTH_SHORT
        ).show();
    }
}