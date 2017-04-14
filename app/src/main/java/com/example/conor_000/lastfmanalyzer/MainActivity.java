package com.example.conor_000.lastfmanalyzer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private InputMethodManager inMgr;
    private ListView tracklist;
    private ArrayList<SongInfo> tracks;
    private LayoutInflater inflater;
    private Spinner geoSpinner;
    private Button trackButton;

    //user can create an account in which they set their default Geo-area
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.inMgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        this.tracklist = (ListView)findViewById(R.id.track_list);
        this.geoSpinner = (Spinner)findViewById(R.id.geo);
        this.trackButton = (Button)findViewById(R.id.track_button);
        this.inflater = LayoutInflater.from(this);

        this.trackButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                  inMgr.hideSoftInputFromWindow(trackButton.getWindowToken(),0);
                  LastFMWebAPIFetchTask lfmWebAPITask = new LastFMWebAPIFetchTask(MainActivity.this);
                  try{
                        TextView txtView = (TextView)geoSpinner.getSelectedView(); //gets the text (i.e. the country) selected in the UI by the spinner widget
                        String metroTxt = txtView.getText().toString();
                        lfmWebAPITask.execute(metroTxt); //calls the AsyncTask with the name of the requested metro passed to it. The AsyncTask handles the calls to the LastFm API's and the subsequent parsing of the JSON then returns the results to the main thread.

                  }
                  catch(Exception e) {
                       lfmWebAPITask.cancel(true);
                       alert(getResources().getString(R.string.no_tracks) );
                }
            }
        } );
        //restore previously fetched data on orientation change
        /*final Object[] data = (Object[])getLastCustomNonConfigurationInstance();
        if(data!=null){
            this.tracks=(ArrayList<SongInfo>)data[0];
            tracklist.setAdapter(new TrackDataAdapter(this,this.inflater, this.tracks));
            //the tracklist layout (i.e. the layout showing relevant tracks) is updated.
        }*/

    }
    //uses Toast to display a small message that does not distract from the main activity
    public void alert(String msg){
         Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    //save fetched track data for orientation changes
   /* @Override
    public Object onRetainNonConfigurationInstance(){
         Object[] myStuff = new Object[2];
         myStuff[0]=this.tracks; //the tracks currently being displayed are stored in the array
         return myStuff;

    } */
    //bundle that holds references to row item views
    public static class MyViewHolder{
        public TextView trackName, artistName;
        public Button trackButton;
        public SongInfo song;
    }

    public void setTracks(ArrayList<SongInfo> tracks){
        this.tracks=tracks;
        this.tracklist.setAdapter(new TrackDataAdapter(this, this.inflater,this.tracks) );

    }




}
