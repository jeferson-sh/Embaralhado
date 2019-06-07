package com.mydroidtechnology.embaralhado.model;

import android.graphics.drawable.Drawable;

public class Score extends GenericModel {

    private Double score;
    private Integer contextID;
    private Integer answerCount;
    private Integer answerTotal;

    public Score(Drawable drawable, Double score, String userName, Integer contextID, Integer answerCount, Integer answerTotal) {
        super(drawable,userName);
        this.contextID = contextID;
        this.score = score;
        this.answerCount = answerCount;
        this.answerTotal = answerTotal;

    }

    public Score(Integer id, byte[] imageBytes, Double score, String userName, Integer contextID, Integer answerCount, Integer answerTotal) {
        super(id,imageBytes,userName);
        this.contextID = contextID;
        this.score = score;
        this.answerCount = answerCount;
        this.answerTotal = answerTotal;
    }

    public Double getScore() {
        return score;
    }

    public Integer getContextID() {
        return contextID;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public Integer getAnswerTotal() {
        return answerTotal;
    }
}
