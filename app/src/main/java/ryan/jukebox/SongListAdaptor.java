package ryan.jukebox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ryan.jukebox.ryan.jukebox.api.SongInfo;

/**
 * Created by Ryan on 8/13/2016.
 */
public class SongListAdaptor extends ArrayAdapter<SongInfo> {
    private Context context;
    private ArrayList<SongInfo> objects;

    public SongListAdaptor(Context context, ArrayList<SongInfo> objects) {
        super(context, -1, objects);

        this.context = context;
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.queue_item, parent, false);
        TextView txtSongTitle = (TextView)view.findViewById(R.id.songTitle);
        TextView txtSongArtist = (TextView)view.findViewById(R.id.songArtist);

        txtSongTitle.setText(objects.get(position).name);
        txtSongArtist.setText(objects.get(position).artist);

        return view;
    }
}
