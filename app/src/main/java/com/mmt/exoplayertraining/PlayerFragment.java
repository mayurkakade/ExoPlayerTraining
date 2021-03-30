package com.mmt.exoplayertraining;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PlayerFragment extends Fragment {

    private static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 700 ;
    SimpleExoPlayer player;
    List<AudioModel> songs;
    PlayerAdapter adapter;
    RecyclerView recyclerView;
    public static final String TAG = "exo_music_logs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        // Inflate the layout for this fragment

        player = new SimpleExoPlayer.Builder(container.getContext()).build();
        PlayerView exoPlayer = view.findViewById(R.id.exo_player);
        exoPlayer.setPlayer(player);

        songs = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        songs = getAllSongs();
        adapter = new PlayerAdapter(songs,container.getContext(),player);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        requestPermission();

        return view;
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
            }
        }
    }


    private List<AudioModel> getAllSongs() {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
        };

        Cursor cursor = getContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);



        List<AudioModel> songs = new ArrayList<AudioModel>();
        while(cursor.moveToNext()){
            Uri audioUri=
                    ContentUris
                            .withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                    cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID)));
            AudioModel audioModel = new AudioModel(cursor.getString(cursor.getColumnIndex( MediaStore.Audio.Media.TITLE)),audioUri);

            songs.add(audioModel);
        }



        return songs;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // check whether storage permission granted or not.
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // do what you want;
                    }
                }
                break;
            default:
                break;
        }
    }

}