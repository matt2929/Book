package com.example.matthew.book.Activities;

import java.util.ArrayList;

/**
 * Created by Matthew on 1/2/2017.
 */

public class PrematureClick {
    ArrayList<String> readOuts = new ArrayList<>();

    public PrematureClick() {
        readOuts.add("listen to the tree read and then tap the apple to find the seeds inside");
        readOuts.add( "listen to the tree read and then drag the apple seeds into the soil");
        readOuts.add("listen to the tree read and then use your finger to drag the soil over the seed so it can grow");
        readOuts.add("listen to the tree read and then tap the rainclouds to make it rain");
        readOuts.add("listen to the tree read and then drag the nutrients to the tree’s roots");
        readOuts.add("listen to the tree read and then tap the rain, sun, and nutrients to help the blossoms grow");
        readOuts.add("listen to the tree read and then tap the bees to see them pollinate the apple blossoms");
        readOuts.add("listen to the tree read and then tap the apple to see its seeds inside");
        readOuts.add("listen to the tree read and then tap each stage of his life cycle");
    }

    public ArrayList<String> getReadOuts() {
        return readOuts;
    }
}
