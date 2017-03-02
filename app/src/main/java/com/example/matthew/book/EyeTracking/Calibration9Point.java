package com.example.matthew.book.EyeTracking;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/16/2017.
 */

public class Calibration9Point {
    private ArrayList<ArrayList<Double>> dataX = new ArrayList<>();
    private ArrayList<ArrayList<Double>> dataY = new ArrayList<>();
    private boolean calibrationValuesSet = false;
    private double[] averagesX = new double[9];
    private double[] averagesY = new double[9];
    private boolean centerCorrection=false;
    double leftX=0,midX=0,rightX=0,topY=0,midY=0,bottomY=0;
    double midOffsetX,midOffsetY;



    public Calibration9Point() {
        for (int i = 0; i < 9; i++) {
            dataX.add(new ArrayList<Double>());
            dataY.add(new ArrayList<Double>());
        }
    }

    public void recordCalibration(double x, double y, int state) {
        if (state != -1) {
            dataX.get(state).add(x);
            dataY.get(state).add(y);
        }
    }

    public double[] getXYPoportional(double xGaze, double yGaze, double width, double height) {
        if (!calibrationValuesSet) {
            for (int s = 0; s < dataX.size(); s++) {
                for (int v = 0; v < dataX.get(s).size(); v++) {
                    averagesX[s] += dataX.get(s).get(v);
                    averagesY[s] += dataY.get(s).get(v);
                }
                averagesX[s] /= dataX.get(s).size();
                averagesY[s] /= dataY.get(s).size();

                Log.e("average", ":" + s + "x:" + averagesX[s] + "\n" + "y:" + averagesY[s]);
            }
            double buffer=.005;
            topY=((averagesY[0]+averagesY[1]+averagesY[2])/3.0)+buffer;
            midY=(averagesY[3]+averagesY[4]+averagesY[5])/3.0;
            bottomY=((averagesY[6]+averagesY[7]+averagesY[8])/3.0)-buffer;
            leftX=((averagesX[0]+averagesX[3]+averagesX[6])/3.0)+buffer;
            midX=(averagesX[1]+averagesX[4]+averagesX[7])/3.0;
            rightX=((averagesX[2]+averagesX[5]+averagesX[8])/3.0)-buffer;
            double recordedMidX=(rightX-leftX)/2.0;
            double recordedMidY=(bottomY-topY)/2.0;
           midOffsetX=(recordedMidX-midX);
            midOffsetY=(recordedMidY-midY);
            calibrationValuesSet = true;
        }
        double x=0;
        double y=0;
        x=((xGaze-leftX)*width)/(rightX-leftX);
        y=((yGaze-topY)*height)/(bottomY-topY);
        return new double[]{x, y};

    }

    public String getPoints(){
        String s="";
        for(int i=0;i<averagesX.length;i++){
            s+=i+"[x:"+averagesX[i]+"y:"+averagesY[i]+"]\n";
        }
        return s;
    }
    public void setCenterCorection(boolean b){
        centerCorrection=b;
    }
}
