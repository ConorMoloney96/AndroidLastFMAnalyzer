package com.example.conor_000.lastfmanalyzer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.conor_000.lastfmanalyzer.R.string.look_for_tracks;

/**
 * Created by conor_000 on 15/03/2017.
 */

public class LastFMWebAPIFetchTask extends AsyncTask<String, Integer, String> {

    private static String debugTag = "LastFMWebAPIFetchTask";
    private ProgressDialog progDialog;
    private MainActivity activity;
    private Context context;


    //constrcuts the AsyncTask
    LastFMWebAPIFetchTask(MainActivity activity){
          super();
          this.activity=activity;
          this.context=this.activity.getApplicationContext();
    }

    //Actions performed before the execution of the AsyncThread begins. In this case a bar showing the progress of the search is shown
    //on the user thread.
    void preExecute(){
        super.onPreExecute();
        progDialog= ProgressDialog.show(this.activity, "Searching please wait", this.context.getResources().getString(look_for_tracks), true, false);

    }
    //what is to be actually performed in the background thread. In this case searching LastFM for relevant information
    //is done in a background thread using AsyncTask in order to avoid slowing down the main thread
    protected String doInBackground(String... params) {
        try {
            Log.i(debugTag, Thread.currentThread().getName());
            String result = LastFMHelper.downloadFromServer(params);
            return result;
        } catch (Exception e) {
            System.out.println("Caught exception while trying to download from server.");
            return new String();
        }
    }
     //onPostExecute is run immediately after doInBackGround with the results returned by doInBackground
    //passed to onPostExecute as an argument
     protected void onPostExecute(String result){

         ArrayList<SongInfo> trackdata = new ArrayList<SongInfo> ();

         //progDialog.dismiss(); gets rid of the Progress Dialogue bar initialized in preExecute
         if(result.length() ==0) {
             this.activity.alert("No track data found.");
             return;
         }

        try {

            JSONObject respObject = new JSONObject(result); //converts the httpRequest result to a JSON object
            JSONObject topTracksObj = respObject.getJSONObject("tracks"); //gets the JSONObject value corresponding to the name "tracks"
            JSONArray tracks = topTracksObj.getJSONArray("track"); //gets the array of values corresponding to the name "track". Each of the entries in the array contains all of the information about a single track
            for(int i=0;i<tracks.length();i++) {
                  JSONObject track = tracks.getJSONObject(i); //gets the JSON object associated with this position in the tracks JSON array
                  String trackName = track.getString("name");
                  int listenerNum = track.getInt("listeners");
                  String trackURL = track.getString("url");
                  JSONObject artist = track.getJSONObject("artist");
                  String artistName = artist.getString("name");  //gets the value from the artist object corresponding to the artist name
                  String artistURL = artist.getString("url");

                  trackdata.add(new SongInfo(trackName,artistName,artistURL,trackURL, listenerNum) );

            }

        }
        catch(JSONException e) {
            throw new RuntimeException(e);

        }
         this.activity.setTracks(trackdata);
         //passes the array containing the relevant information about each track to setTracks.
         //setTracks in turn creates an adapter to adapt this information to the UI that displays the songs.
    }

    }


