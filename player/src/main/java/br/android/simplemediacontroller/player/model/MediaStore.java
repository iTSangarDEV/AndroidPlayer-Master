package br.android.simplemediacontroller.player.model;

import java.util.List;

/**
 * Created by diogojayme on 12/17/15.
 */
public class MediaStore {

    private Album album;
    private Artist artist;
    private List<Track> tracks;

    public MediaStore(){}

    public MediaStore create(Album album, Artist artist, List<Track> tracks){
        this.album = album;
        this.artist = artist;
        this.tracks = tracks;
        return this;
    }

    public Album getAlbum(){
        return album;
    }

    public Artist getArtist(){
        return artist;
    }

    public List<Track> getTracks(){
        return tracks;
    }
}
