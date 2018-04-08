package com.example.jeff.meowmix;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class albumAdapter extends BaseAdapter {
    private Context mContext;
    private Bitmap[] artList;
    private LayoutInflater albumInf;


    public albumAdapter(Context c, ArrayList<Bitmap> albumList) {
        mContext = c;

        // grab all the images and put them into an array
        if(!albumList.isEmpty())
            artList = albumList.toArray(new Bitmap[albumList.size()]);
    }

    @Override
    public int getCount() {
        return artList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if(convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        }
        else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(artList[position]);
        return imageView;
    }
}
