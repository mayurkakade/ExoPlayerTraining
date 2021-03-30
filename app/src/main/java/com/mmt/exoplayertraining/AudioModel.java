package com.mmt.exoplayertraining;

import android.net.Uri;

public class AudioModel {
    String title;
    Uri audioUri;

    public AudioModel() {
    }

    public AudioModel(String title, Uri audioUri) {
        this.title = title;
        this.audioUri = audioUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Uri getAudioUri() {
        return audioUri;
    }

    public void setAudioUri(Uri audioUri) {
        this.audioUri = audioUri;
    }
}
