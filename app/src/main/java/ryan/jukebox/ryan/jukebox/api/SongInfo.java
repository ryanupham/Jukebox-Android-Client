package ryan.jukebox.ryan.jukebox.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ryan on 8/13/2016.
 */
public class SongInfo {
    public String name, artist, album, uri;

    public SongInfo(String name, String artist, String album, String uri) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.uri = uri;
    }

    public SongInfo() {
        this("", "", "", "");
    }

    public SongInfo(JSONObject song) {
        try {
            this.name = song.getString("name");
            this.artist = song.getString("artist");
            this.album = song.getString("album");
            this.uri = song.getString("uri");
        } catch(JSONException e) {
            this.name = "";
            this.artist = "";
            this.album = "";
            this.uri = "";
        }
    }
}
