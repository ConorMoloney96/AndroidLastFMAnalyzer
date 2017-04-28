package com.example.conor_000.lastfmanalyzer;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by conor_000 on 17/03/2017.
 */

public class LastFMHelper {
    private static final String LastFMMetroTrackChartUrl = "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&api_key=3cc7f468dabeb924f6c76da1a495f3e9&format=json";
    private static final int HTTP_STATUS_OK = 200;
    private static byte[] buff = new byte[1024];
    private static final String logHeading = "LastFMHelper";


    //inner class defined to use to handle exceptions when reading from LastFM database
    public static class APIException extends Exception{
         private static final long serialVersionUID=1L;

         public APIException(String message){
            super(message);
        }
        public APIException(String message, Throwable thr){
            super(message, thr);
        }
    }
    //downloads the requested tracks from the LastFM server
    protected static synchronized String downloadFromServer(String... params) throws APIException {

          String retval=null;
          String metro = params[0]; //the Geo/metro selected from the Spinner is what is being passed as params here

          String urlName = LastFMMetroTrackChartUrl + "&country=" + metro;



          Log.i(logHeading, "Fetching" + urlName);


          HttpURLConnection urlConnection = null;

          try {
              URL url = new URL(urlName);  //creates a new object of type URL to store the information about the universal resource locator. The URL objct must be created inside a try block in case of a malformed URL Exception
              urlConnection = (HttpURLConnection) url.openConnection(); //creates a connection object to this url

              int responseCode = urlConnection.getResponseCode();


              if(responseCode!= HTTP_STATUS_OK)
              //if the responses status is not 200 to indicate a valid response
              {
                    throw new APIException("Invalid response from last.fm"+ responseCode);
              }

              ByteArrayOutputStream content = new ByteArrayOutputStream();
              InputStream in = urlConnection.getInputStream();

              int readCount = 0;
              while((readCount=in.read(buff) )!=-1 ) {
                    content.write(buff,0,readCount); //writes to content. buff is the buffer, 0 is the offset before starting writing and readCount is the length of the write
              }

              retval= new String(content.toByteArray() );

          }
          catch(Exception e){
                throw new APIException("Unable to download as there was a problem connecting to the server"+ e.getMessage(), e);
          }


        return retval;


    }


}
