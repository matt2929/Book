package com.example.matthew.book.Util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.matthew.book.Activities.FrontPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static android.R.attr.x;

/**
 * Created by Matthew on 3/20/2017.
 */

public class SaveAverages {
    File _myFile;
    private FileOutputStream outputStream;
    private FileInputStream inputStream;
    private Context _context;
    public String _fileName;
    File _file;
    int count = 0;
    private Calendar cal = Calendar.getInstance();
    private int month = cal.get(Calendar.MONTH) + 1;
    private int day = cal.get(Calendar.DAY_OF_MONTH);
    private int year = cal.get(Calendar.YEAR);
    private int hour = cal.get(Calendar.HOUR_OF_DAY);
    private int minute = cal.get(Calendar.MINUTE);
    private int second = cal.get(Calendar.SECOND);

    public SaveAverages(Context context) {
        FileOutputStream outputStream;
        FileInputStream inputStream;
        _context = context;
        _fileName = "Book_Totals_" + FrontPage.name + "_" + (month + 1) + "M" + day + "D" + year + "Y_" + hour + "h" + minute + "m" + second + "s.csv";
    }

    public void saveData(int chapter, HashMap<String, Integer> hm) {
        try {
            String string = "";
            string += load();

            String titleBar=" Chapter "+chapter+",";
            String valueBar=",";
            Integer sum=0;
            for ( Map.Entry<String, Integer> entry : hm.entrySet() ) {
                sum+=entry.getValue();
                titleBar+=readableId(entry.getKey())+",";
            }
            titleBar+=";";
            for ( Map.Entry<String, Integer> entry : hm.entrySet() ) {
                valueBar+=""+((float)entry.getValue()/(float)sum)+",";
            }
            valueBar+=";";
            string+=titleBar+valueBar;
            outputStream = _context.openFileOutput(_fileName, Context.MODE_WORLD_READABLE);
            outputStream.write(string.getBytes());
            outputStream.close();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), _fileName);
            PrintWriter csvWriter;
            if ( file.exists() ) {
                file.delete();
            }
            try {
                file.createNewFile();
                csvWriter = new PrintWriter(new FileWriter(file, true));
                int last = 0;
                int count = 0;
                csvWriter.print(FrontPage.name + " Reading Session [" + (month + 1) + "/" + day + "/" + year + "]");
                csvWriter.append('\n');
                for ( int i = 0; i < string.length(); i++ ) {
                    if ( string.charAt(i) == ';' ) {
                        csvWriter.print(string.substring(last + 1, i));
                        csvWriter.append('\n');
                        last = i;
                    }
                }
                csvWriter.print(string.substring(last, string.length() - 1));
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String load() {
        String line1 = "";
        try {
            inputStream = _context.openFileInput(_fileName);
            if ( inputStream != null ) {
                InputStreamReader inputreader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = "";
                try {
                    while ( (line = buffreader.readLine()) != null ) {
                        line1 += line;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

            String error = "";
            error = e.getMessage();
        }
        return line1;
    }

    public String readableId(String s){
        return s.substring(28);
    }
}