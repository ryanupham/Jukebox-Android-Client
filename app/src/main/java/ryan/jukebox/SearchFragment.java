package ryan.jukebox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;

import ryan.jukebox.ryan.jukebox.api.JukeboxAPI;
import ryan.jukebox.ryan.jukebox.api.SongInfo;

public class SearchFragment extends ListFragment {
    private ArrayList<SongInfo> searchResults = new ArrayList<SongInfo>();
    private SongListAdaptor adapter;

    public SearchFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SearchView searchView = (SearchView)getActivity().findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SongInfo[] results = JukeboxAPI.search(query);
                setSearchResults(new ArrayList<SongInfo>(Arrays.asList(results)));

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        setSearchResults(searchResults);
    }

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);

        final SongInfo song = searchResults.get(position);

        PopupMenu popup = new PopupMenu(getContext(), getView());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.itemAddToQueue:
                        JukeboxAPI.addSong(JukeboxAPI.getMAC(getContext()),
                                song.name, song.artist, song.album, song.uri);
                        return true;
                    case R.id.itemPlayNext:
                        JukeboxAPI.playNext(JukeboxAPI.getMAC(getContext()),
                                song.name, song.artist, song.album, song.uri);
                        return true;
                }

                return true;
            }
        });
        popup.inflate(R.menu.popup_menu_search);
        popup.show();
    }

    public void setSearchResults(ArrayList<SongInfo> searchResults) {
        if(isAdded()) {
            if(searchResults != this.searchResults || adapter == null) {
                this.searchResults = searchResults;
                adapter = new SongListAdaptor(getActivity(), this.searchResults);
                setListAdapter(adapter);
            }

            adapter.notifyDataSetChanged();
        }
    }
}