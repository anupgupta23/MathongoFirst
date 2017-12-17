package com.learnacad.learnacad.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 14-08-2017.
 */

public class Filter implements Serializable {

    ArrayList<String> classArray;
    ArrayList<String> subjectArray;
    ArrayList<String> categoryArray;
    ArrayList<String> mediumArray;
    ArrayList<String> difficultyArray;

    public Filter(){

        classArray = new ArrayList<>();
        subjectArray = new ArrayList<>();
        categoryArray = new ArrayList<>();
        mediumArray = new ArrayList<>();
        difficultyArray = new ArrayList<>();
    }

    public ArrayList<String> getClassArray() {
        return classArray;
    }

    public ArrayList<String> getSubjectArray() {
        return subjectArray;
    }

    public ArrayList<String> getCategoryArray() {
        return categoryArray;
    }

    public ArrayList<String> getMediumArray() {
        return mediumArray;
    }

    public ArrayList<String> getDifficultyArray() {
        return difficultyArray;
    }
}
