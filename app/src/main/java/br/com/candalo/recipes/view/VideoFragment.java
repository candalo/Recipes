package br.com.candalo.recipes.view;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcels;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.RecipeStep;
import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoFragment extends Fragment {

    private String videoURL;
    private SimpleExoPlayer player;
    private Unbinder unbinder;
    @BindView(R.id.player)
    SimpleExoPlayerView playerView;
    @BindView(R.id.iv_error)
    ImageView errorImageView;
    @BindBool(R.bool.is_tablet)
    boolean isTablet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        injectDependencies(view);
        videoURL = getVideoURL();
        setupPlayerForTablets();

        return view;
    }

    private void injectDependencies(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private String getVideoURL() {
        if (getArguments() != null) {
            RecipeStep step = Parcels.unwrap(getArguments().getParcelable(RecipeStep.class.getName()));
            return step.getVideoURL();
        }

        return "";
    }

    private void setupPlayerForTablets() {
        if (isTablet) {
            setupPlayer();
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        releasePlayer();
        super.onDestroyView();
    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    public void setStep(RecipeStep step) {
        this.videoURL = step.getVideoURL();
        setupPlayer();
    }

    private void setupPlayer() {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelector trackSelector = new DefaultTrackSelector();

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "Recipe"), bandwidthMeter);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoURL));

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        player.prepare(videoSource);
        playerView.setPlayer(player);

        if (videoURL.isEmpty()) {
            errorImageView.setVisibility(View.VISIBLE);
        } else {
            errorImageView.setVisibility(View.INVISIBLE);
        }
    }
}
