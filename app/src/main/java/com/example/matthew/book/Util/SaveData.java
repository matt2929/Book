package com.example.matthew.book.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.matthew.book.Util.ReadingSession.*;

/**
 * Created by Matthew on 1/9/2017.
 */

public class SaveData implements Serializable {

    private Context context;
    private File dir;
    private File file;
    ArrayList<ReadingSession.PageInfo> pageInfos = new ArrayList<>();

    public SaveData(Context c) {
        dir = new File(c.getFilesDir() + "/serialisedBookSessions");

        dir.mkdirs();
        file = new File(dir, "BookData.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        context = c;
    }

    public void saveSession(Context context, Calendar startTime, Calendar endTime) {
        //UserList ul = new UserList(a);
        Log.e("length", "" + getReadingSessions(context).size());
        ArrayList<ReadingSession> arrayWork = getReadingSessions(context);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream oout = new ObjectOutputStream(out);
            // write something in the file
            //String workoutname, int[] shakelist, String workoutinfo, int grade, boolean leftHand
            ReadingSession readingSession = new ReadingSession(startTime,endTime,pageInfos);
            arrayWork.add(0, readingSession);
            oout.writeObject(arrayWork);
            oout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void savePage(ArrayList<ReadingSession.Touch> alltouch, int early, long duration, int pageNum) {
        PageInfo tempPageInfo = new PageInfo(alltouch,early, duration, pageNum);
        pageInfos.add(tempPageInfo);
    }


    public ArrayList<ReadingSession> getReadingSessions(Context c) {

        ObjectInputStream ois =
                null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();Log.e("HEY!","This is broken input stream");
        }


        // UserList ul = null;
        ArrayList<ReadingSession> ul = null;

        try {
            if (ois == null) {
                ul = new ArrayList<ReadingSession>();

            } else {
                ul = (ArrayList<ReadingSession>) ois.readObject();
            }
        } catch (ClassNotFoundException e) {
            ul = new ArrayList<ReadingSession>();
            e.printStackTrace();
        } catch (IOException e) {
            ul = new ArrayList<ReadingSession>();
            e.printStackTrace();
        }
        if (ul != null) {
            return ul;
        } else {
            Toast.makeText(c, "null", Toast.LENGTH_LONG).show();
            return new ArrayList<ReadingSession>();
        }
    }
}
