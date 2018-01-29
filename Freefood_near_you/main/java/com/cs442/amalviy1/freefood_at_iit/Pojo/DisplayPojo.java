package com.cs442.amalviy1.freefood_at_iit.Pojo;

import java.io.File;

/**
 * Created by Ankit on 11/6/16.
 */
public class DisplayPojo {
    int id;
    String item_name;
    String food_description;
    File image;
    String location;
    int likes;
    String date;


    public DisplayPojo(int id,String item_name,String food_description,File image,String location,int likes, String date)
    {
        this.id=id;
        this.item_name=item_name;
        this.food_description= food_description;
        this.image= image;
        this.location=location;
        this.likes= likes;

        this.date=date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getFood_description() {
        return food_description;
    }

    public void setFood_description(String food_description) {
        this.food_description = food_description;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
