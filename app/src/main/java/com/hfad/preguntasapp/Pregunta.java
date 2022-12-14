package com.hfad.preguntasapp;

public class Pregunta {
    private String question;
    private boolean answer;

    public Pregunta(String question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean getAnswer() {
        return answer;
    }
}
