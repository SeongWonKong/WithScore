package com.withscore.seongwonkong.withscore.util;

import android.content.Context;

import com.withscore.seongwonkong.withscore.WithScoreConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by seongwonkong on 2017. 7. 3..
 */

public class FileUtils {
    public static File getRepoDirectory(Context context) {
        return context.getFilesDir();
    }

    public static File getRepoFile(Context context, String dir, String fileName) {
        if (context != null) {
            String suffix = "";
            if (WithScoreConstants.WITH_SCORE_DIR_NAME.equals(dir)) {
                suffix = ".jpeg";
            }
            return new File(getRepoDirectory(context), dir + "/" + fileName + suffix);
        }
        return null;
    }

    public static void saveFile(String outPath, ByteArrayOutputStream stream) {
        FileOutputStream outputStream = null;

        try {
            File outFile = new File(outPath);
            outFile.getParentFile().mkdirs();

            outputStream = new FileOutputStream(outFile);
            stream.writeTo(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
