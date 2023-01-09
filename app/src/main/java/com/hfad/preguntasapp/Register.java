package com.hfad.preguntasapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Register extends AppCompatActivity {

    private String genreSelected;

    EditText datePickerEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    Spinner countrySpinner;
    EditText emailEditeText;
    EditText passwordEditText;
    EditText phoneEditText;
    RadioGroup genreRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_register);
        setSupportActionBar( toolbar );


        Button goToLoginButton = findViewById(R.id.go_to_login_btn);
        Button registerButton = findViewById(R.id.register_btn);

        datePickerEditText = findViewById(R.id.date_picker);
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        countrySpinner = findViewById(R.id.country);
        emailEditeText = findViewById(R.id.input_email);
        passwordEditText = findViewById(R.id.input_password);
        phoneEditText = findViewById(R.id.phone_number);
        genreRadioGroup = findViewById(R.id.genre);

        Button resetButton = findViewById(R.id.reset_btn);
        Button saveInSDButton = findViewById(R.id.save_sd_btn);

        goToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginActivity = new Intent(view.getContext(), Login.class);
                startActivity( loginActivity );
            }
        });

        datePickerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        final String selectedDate = day + " / " + (month+1) + " / " + year;
                        datePickerEditText.setText( selectedDate );
                    }
                });

                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = String.valueOf( firstNameEditText.getText() );
                String lastName = String.valueOf( lastNameEditText.getText() );
                fireToast( "Usuario: " + firstName + " " + lastName);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstNameEditText.setText("");
                lastNameEditText.setText("");
                emailEditeText.setText("");
                passwordEditText.setText("");
                countrySpinner.setSelection(0);
                phoneEditText.setText("");
                genreRadioGroup.clearCheck();
                datePickerEditText.setText("");
            }
        });

        saveInSDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFormInSD();
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
            case R.id.datos_sd_item:
                Intent intent = new Intent(this, DatosSD.class);
                startActivity( intent );
                return true;

            default:
                return true;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_masculino:
                if (checked)
                    this.genreSelected = "Masculino";
                    break;
            case R.id.radio_femenino:
                if (checked)
                    this.genreSelected = "Femenino";
                    break;
        }
    }

    private void fireToast( String message ) {
        Toast.makeText(
                getApplicationContext(),
                message,
                Toast.LENGTH_SHORT
        ).show();
    }

    private boolean isExternalStorageAvailableForRW() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void saveFormInSD() {

        // Get values
        String firstName = String.valueOf( firstNameEditText.getText() );
        String lastName = String.valueOf( lastNameEditText.getText() );
        String email = String.valueOf( emailEditeText.getText() );
        String password = String.valueOf( passwordEditText.getText() );
        String country = String.valueOf( countrySpinner.getSelectedItem() );
        String phone = String.valueOf( phoneEditText.getText() );
        String genre = this.genreSelected;
        String date = String.valueOf( datePickerEditText.getText() );

        // Save in SD
        if (isExternalStorageAvailableForRW()) {
            String filename = "form.txt";
            String fileContents = "firstname=" + firstName + ", " +
                    "lastname=" + lastName + ", " +
                    "email=" + email + ", " +
                    "password=" + password + ", " +
                    "country=" + country + ", " +
                    "phone=" + phone + ", " +
                    "genre=" + genre + ", " +
                    "date=" + date;

            File file = new File(getExternalFilesDir(null), filename);
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                outputStream.write(fileContents.getBytes());
                outputStream.close();

                fireToast("Datos guardados en SD");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}