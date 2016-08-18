package ryan.jukebox.ryan.jukebox.api;

import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ryan on 8/12/2016.
 */
public class JukeboxAPI {
    private static final String URL_PREFIX = "http://192.168.1.130:5000/";

    private JukeboxAPI() {

    }

    private static String getAPIRequest(String postfix) {
        postfix = Uri.encode(postfix);

        //http://www.androidauthority.com/use-remote-web-api-within-android-app-617869/
        try {
            URL url = new URL(URL_PREFIX + postfix);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuilder builder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }

                reader.close();

                return builder.toString().trim();
            } finally {
                connection.disconnect();
            }
        } catch(Exception e) {
            Log.e("Error", e.getMessage(), e);
            return "";
        }
    }

    public static String getMAC(Context context) {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo().getMacAddress();
    }

    public static boolean addUser(String uid, String name) {
        String response = getAPIRequest("user/add/" + name + "/" + uid);

        return response.trim().equals("\"success\"");
    }

    public static JSONObject getUserInfo(String uid) {
        if(userExists(uid)) {
            try {
                return new JSONObject(getAPIRequest("user/info/" + uid));
            } catch(JSONException e) {
                return null;
            }
        }

        return null;
    }

    public static boolean userExists(String uid) {
        String response = getAPIRequest("user/info/" + uid + "/");

        return !response.equals("\"invalid\"");
    }

    public static String getName(String uid) {
        JSONObject user = getUserInfo(uid);

        try {
            return user.getString("name");
        } catch(Exception e) {
            return "";
        }
    }

    public static int getTokens(String uid) {
        JSONObject user = getUserInfo(uid);

        try {
            return user.getInt("tokens");
        } catch(Exception e) {
            return 0;
        }
    }

    public static boolean skipSong(String uid) {
        String response = getAPIRequest("queue/skip/" + uid);

        return response.trim().equals("\"success\"");
    }

    public static boolean voteSkipSong(String uid, int index) {
        String response = getAPIRequest("queue/vote_skip/" + uid + "/" + index);

        return response.trim().equals("\"success\"");
    }

    public static boolean removeSong(String uid, int index) {
        String response = getAPIRequest("queue/remove/" + uid + "/" + index);

        return response.trim().equals("\"success\"");
    }

    public static boolean addSong(String uid, String name, String artist, String album, String uri) {
        String response = getAPIRequest("queue/add/" + uid + "/" + name + "/" + artist + "/" + album + "/" + uri);

        return response.trim().equals("\"success\"");
    }

    public static boolean playNext(String uid, String name, String artist, String album, String uri) {
        String response = getAPIRequest("queue/add_next/" + uid + "/" + name + "/" + artist + "/" + album + "/" + uri);

        return response.trim().equals("\"success\"");
    }

    private static SongInfo[] JSONArrayToSongInfo(JSONArray arr) {
        try {
            SongInfo[] infoArr = new SongInfo[arr.length()];

            for(int i = 0; i < arr.length(); i++)
                infoArr[i] = new SongInfo(arr.getJSONObject(i));

            return infoArr;
        } catch (JSONException e) {
            return new SongInfo[0];
        }
    }

    public static SongInfo[] getQueue() {
        try {
            return JSONArrayToSongInfo(new JSONArray(getAPIRequest("queue")));
        } catch(JSONException e) {
            return new SongInfo[0];
        }
    }

    public static SongInfo[] search(String query) {
        try {
            return JSONArrayToSongInfo(new JSONArray(getAPIRequest("search/song/" + query)));
        } catch(JSONException e) {
            return new SongInfo[0];
        }
    }

    public static TrackInfo getTrackInfo() {
        try {
            return new TrackInfo(new JSONObject(getAPIRequest("player")));
        } catch(JSONException e) {
            return new TrackInfo("", "", false, 0, 0);
        }
    }
}
