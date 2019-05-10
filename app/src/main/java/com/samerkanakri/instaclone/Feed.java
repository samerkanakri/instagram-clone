package com.samerkanakri.instaclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.samerkanakri.instaclone.Adapters.MyDividerItemDecoration;
import com.samerkanakri.instaclone.Adapters.RecyclerTouchListener;
import com.samerkanakri.instaclone.Adapters.RecyclerViewAdapter;
import com.samerkanakri.instaclone.Models.Image;
import com.samerkanakri.instaclone.Models.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton imgBtnOptions;
    private Image img;
    private User user;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMenuView amvMenu;
    private Bitmap bitmap;
    private Uri uriSelectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setTitle("Feed");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) findViewById(R.id.feedRecyclerView);
        imgBtnOptions = (ImageButton) findViewById(R.id.imgBtnOptions);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    retrieveContent();
                }
            }
        );

        customizeRecyclerView();

        retrieveContent();
    }

    private void customizeRecyclerView(){

        // use a linear layout manager
        // default layout
        mLayoutManager = new LinearLayoutManager(this);
        // horizontal layout
//        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        // Divider
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // Custom divider
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 10));

        // handle clicks
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                Toast.makeText(getApplicationContext(), "long click", Toast.LENGTH_SHORT).show();
//            }
//        }));

        // ???
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.feed_menu, amvMenu.getMenu());
        menuInflater.inflate(R.menu.feed_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.camera){
//            Intent i = new Intent(Feed.this, NewPost.class);
//            startActivity(i);
            openCamera();
        }
        if(item.getItemId() == R.id.gallery){
//            Intent i = new Intent(Feed.this, NewPost.class);
//            startActivity(i);
            openGallery();
        }
        if(item.getItemId() == R.id.logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openCamera(){
        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),"file"+String.valueOf(System.currentTimeMillis())+".jpg");
        uriSelectedImage = uriSelectedImage.fromFile(file);
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT,uriSelectedImage);
        camIntent.putExtra("return-data",true);
        startActivityForResult(camIntent,0);
    }

    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 2);
    }

    private void cropImage(){
        try{
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(uriSelectedImage,"image/*");

            cropIntent.putExtra("crop","true");
            cropIntent.putExtra("outputX",1024);
            cropIntent.putExtra("outputY",1024);
            cropIntent.putExtra("aspectX",1);
            cropIntent.putExtra("aspectY",1);
            cropIntent.putExtra("scaleUpIfNeeded",true);
            cropIntent.putExtra("return-data",true);

            startActivityForResult(cropIntent,1);
        }catch (Exception e){
            Log.i("error cropping", e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){ // from camera
            cropImage();
        }else if(requestCode == 1 && resultCode == RESULT_OK) { // cropped
            if(data != null){
                Bundle bundle = data.getExtras();
                bitmap = bundle.getParcelable("data");
                Intent i = new Intent(this,NewPost.class);
                i.putExtra("bitmap",bitmap);
                startActivity(i);
            }
        }else if(requestCode == 2 && resultCode == RESULT_OK) { // from gallery
            if(data != null){
                uriSelectedImage = data.getData();
                cropImage();
            }
        }
//            Uri selectedImage = data.getData();
//            cropImage();
////            try {
////                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
////                imgSelectedImage.setImageBitmap(bitmap);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
    }

    private void retrieveContent(){

        // Get all data by class from parse server
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
        //query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        ArrayList<Image> lstImages = new ArrayList<>();

                        for(final ParseObject object: objects){
                            img = new Image();
                            img.setTitle(object.getString("title"));
                            img.setOwner(object.getString("username"));

//                            ParseRelation<ParseUser> owner = object.getRelation("owner");
//                            ParseQuery<ParseUser> ownerQuery = owner.getQuery();
//                            ownerQuery.findInBackground(new FindCallback<ParseUser>() {
//                                @Override
//                                public void done(List<ParseUser> objects, ParseException e) {
//                                    if(e == null){
//                                        for(ParseUser user: objects){
//                                            img.setOwner(user.getUsername());
//                                        }
//
//                                    }else{
//                                        Log.e("error",e + "");
//                                    }
//                                }
//                            });
//                            ParseRelation<ParseObject> owner = object.getRelation("owner");
//                            ParseQuery<ParseObject> ownerQuery = owner.getQuery();
//                            ownerQuery.whereEqualTo("title",img.getTitle());
//                            Log.e("tiiiiiiiiiiiiitle",img.getTitle());
//
//                            ownerQuery.findInBackground(new FindCallback<ParseObject>() {
//                                @Override
//                                public void done(List<ParseObject> objects, ParseException e) {
//                                    if(e == null){
//                                        Log.i("owners list", objects.size() + " owner(s)");
//                                        if(objects.size() > 0){
//                                            Log.i("owners list", objects.size() + " owner(s)");
//                                            for(ParseObject object : objects){
//                                                Log.i("user", object.getString("username"));
//                                            }
//                                        }
//                                    }
//                                }
//                            });

                            img.setDate(object.getCreatedAt());
                            ParseFile fileImg = object.getParseFile("image");
                            img.setParseFile(fileImg);

                            lstImages.add(img);
                        }
                        recyclerViewAdapter = new RecyclerViewAdapter(getApplication(),lstImages);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }else{
                    Log.i("error getting list",e.getMessage());
                }
            }
        });
    }

    private void logout(){
       // log out
      ParseUser.logOutInBackground(new LogOutCallback() {
          @Override
          public void done(ParseException e) {
              if(e == null){
                  Log.i("log out","logged out");
                  Intent i = new Intent(Feed.this, Login.class);
                  startActivity(i);
                  finish();
              }else{
                  Log.i("log out","log out failed, " + ParseUser.getCurrentUser().getUsername() + " is still logged in");
              }
          }
      });
    }

}
