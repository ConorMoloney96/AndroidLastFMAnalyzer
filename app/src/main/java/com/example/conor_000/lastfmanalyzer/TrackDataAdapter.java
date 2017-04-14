package com.example.conor_000.lastfmanalyzer;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by conor_000 on 26/03/2017.
 */

//Adapter that creates a view to display the tracks returned by LastFM to the user
public class TrackDataAdapter extends BaseAdapter implements OnClickListener {

    private static final String debugTag="TrackDataAdapter";
    private MainActivity activity;
    private LayoutInflater layoutInflater;
    private ArrayList<SongInfo> tracks;

    public TrackDataAdapter(MainActivity activity, LayoutInflater li, ArrayList<SongInfo> tracks){
        this.activity = activity;
        this.layoutInflater=li;
        this.tracks=tracks;

    }
    //getCount method returns the number of items (i.e. tracks) in the adapter
    @Override
    public int getCount(){
        return this.tracks.size();
    }
    //not allowed to directly access an item
    @Override
    public boolean areAllItemsEnabled(){
        return true;
    }
    @Override
    public Object getItem(int arg0){
        return null;
    }
    //method to get the ID number of an object from the tracks array.
    //the id of track items corresponds to their index position
    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int pos,View convertView,ViewGroup parent){
        MainActivity.MyViewHolder viewHolder; //creates an object of type MyViewHolder. An inner class in MainActivity
        if(convertView==null) //if convertView is set to null we have to create a new View i.e. initially there is no View to convert so we have to create a new view
        {
            convertView=layoutInflater.inflate(R.layout.trackdatarow, parent,false); //when the third parameter (attach to root) is set to false this means that the View will be attached to the parent ViewGroup later on
            viewHolder=new MainActivity.MyViewHolder();
            viewHolder.trackName = (TextView) convertView.findViewById(R.id.track_name);
            viewHolder.artistName = (TextView) convertView.findViewById(R.id.artist_name);
            viewHolder.trackButton = (Button) convertView.findViewById(R.id.track_button);
            viewHolder.trackButton.setTag(viewHolder);
            convertView.setTag(viewHolder);


        }
        else //if convertView is not null we simply recycle the previous view by subbing in new data
        {
            viewHolder = (MainActivity.MyViewHolder)convertView.getTag(); //gets the tags representing data from the View to be converted
        }

           convertView.setOnClickListener(this); //this adapter is called whn the View is clicked. OnClick method declared below

           SongInfo track = tracks.get(pos);
           viewHolder.song=track;
           viewHolder.trackName.setText(track.getName() );
           viewHolder.artistName.setText(track.getArtist());
           viewHolder.trackButton.setOnClickListener(this);

           return convertView;


    }

    @Override
    public void onClick(View v){
        MainActivity.MyViewHolder holder = (MainActivity.MyViewHolder) v.getTag();
        if (v instanceof Button) {

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(holder.song.getArtistUrl()));
            this.activity.startActivity(intent);

        } else if (v instanceof View) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(holder.song.getTrackUrl()));
            this.activity.startActivity(intent);
        }
        Log.d(debugTag,"OnClick pressed.");

    }
}










