package com.example.jeff.meowmix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Album> albumList;

    public AlbumAdapter(Context c, ArrayList<Album> albumList) {
        mContext = c;
        this.albumList = albumList;
    }

    @Override
    public int getCount() {
        return albumList.size();
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
        View gridView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);

            gridView = inflater.inflate(R.layout.album_tab, null);
        }
        else {
            gridView = (View) convertView;
        }

        TextView textArtist = (TextView) gridView.findViewById(R.id.album_artist);
        TextView textTitle = (TextView) gridView.findViewById(R.id.album_title);
        TextView textCount = (TextView) gridView.findViewById(R.id.album_song_count);
        ImageView imageAlbum = (ImageView) gridView.findViewById(R.id.album_image);

        textArtist.setText(albumList.get(position).getArtist());
        textTitle.setText(albumList.get(position).getTitle());
        textCount.setText(albumList.get(position).getCount() + " songs");

        if(albumList.get(position).getArt() != null) {
            imageAlbum.setImageBitmap(albumList.get(position).getArt());
        }

        // if there's no album art, stick in a stock image as a placeholder
        else {
            imageAlbum.setImageResource(R.drawable.no_album);
        }

        return gridView;
    }
}
