package com.example.conor_000.lastfmanalyzer;

/**
 * Created by conor_000 on 14/03/2017.
 */

public class SongInfo {
    private String name;
    private String artist;
    private String imageUrl;
    private String artistUrl;
    private String trackUrl;
    private int listenerNum;

    public SongInfo(String name, String artist, String artistURL, String trackURL, int listenerNum) {

        this.name = name;
        this.artist = artist;
        this.artistUrl = artistURL;
        this.trackUrl = trackURL;
        this.listenerNum = listenerNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArtistUrl() {
        return artistUrl;
    }

    public void setArtistUrl(String artistUrl) {
        this.artistUrl = artistUrl;
    }

    public String getTrackUrl() {
        return trackUrl;
    }

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }

    public void setListenerNum(int listenerNum) {
        this.listenerNum=listenerNum;
    }

    public int getListenerNum() {
        return listenerNum;
    }




}


