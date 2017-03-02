package com.example.matthew.book.EyeTracking;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/5/2017.
 */

public class MovingAverage {
    ArrayList<Double> data = new ArrayList<>();
    int max = 0;

    public MovingAverage(int length) {
        max = length;
    }

    public double update(double f) {
        if (data.size() < max) {
            data.add(f);
        } else if (data.size() >= max) {
            data.remove(0);
            data.add(f);
        } else {
        }
        float sum = 0;
        for (Double flo : data) {
            sum += flo;
        }
        return (sum / (double) max);
    }

    public double getCurrent() {
        float sum = 0;
        for (Double flo : data) {
            sum += flo;
        }
        return -(sum / (double) max);
    }
    public double getCurrentNeg() {
        float sum = 0;
        for (Double flo : data) {
            sum += flo;
        }
        return (sum / (double) max);
    }
}
