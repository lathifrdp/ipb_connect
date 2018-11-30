package com.example.lathifrdp.demoapp.fragment.post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.MemoriesList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Memory;
import com.example.lathifrdp.demoapp.response.MemoriesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class PostMemoriesFragment extends Fragment{

    public List<Memory> galleryItems;
    private RecyclerView recyclerViewGallery;
    MemoriesList mGalleryAdapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    private int page = 1;
    private int limitpage=0;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_memories,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Memories");
        sessionManager = new SessionManager(getActivity());

        recyclerViewGallery= (RecyclerView) getView().findViewById(R.id.recc);
        recyclerViewGallery.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        if(bundle != null){
            page=1;
        }
        else {
            page=1;
        }
        loadMemories();

        galleryItems = new ArrayList<>();
        mGalleryAdapter= new MemoriesList(galleryItems);

        recyclerViewGallery.setAdapter(mGalleryAdapter);
        recyclerViewGallery.smoothScrollToPosition(0);
        recyclerViewGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == SCROLL_STATE_IDLE){
                    if (page<limitpage+1) {
                        // method where you get your images
                        loadMemories();
                        //Toast.makeText(getActivity(), "lanjut " + page, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void loadMemories() {

            apiService = ApiClient.getClient().create(ApiInterface.class);
            final String createdBy = sessionManager.getKeyId();

            Call<MemoriesResponse> call = apiService.getMemoriesPost("JWT "+ sessionManager.getKeyToken(),createdBy,page);
            call.enqueue(new Callback<MemoriesResponse>() {

                @Override
                public void onResponse(Call<MemoriesResponse> call, final Response<MemoriesResponse> response) {
                    if (response.isSuccessful()) {

                        mGalleryAdapter.addList(response.body().getMemories());
                        mGalleryAdapter.notifyDataSetChanged();
                        int total = response.body().getTotal();
                        int limit = response.body().getLimit();
                        limitpage = (int)Math.ceil((double)total/limit);

                        //Log.e("limitpage: ",String.valueOf(limitpage));
                        page++;
                    }
                }

                @Override
                public void onFailure(Call<MemoriesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getActivity(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();
//                    pd.hide();
                }

            });
    }
}
