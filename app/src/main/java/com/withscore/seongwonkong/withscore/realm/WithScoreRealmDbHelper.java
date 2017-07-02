package com.withscore.seongwonkong.withscore.realm;

import com.withscore.seongwonkong.withscore.realm.model.Score;
import com.withscore.seongwonkong.withscore.realm.model.ScorePiece;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by seongwonkong on 2017. 7. 3..
 */

public class WithScoreRealmDbHelper {

    public static void saveScore(final long timeStamp, final String thumbnailImagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Score score = realm.createObject(Score.class);
                score.setTitle("TEMP_TITLE");
                score.setSeq(timeStamp);
                score.setThumbnailImagePath(thumbnailImagePath);
                score.setCreatedDateTime(System.currentTimeMillis());
            }
        });
        realm.close();
    }

    public static void saveScorePiece(final long scoreSeq, final String imagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ScorePiece scorePiece = realm.createObject(ScorePiece.class);
                scorePiece.setImagePath(imagePath);
                scorePiece.setScoreSeq(scoreSeq);
                scorePiece.setCreatedDateTime(System.currentTimeMillis());
            }
        });
        realm.close();
    }

    public static RealmResults<Score> getScores() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Score.class)
                .findAllSorted("createdDateTime");
    }

    public static void updateScore(final long seq, final String thumbnailImagePath) {
        Realm realm = Realm.getDefaultInstance();
        Score score = realm.where(Score.class)
                .equalTo("seq", seq)
                .findFirst();

        realm.beginTransaction();
        score.setThumbnailImagePath(thumbnailImagePath);
        realm.insertOrUpdate(score);
        realm.commitTransaction();
        realm.close();
    }
}
