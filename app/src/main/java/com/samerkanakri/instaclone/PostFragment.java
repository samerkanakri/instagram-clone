package com.samerkanakri.instaclone;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.samerkanakri.instaclone.R;

/**
 * Created by Samer on 14-May-18.
 */

public class PostFragment extends Fragment {

    ImageButton imgBtnOptions;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_item_card,container,false);

    }
}
