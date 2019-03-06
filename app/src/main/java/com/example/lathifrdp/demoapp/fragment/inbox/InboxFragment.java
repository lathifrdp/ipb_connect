package com.example.lathifrdp.demoapp.fragment.inbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.SenderList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Message;
import com.example.lathifrdp.demoapp.response.InboxResponse;
import com.example.lathifrdp.demoapp.response.SenderResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxFragment extends Fragment {

    private String title;
    TextView tv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    SenderList adapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    private int page = 1;
    private boolean isRefresh = false;
    private List<SenderResponse> listMessage;
    private int limitpage=0;
    Bundle bundle;
    TextView stat;
    String verified = "1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inbox,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Kotak Masuk");
        listView=(ListView)getView().findViewById(R.id.listKontenInbox);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        stat = (TextView) getView().findViewById(R.id.konten_kosong);

        sessionManager = new SessionManager(getActivity());

        if(bundle != null){
            page=1;
        }
        else {
            page=1;
        }

        loadDataMessage();

        bundle = new Bundle();
        listMessage = new ArrayList<>();
        adapter= new SenderList(listMessage,getActivity());
        listView.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page=1;
                loadDataMessage();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment newFragment = null;
                newFragment = new DetailInboxFragment();
                bundle.putString("sender_id",listMessage.get(i).getSender().getId());
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE && listView.getLastVisiblePosition() ==
                        adapter.getSize() - 1){
                    if(page<limitpage+1) {
                        loadDataMessage();
                        //Toast.makeText(getActivity(), "lanjut " + page, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //we don't need this method, so we leave it empty
            }
        });

    }

    private void loadDataMessage(){
        //final String actived = "1";
        final String user = sessionManager.getKeyId();
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<InboxResponse> call = apiService.getSender("JWT "+ sessionManager.getKeyToken(),user);
        call.enqueue(new Callback<InboxResponse>() {
            @Override
            public void onResponse(Call<InboxResponse> call, Response<InboxResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    InboxResponse ir = response.body();

                    if(ir.getSenderResponses().isEmpty()){
                        //Toast.makeText(getActivity(), tr.getMessage(), Toast.LENGTH_SHORT).show();
                        stat.setText("Anda tidak memiliki pesan");
                    }
                    if(ir.getSenderResponses().size() != 0) {
                        //Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();
                        stat.setText("");
                        //for(int i=0; i<ir.getSenderResponses().size();i++){
                            if(isRefresh) adapter.setList(ir.getSenderResponses());
                            else adapter.addList(ir.getSenderResponses());
                        //}
                        isRefresh = false;
                        adapter.notifyDataSetChanged();

                        //int total = tr.getTotal();
                        //int limit = response.body().getLimit();
                        //limitpage = (int)Math.ceil((double)total/limit);
                        page++;
                    }
                }
            }

            @Override
            public void onFailure(Call<InboxResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
