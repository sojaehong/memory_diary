package com.jaehong.kcci.memorydiary;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by jeahong on 2018-05-07.
 */

public class DBImportAndExport {
    Context context;

    public DBImportAndExport(Context context) {
        this.context = context;
    }

    public void importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                File backupDB = new File(data, "//data//com.jaehong.kcci.memorydiary//databases//memoryDiaryDB");
                File currentDB = new File(sd, "memoryDiary/memoryDiaryDB");

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();

                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Toast.makeText(context,
                        "추억 복원 완료", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.e("import", e.getMessage());
            Toast.makeText(context, "추억 복원 실패", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    public String exportDB() {
        File backupDB = null;
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();


            if (sd.canWrite()) {
                File BackupDir = new File(sd, "memoryDiary");
                BackupDir.mkdir();

                File currentDB =
                        new File(data, "//data//com.jaehong.kcci.memorydiary//databases//memoryDiaryDB");
                backupDB = new File(sd, "memoryDiary/memoryDiaryDB");

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();

                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Toast.makeText(context,
                        "추억 백업 완료", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.e("backup", e.getMessage());
            Toast.makeText(context, "추억 백업 실패", Toast.LENGTH_SHORT).show();

        }

        if (backupDB == null) {
            Toast.makeText(context, "설정 -> 애플리케이션 -> 권한을 주세요!", Toast.LENGTH_SHORT).show();
            return null;
        }

        return backupDB.toString();
    }

}
