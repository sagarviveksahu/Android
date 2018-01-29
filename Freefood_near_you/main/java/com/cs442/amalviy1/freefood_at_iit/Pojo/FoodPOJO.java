package com.cs442.amalviy1.freefood_at_iit.Pojo;

import java.io.File;
import java.text.DateFormat;

/**
 * Created by Ankit on 11/5/16.
 */
public class FoodPOJO {
    File file;
    String foodTitle, foodDescription, foodLocation, date;
    int likes;

    public FoodPOJO(File file, String foodTitle, String foodDescription, String foodLocation, String date) {
        this.file=file;
        this.foodTitle=foodTitle;
        this.foodLocation=foodLocation;
        this.foodDescription=foodDescription;
        this.likes=likes;
        this.date=date;

    }



    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFoodTitle() {
        return foodTitle;
    }

    public void setFoodTitle(String foodTitle) {
        this.foodTitle = foodTitle;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getFoodLocation() {
        return foodLocation;
    }

    public void setFoodLocation(String foodLocation) {
        this.foodLocation = foodLocation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
