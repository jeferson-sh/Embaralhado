package com.mydroidtechnology.embaralhado.model;

import android.graphics.drawable.Drawable;

public class Score extends GenericModel {

    private Double score; //Score value.
    private Integer contextID; //Id of the Context that the points were saved.
    private Integer correctAnswerCount; //Count of corrects answers.
    private Integer answerTotal;    //Total of answers.

    //This constructor is called when inserting new Scores in database.
    public Score(Drawable drawable, Double score, String userName, Integer contextID, Integer correctAnswerCount, Integer answerTotal) {
        super(drawable,userName);
        this.contextID = contextID;
        this.score = score;
        this.correctAnswerCount = correctAnswerCount;
        this.answerTotal = answerTotal;

    }

    //This constructor is called when reading Scores in database.
    public Score(Integer id, byte[] imageBytes, Double score, String userName, Integer contextID, Integer correctAnswerCount, Integer answerTotal) {
        super(id,imageBytes,userName);
        this.contextID = contextID;
        this.score = score;
        this.correctAnswerCount = correctAnswerCount;
        this.answerTotal = answerTotal;
    }

    public Double getScore() {
        return score;
    }

    public Integer getContextID() {
        return contextID;
    }

    public Integer getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public Integer getAnswerTotal() {
        return answerTotal;
    }
}
