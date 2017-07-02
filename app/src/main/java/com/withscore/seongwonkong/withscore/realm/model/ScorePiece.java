package com.withscore.seongwonkong.withscore.realm.model;

import io.realm.RealmObject;

/**
 * Created by seongwonkong on 2017. 7. 3..
 */

public class ScorePiece extends RealmObject {
    private long scoreSeq;
    private String imagePath;
    private long createdDateTime;

    public long getScoreSeq() {
        return scoreSeq;
    }

    public void setScoreSeq(long scoreSeq) {
        this.scoreSeq = scoreSeq;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
