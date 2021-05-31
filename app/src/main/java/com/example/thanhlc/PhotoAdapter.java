package com.example.thanhlc;

import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.thanhlc.fragment.HomeFragment;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {

    private HomeFragment mContext;
    private List<photo> mListPhoto;


    public PhotoAdapter(HomeFragment mContext, List<photo> mListPhoto) {
        this.mContext = mContext;
        this.mListPhoto = mListPhoto;
    }

    @NonNull

    public int getCount() {
        if(mListPhoto != null){
            return mListPhoto.size();
        }
        return 0;
    }

    @NonNull
    
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container, false);
        ImageView imgPhoto = view.findViewById(R.id.img_photo);
        TextView textView = view.findViewById(R.id.link);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView .setText("https://www.facebook.com/All-RacingShop-106377424321048");
                Linkify.addLinks(textView , Linkify.WEB_URLS);
            }
        });
        photo photo = mListPhoto.get(position);

        if(photo != null){
            Glide.with(mContext).load(photo.getResourceId()).into(imgPhoto);
        }
        //Add view to viewgroup
        container.addView(view);

        return view;
    }


    public boolean isViewFromObject(@NonNull  View view, @NonNull  Object object) {
        return view == object;
    }


    public void destroyItem(@NonNull  ViewGroup container, int position,  Object object) {
        //Remove view
        container.removeView((View) object);
    }
}
