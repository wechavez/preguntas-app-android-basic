package com.hfad.preguntasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private String genreSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button goToLoginButton = findViewById(R.id.go_to_login_btn);
        Button registerButton = findViewById(R.id.register_btn);
        EditText datePickerEditText = findViewById(R.id.date_picker);

        EditText firstNameEditText = findViewById(R.id.firstname);
        EditText lastNameEditText = findViewById(R.id.lastname);
        Spinner countrySpinner = findViewById(R.id.country);
        EditText emailEditeText = findViewById(R.id.input_email);
        EditText passwordEditText = findViewById(R.id.input_password);
        EditText phoneEditText = findViewById(R.id.phone_number);
        RadioGroup genreRadioGroup = findViewById(R.id.genre);

        Button resetButton = findViewById(R.id.reset_btn);

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
}