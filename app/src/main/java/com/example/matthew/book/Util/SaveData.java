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

/**
 * Created by Matthew on 1/9/2017.
 */

public class SaveData implements Serializable {

    private Context context;
    private File dir;
    private File file;
    ArrayList<PageInfo> pageInfos = new ArrayList<>();

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
            arrayWork.add(0, new ReadingSession(startTime,endTime,pageInfos));
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

    public void savePage(ArrayList<Touch> alltouch, long duration, int pageNum) {
        PageInfo tempPageInfo = new PageInfo(alltouch, duration, pageNum);
        pageInfos.add(tempPageInfo);
    }


    public ArrayList<ReadingSession> getReadingSessions(Context c) {

        ObjectInputStream ois =
                null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
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

    public static class ReadingSession implements Serializable {
        Calendar StartTime, EndTime;
        ArrayList<PageInfo> pageInfo = new ArrayList<>();

        public ReadingSession(Calendar startTime, Calendar endTime, ArrayList<PageInfo> pageInfos) {
            StartTime = startTime;
            EndTime = endTime;
            this.pageInfo=pageInfos;
        }

        public ArrayList<SaveData.PageInfo> getPageInfo() {
            return pageInfo;
        }

        public Calendar getEndTime() {
            return EndTime;
        }

        public Calendar getStartTime() {
            return StartTime;
        }

        public void setEndTime(Calendar endTime) {
            EndTime = endTime;
        }

        public void setPageInfo(ArrayList<SaveData.PageInfo> pageInfos) {
            pageInfo = pageInfos;
        }

        public void setStartTime(Calendar startTime) {
            StartTime = startTime;
        }

    }

    public static class PageInfo implements Serializable {
        public ArrayList<Touch> touches = new ArrayList<>();
        public Long duration;
        public int pageNum;

        public PageInfo(ArrayList<Touch> alltouch, long duration, int pageNum) {

        }

        public ArrayList<Touch> getTouches() {
            return touches;
        }

        public Long getDuration() {
            return duration;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public void setTouches(ArrayList<Touch> touches) {
            this.touches = touches;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }
    }


    public static class Touch implements Serializable {
        private float _X, _Y;
        private boolean _Good;
        private Calendar _Time;

        public Touch(Calendar time, float x, float y, boolean good) {
            _Time = time;
            _X = x;
            _Y = y;
            _Good = good;
            _Time = time;
        }

        public boolean is_Good() {
            return _Good;
        }

        public float get_X() {
            return _X;
        }

        public float get_Y() {
            return _Y;
        }

        public Calendar get_Time() {
            return _Time;
        }

        public void set_Good(boolean _Good) {
            this._Good = _Good;
        }

        public void set_Time(Calendar _Time) {
            this._Time = _Time;
        }

        public void set_X(float _X) {
            this._X = _X;
        }

        public void set_Y(float _Y) {
            this._Y = _Y;
        }

    }
}
