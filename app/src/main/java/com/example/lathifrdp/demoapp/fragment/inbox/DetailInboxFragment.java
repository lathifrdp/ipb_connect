package com.example.lathifrdp.demoapp.fragment.inbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.lathifrdp.demoapp.adapter.InboxList;
import com.example.lathifrdp.demoapp.adapter.SenderList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Message;
import com.example.lathifrdp.demoapp.response.InboxResponse;
import com.example.lathifrdp.demoapp.response.MessageResponse;
import com.example.lathifrdp.demoapp.response.SenderResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailInboxFragment extends Fragment {

    private String title;
    TextView tv;
    ListView listView;
    InboxList adapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    private int page = 1;
    private boolean isRefresh = false;
    private List<Message> listMessage;
    private int limitpage=0;
    Bundle bundle;
    TextView stat, nama;
    CircleImageView foto;
    String sender_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_inbox,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Kotak Masuk");
        listView=(ListView)getView().findViewById(R.id.listKontenInbox);
        stat = (TextView) getView().findViewById(R.id.konten_kosong);
        nama = (TextView) getView().findViewById(R.id.nama_orang);
        foto = (CircleImageView) getView().findViewById(R.id.foto_orang);

        sessionManager = new SessionManager(getActivity());
        bundle = this.getArguments();

        if(bundle != null){
            page=1;
            sender_id = bundle.getString("sender_id");
        }
        else {
            page=1;
        }

        loadDataMessage();

        bundle = new Bundle();
        listMessage = new ArrayList<>();
        adapter= new InboxList(listMessage,getActivity());
        listView.setAdapter(adapter);

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

        Call<MessageResponse> call = apiService.getMessage("JWT "+ sessionManager.getKeyToken(),user,sender_id);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse mr = response.body();

                    if(mr.getMessages().isEmpty()){
                        //Toast.makeText(getActivity(), tr.getMessage(), Toast.LENGTH_SHORT).show();
                        stat.setText("Anda tidak memiliki pesan");
                    }
                    if(mr.getMessages().size() != 0) {
                        //Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();
                        stat.setText("");
                        nama.setText(mr.getMessages().get(0).getSender().getFullName());
                        String url = new BaseModel().getProfileUrl()+mr.getMessages().get(0).getSender().getUserProfile().getPhoto();
                        Picasso.get()
                                .load(url)
                                .placeholder(R.drawable.placegam)
                                .error(R.drawable.placeholdergambar)
                                .into(foto);

                        if(isRefresh) adapter.setList(mr.getMessages());
                        else adapter.addList(mr.getMessages());

                        isRefresh = false;
                        adapter.notifyDataSetChanged();
                        page++;
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
