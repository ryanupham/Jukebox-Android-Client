package ryan.jukebox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import ryan.jukebox.ryan.jukebox.api.JukeboxAPI;
import ryan.jukebox.ryan.jukebox.api.SongInfo;

public class QueueFragment extends ListFragment {
    private ArrayList<SongInfo> queueData = new ArrayList<SongInfo>();
    private SongListAdaptor adapter;

    public QueueFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setQueueData(queueData);
    }

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);

        PopupMenu popup = new PopupMenu(getContext(), getView());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.itemRemove:
                        JukeboxAPI.removeSong(JukeboxAPI.getMAC(getContext()), position);
                        return true;
                    case R.id.itemVoteRemove:
                        JukeboxAPI.voteSkipSong(JukeboxAPI.getMAC(getContext()), position);
                        return true;
                }

                return true;
            }
        });
        popup.inflate(R.menu.popup_menu_queue);
        popup.show();
    }

    public void setQueueData(ArrayList<SongInfo> queueData) {
        if(isAdded()) {
            if(queueData != this.queueData || adapter == null) {
                this.queueData = queueData;
                adapter = new SongListAdaptor(getActivity(), this.queueData);
                setListAdapter(adapter);
            }

            adapter.notifyDataSetChanged();
        }
    }
}