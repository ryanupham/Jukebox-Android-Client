package ryan.jukebox.ryan.jukebox.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ryan on 8/17/2016.
 */
public class TrackInfo {
    private String trackName;
    private String artist;
    private boolean playing;
    private double duration;
    private double seekTime;

    public TrackInfo(String trackName, String artist, boolean playing, double duration, double seekTime) {
        this.trackName = trackName;
        this.artist = artist;
        this.playing = playing;
        this.duration = duration;
        this.seekTime = seekTime;
    }

    public TrackInfo(JSONObject obj) {
        try {
            this.trackName = obj.getString("name");
            this.artist = obj.getString("artist");
            this.playing = obj.getBoolean("playing");
            this.duration = obj.getDouble("duration");
            this.seekTime = obj.getDouble("seek");
        } catch(JSONException e) {
            this.trackName = "";
            this.artist = "";
            this.playing = false;
            this.duration = 0;
            this.seekTime = 0;
        }
    }

    public String getTrackName() {
        return trackName;
    }

    public String getArtist() {
        return artist;
    }

    public boolean isPlaying() {
        return playing;
    }

    public double getDuration() {
        return duration;
    }

    public double getSeekTime() {
        return seekTime;
    }
}
