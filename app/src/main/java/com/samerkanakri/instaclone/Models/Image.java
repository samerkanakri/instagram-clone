package com.samerkanakri.instaclone.Models;

import android.graphics.Bitmap;

import com.parse.ParseFile;

import java.util.Date;

/**
 * Created by Samer on 11-May-18.
 */

public class Image {
    private String owner;
    private Bitmap imageBitmap;
    private String title;
    private String description;
    private ParseFile parseFile;
    private Date date;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParseFile getParseFile() {
        return parseFile;
    }

    public void setParseFile(ParseFile parseFile) {
        this.parseFile = parseFile;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

}
