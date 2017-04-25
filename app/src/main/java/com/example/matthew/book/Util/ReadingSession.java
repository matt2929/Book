package com.example.matthew.book.Util;

import com.example.matthew.book.Activities.FrontPage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Matthew on 1/12/2017.
 */

public class ReadingSession implements Serializable {
    Calendar StartTime, EndTime;
    ArrayList<PageInfo> pageInfo = new ArrayList<>();
    String Name = "";

    public ReadingSession(Calendar startTime, Calendar endTime, ArrayList<PageInfo> pageInfos) {
        StartTime = startTime;
        EndTime = endTime;
        this.pageInfo = pageInfos;
        Name = FrontPage.name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<PageInfo> getPageInfo() {
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

    public void setPageInfo(ArrayList<PageInfo> pageInfos) {
        pageInfo = pageInfos;
    }

    public void setStartTime(Calendar startTime) {
        StartTime = startTime;
    }


    public static class PageInfo implements Serializable {
        private ArrayList<Touch> touches = new ArrayList<>();
        private ArrayList<Touch> eyePre = new ArrayList<>();
        private ArrayList<Touch> eyePost = new ArrayList<>();

        private Long duration;
        private int pageNum;
        private int numEarly = 0;

        public PageInfo(ArrayList<Touch> allTouch, ArrayList<Touch> allEye, ArrayList<Touch> allEyePost, int numEarly, long duration, int pageNum) {
            eyePre = new ArrayList<>(allEye);
            touches = new ArrayList<Touch>(allTouch);
            eyePost = new ArrayList<>(allEyePost);
            this.duration = duration;
            this.pageNum = pageNum;
            this.numEarly = numEarly;
        }

        public ArrayList<Touch> getTouches() {
            return touches;
        }

        public ArrayList<Touch> getEyePre() {
            return eyePre;
        }

        public void setEyePost(ArrayList<Touch> eyePost) {
            this.eyePost = eyePost;
        }

        public ArrayList<Touch> getEyePost() {
            return eyePost;
        }

        public void setEyePre(ArrayList<Touch> eyePre) {
            this.eyePre = eyePre;
        }

        public Long getDuration() {
            return duration;
        }

        public int getPageNum() {
            return pageNum;
        }

        public int getNumEarly() {
            return numEarly;
        }

        public void setNumEarly(int numEarly) {
            this.numEarly = numEarly;
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

