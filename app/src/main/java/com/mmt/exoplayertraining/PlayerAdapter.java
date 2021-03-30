package com.mmt.exoplayertraining;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    List<AudioModel> sList;
    Context context;
    SimpleExoPlayer player;

    public static final String TAG = "exo_music_logs";

    public PlayerAdapter(List<AudioModel> sList, Context context, SimpleExoPlayer player) {
        this.sList = sList;
        this.context = context;
        this.player = player;
    }

    @NonNull
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_music,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.ViewHolder holder, int position) {
        holder.text.setText(sList.get(position).getTitle());

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToQueue(sList.get(position));
            }
        });
    }

    private void addToQueue(AudioModel audioModel) {
        Log.d(TAG, "addToQueue: " + audioModel.audioUri.toString());
        MediaItem item = MediaItem.fromUri(audioModel.audioUri);
        player.addMediaItem(item);
        player.prepare();
        player.play();
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.bt_text);
        }
    }
}
