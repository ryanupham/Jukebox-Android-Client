package ryan.jukebox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import ryan.jukebox.R;
import ryan.jukebox.ryan.jukebox.api.JukeboxAPI;
import ryan.jukebox.ryan.jukebox.api.TrackInfo;

/**
 * Created by Ryan on 8/17/2016.
 */
public class PlayerFragment extends Fragment {

    public PlayerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().findViewById(R.id.buttonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JukeboxAPI.skipSong(JukeboxAPI.getMAC(getContext()));
            }
        });

        getActivity().findViewById(R.id.buttonVoteNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JukeboxAPI.voteSkipSong(JukeboxAPI.getMAC(getContext()), 0);
            }
        });

        getActivity().findViewById(R.id.seekBar).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void setTrackInfo(TrackInfo info) {
        if(isAdded()) {
            SeekBar seekBar = (SeekBar) getActivity().findViewById(R.id.seekBar);

            if(info.isPlaying()) {
                seekBar.setMax((int) info.getDuration());
                seekBar.setProgress((int) info.getSeekTime());
            } else {
                seekBar.setMax(1);
                seekBar.setProgress(0);
            }
        }
    }
}
