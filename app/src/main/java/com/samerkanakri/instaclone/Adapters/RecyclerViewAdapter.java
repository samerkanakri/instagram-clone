package com.samerkanakri.instaclone.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.samerkanakri.instaclone.Models.Image;
import com.samerkanakri.instaclone.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Samer on 11-May-18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<Image> lstDataset = new ArrayList<>();
    Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView txtTitle, txtOwner, txtDate;
        private ImageButton imgBtnIcon,imgBtnOptions;
        private ImageView imgPost;
        private ToggleButton tglBtnUpVote;

        public ViewHolder(View view) {
            super(view);
//            this.setIsRecyclable(false); // Block the View Holder from recycling it’s views (DON’T DO THIS)
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtOwner = (TextView) view.findViewById(R.id.txtOwner);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            imgPost = (ImageView) view.findViewById(R.id.imgPost);
            imgBtnIcon = (ImageButton) view.findViewById(R.id.imgBtnIcon);
            imgBtnOptions = (ImageButton) view.findViewById(R.id.imgBtnOptions);
            tglBtnUpVote = (ToggleButton) view.findViewById(R.id.tglBtnUpVote);
        }

    }


    public RecyclerViewAdapter(Context context, ArrayList<Image> lstImages) {
        lstDataset = lstImages;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder vh;
        View layoutParent = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item_card, parent, false);
        vh = new ViewHolder(layoutParent);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Image img = lstDataset.get(position);
        holder.txtTitle.setText(img.getTitle());
        holder.txtOwner.setText(img.getOwner());
        Date date = img.getDate();
        DateFormat df = new SimpleDateFormat("dd MMM");
        String strDate = df.format(date);
        holder.txtDate.setText(strDate);
        holder.imgBtnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "profile*", Toast.LENGTH_SHORT).show();
            }
        });
        holder.imgBtnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context wrapper = new ContextThemeWrapper(context,R.style.PopupMenu); // for the popupMenu theme
                PopupMenu popup = new PopupMenu(wrapper, view);

                popup.getMenuInflater().inflate(R.menu.post_options, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.report){
                            Toast.makeText(context, "reported*", Toast.LENGTH_SHORT).show();
                        }else if(menuItem.getItemId() == R.id.follow){
                            Toast.makeText(context, "followed*", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
            }
        });
        holder.tglBtnUpVote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(context, "up voted*", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "down voted*", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ParseFile file = img.getParseFile();
        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                lstDataset.get(position).setImageBitmap(bitmap);
                holder.imgPost.setImageBitmap(lstDataset.get(position).getImageBitmap());
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lstDataset.size();
    }




}