package com.example.lathifrdp.demoapp.fragment.crowdfunding.alumni;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Crowdfunding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdMediaFragment extends Fragment{

    //ImageView play, pause;
    VideoView videoView;
    WebView webView;
    ApiInterface apiService;
    SessionManager sessionManager;
    Bundle bundle;
    private String id_crowd;
    MediaController mediaController;
    ProgressDialog pd_dok, pd_vid;
    ProgressBar progressBar = null;

    public static CrowdMediaFragment newInstance(String id) {

        Bundle args = new Bundle();
        CrowdMediaFragment fragment = new CrowdMediaFragment();
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crowd_media_alumni,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(getActivity());
        videoView = (VideoView) getView().findViewById(R.id.video_crowd_alumni);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressbar);
//        play = (ImageView) getView().findViewById(R.id.play_crowd_alumni);
//        pause = (ImageView) getView().findViewById(R.id.pause_crowd_alumni);
        webView = (WebView) getView().findViewById(R.id.file_crowd_alumni);
        mediaController = new MediaController(getActivity());
        pd_dok = new ProgressDialog(getActivity());
        pd_dok.setMessage("Memuat dokumen, harap menunggu...");

        pd_vid = new ProgressDialog(getActivity());
        pd_vid.setMessage("Memuat video, harap menunggu...");
        progressBar.setVisibility(View.VISIBLE);

        bundle = this.getArguments();

        if(bundle != null){
            id_crowd = bundle.getString("id");
            loadDataMedia();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }

//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                videoView.start();
//            }
//        });
//
//        pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                videoView.pause();
//            }
//        });
    }

    private void loadDataMedia(){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Crowdfunding> call = apiService.getDetailCrowd("JWT "+ sessionManager.getKeyToken(),id_crowd);
        call.enqueue(new Callback<Crowdfunding>() {
            @Override
            public void onResponse(Call<Crowdfunding> call, final Response<Crowdfunding> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    Crowdfunding crowd = response.body();

                    String vid = new BaseModel().getCrowdfundingUrl()+crowd.getProposalVideo();
                    videoView.setVideoPath(vid);
                    mediaController.setAnchorView(videoView);
                    videoView.setMediaController(mediaController);
                    //videoView.start();

                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            //mediaPlayer.start();
                            mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                                @Override
                                public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                                    progressBar.setVisibility(View.GONE);
                                    mediaPlayer.start();
                                }
                            });
                        }
                    });

                    String dok = new BaseModel().getCrowdfundingUrl()+crowd.getFile();
                    webView.getSettings().setJavaScriptEnabled(true);

                    webView.setWebViewClient(new WebViewClient(){
                        @Override
                        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                            super.onReceivedError(view, request, error);
                            Toast.makeText(getActivity(), "Maaf, terjadi kesalahan pada saat memuat dokumen", Toast.LENGTH_SHORT).show();
                            pd_dok.dismiss();
                        }

                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);
                            pd_dok.show();
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            pd_dok.dismiss();
                        }
                    });

                    webView.loadUrl("https://docs.google.com/gview?url="+dok+"&embedded=true");
                }
            }

            @Override
            public void onFailure(Call<Crowdfunding> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
