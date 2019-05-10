package com.samerkanakri.instaclone.Models;

import android.graphics.Bitmap;

import com.parse.ParseFile;

/**
 * Created by Samer on 14-May-18.
 */

public class User {
    private String username;
    private ParseFile fileIcon;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ParseFile getFileIcon() {
        return fileIcon;
    }

    public void setFileIcon(ParseFile fileIcon) {
        this.fileIcon = fileIcon;
    }
}
