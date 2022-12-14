package com.hfad.preguntasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    private static final int WIN_POINTS = 100;
    private static final int LOST_POINTS = 100;


    private Integer score = 0;
    private Pregunta currentQuestion;
    private boolean limitReached = false;

    private Pregunta[] questionsArr = new Pregunta[] {
        new Pregunta("¿Es Quito la capital de Ecuador?", true),
        new Pregunta("¿Nos vamos al mundial?", true),
        new Pregunta("¿Es Guayaquil la ciudad más segura del país?", false),
        new Pregunta("¿Java es los mismo que JavaScript?", false),
    };

    private List<Pregunta> questionsList = Arrays.asList( questionsArr );

    ListIterator<Pregunta> questionIterator = questionsList.listIterator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set username
        Bundle bundle = getIntent().getExtras();

        TextView usernameTextView = findViewById(R.id.username);
        usernameTextView.setText( "User or email: " + bundle.getString( "email" ) );

        Button trueButton = (Button) findViewById(R.id.true_button);
        Button falseButton = (Button) findViewById(R.id.false_button);
        TextView questionTextView = (TextView) findViewById(R.id.question_view);
        TextView scoreTextView = (TextView) findViewById(R.id.score_view);

        // set initial question
        currentQuestion = questionsList.get(0);
        questionTextView.setText( currentQuestion.getQuestion() );

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (limitReached) return;
                if ( questionIterator.next().getAnswer() ) {
                    score = score + WIN_POINTS;
                } else {
                    score = score - LOST_POINTS;
                }

                scoreTextView.setText("Puntaje: " + score);

                if ( questionIterator.hasNext() ) {
                    questionTextView.setText( questionsList.get( questionIterator.nextIndex() ).getQuestion() );
                } else {
                    limitReached = true;
                    scoreTextView.setText("Puntaje Final: " + score);
                    questionTextView.setText("Ya no hay más preguntas!!");
                    trueButton.setVisibility(View.INVISIBLE);
                    falseButton.setVisibility(view.INVISIBLE);
                }
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (limitReached) return;
                if ( !questionIterator.next().getAnswer() ) {
                    score = score + WIN_POINTS;
                } else {
                    score = score - LOST_POINTS;
                }

                scoreTextView.setText("Puntaje: " + score);

                if ( questionIterator.hasNext() ) {
                    questionTextView.setText( questionsList.get( questionIterator.nextIndex() ).getQuestion() );
                } else {
                    limitReached = true;
                    scoreTextView.setText("Puntaje Final: " + score);
                    questionTextView.setText("Ya no hay más preguntas!!");
                    trueButton.setVisibility(View.INVISIBLE);
                    falseButton.setVisibility(view.INVISIBLE);
                }
            }
        });
    }
}