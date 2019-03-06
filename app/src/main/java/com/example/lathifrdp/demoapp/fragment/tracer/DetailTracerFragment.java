package com.example.lathifrdp.demoapp.fragment.tracer;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.TracerStudy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTracerFragment extends Fragment {
    WebView gform;
    SessionManager sessionManager;
    Bundle bundle;
    String tracer_id;
    ApiInterface apiService;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_tracer,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tracer Study");
        sessionManager = new SessionManager(getActivity());
        bundle = this.getArguments();

        gform = (WebView) getView().findViewById(R.id.webview_tracer);
        if(bundle != null){
            // handle your code here.
            tracer_id = bundle.getString("tracer_id");
        }
        else {
            Toast.makeText(getActivity(), "gagal mengambil tracer study", Toast.LENGTH_SHORT).show();
        }
        loadDataDetail();
    }

    private void loadDataDetail(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<TracerStudy> call = apiService.getDetailTracer("JWT "+ sessionManager.getKeyToken(),tracer_id);
        call.enqueue(new Callback<TracerStudy>() {
            @Override
            public void onResponse(Call<TracerStudy> call, final Response<TracerStudy> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    TracerStudy tr = response.body();
                    pd = new ProgressDialog(getActivity());
                    pd.setMessage("Memuat tracer study, harap menunggu...");
                    pd.setCancelable(false);
                    gform.getSettings().setJavaScriptEnabled(true);
                    gform.setWebViewClient(new WebViewClient(){
                        @Override
                        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                            super.onReceivedError(view, request, error);
                            Toast.makeText(getActivity(), "Maaf, terjadi kesalahan pada saat memuat tracer study", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }

                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);
                            pd.show();
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            pd.dismiss();
                        }
                    });
                    gform.loadUrl(tr.getFormLink());

                }
            }

            @Override
            public void onFailure(Call<TracerStudy> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
