package com.example.matthew.book.Activities.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Matthew on 1/9/2017.
 */

public class HistoryReading implements Serializable {
    ArrayList<ReadingSession> _history;
    public HistoryReading(ArrayList<ReadingSession> history) {
        _history = history;
    }

    public void addWorkout(ReadingSession  ws) {
        _history.add(ws);
    }

    public ArrayList<ReadingSession> get_history() {
        return _history;
    }

    public boolean leftHand = false;
    public int grade = 0;

    public static class ReadingSession implements Serializable {
        public Calendar CalendarData = Calendar.getInstance();
        public float JerkScore =0;
        public String WorkoutInfo = "";
        public String WorkoutName = "";
        public boolean LeftHand = false;
        public int Grade = 0;
        public ArrayList<Float> JerkData = new ArrayList<Float>();
        public ReadingSession (String workoutname,float jerkscore, String workoutinfo, int grade, boolean leftHand, ArrayList<Float> jerkData) {
            WorkoutInfo = workoutinfo;
            JerkScore =jerkscore;
            WorkoutName = workoutname;
            LeftHand=leftHand;
            Grade=grade;
            JerkData = jerkData;
        }

        public ArrayList<Float> getJerkData() {
            return JerkData;
        }

        public Calendar get_Cal() {
            return CalendarData;
        }

        public float getJerkScore() {
            return JerkScore;
        }

        public String getWorkoutInfo() {
            return WorkoutInfo;
        }

        public String getWorkoutName() {
            return WorkoutName;
        }

        public int getGrade() {
            return Grade;
        }

        public boolean isLeftHand() {
            return LeftHand;
        }
    }

}
