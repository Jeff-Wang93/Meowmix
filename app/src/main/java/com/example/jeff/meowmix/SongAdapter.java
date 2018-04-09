package com.example.jeff.meowmix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {
    private Context mContext;
    private Song[] songList;

    public SongAdapter(Context c, ArrayList<Song> songArrayList) {
        mContext = c;
        songList = songArrayList.toArray(new Song[songArrayList.size()]);
    }

    @Override
    public int getCount() {
        return songList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);

            listView = inflater.inflate(R.layout.song_tab, null);
        }
        else {
            listView = (View) convertView;
        }

        TextView songTitle = (TextView) listView.findViewById(R.id.song_title);
        TextView songAlbum = (TextView) listView.findViewById(R.id.song_album);
        TextView songArtist = (TextView) listView.findViewById(R.id.song_artist);

        songTitle.setText(songList[position].getTitle());
        songArtist.setText(songList[position].getArtist());
        songAlbum.setText(songList[position].getAlbum());

        return listView;
    }
}
