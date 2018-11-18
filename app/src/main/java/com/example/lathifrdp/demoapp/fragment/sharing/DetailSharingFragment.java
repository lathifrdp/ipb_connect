package com.example.lathifrdp.demoapp.fragment.sharing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.CommentKnowledgeList;
import com.example.lathifrdp.demoapp.adapter.CommentList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Comment;
import com.example.lathifrdp.demoapp.model.KnowledgeSharing;
import com.example.lathifrdp.demoapp.response.GetCommentResponse;
import com.example.lathifrdp.demoapp.response.PostCommentResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSharingFragment extends Fragment{

    SessionManager sessionManager;
    TextView deskripsi,judul_sharing,judul_komen,namanya, nama, angkatan, prodi;
    EditText tulis;
    ImageView foto, kirim,like,download,share,bookmark;
    String sharing_id, msg;
    Bundle bundle;
    CircleImageView fotonya;
    ApiInterface apiService;
    public List<Comment> commentsList;
    private RecyclerView recyclerViewCom;
    CommentKnowledgeList commentAdapter;
    Button btn;
    WebView wv;
    public boolean stateLike = false, stateBookmark = false;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_sharing,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Knowledge");
        sessionManager = new SessionManager(getActivity());

        recyclerViewCom= (RecyclerView) getView().findViewById(R.id.sharing_detail_comment);
        recyclerViewCom.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCom.setHasFixedSize(true);

        bundle = this.getArguments();
        deskripsi = (TextView) getView().findViewById(R.id.sharing_detail_deskripsi);
        //foto = (ImageView) getView().findViewById(R.id.sharing_detail_foto);
        //namanya = (TextView) getView().findViewById(R.id.sharing_nama_posting);
        tulis = (EditText) getView().findViewById(R.id.sharing_tulis_komentar);
        kirim = (ImageView) getView().findViewById(R.id.sharing_kirim_komentar);
        judul_komen = (TextView) getView().findViewById(R.id.sharing_judul_komen);
        judul_sharing = (TextView) getView().findViewById(R.id.sharing_judul_detail);
        download = (ImageView) getView().findViewById(R.id.sharing_download);
        wv = (WebView) getView().findViewById(R.id.webview);
        nama = (TextView) getView().findViewById(R.id.sharing_namanya);
        angkatan = (TextView) getView().findViewById(R.id.sharing_angkatannya);
        prodi = (TextView) getView().findViewById(R.id.sharing_studinya);
        fotonya = (CircleImageView) getView().findViewById(R.id.sharing_fotonya);
        like = (ImageView) getView().findViewById(R.id.sharing_like);
        bookmark = (ImageView) getView().findViewById(R.id.sharing_bookmark);

        if(bundle != null){
            // handle your code here.
            sharing_id = bundle.getString("id_sharing");
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }
        loadDataKnowledge();
        loadComment();
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment();
                //commentAdapter.notifyDataSetChanged();
                //loadComment();
                //tulis.getText().clear();//clears the text
            }
        });
    }
    private void loadDataKnowledge(){

        //spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);

        //final String fullName = full;
        //final String isVerified = x3;

        Call<KnowledgeSharing> call = apiService.getDetailKnowledge("JWT "+ sessionManager.getKeyToken(),sharing_id);
        call.enqueue(new Callback<KnowledgeSharing>() {
            @Override
            public void onResponse(Call<KnowledgeSharing> call, final Response<KnowledgeSharing> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    KnowledgeSharing ks = response.body();
                    //commentsList = memory.getComment();
//                    List<Liker> like = memory.getLiker();
//
//                    commentAdapter = new CommentList(commentsList);
//                    recyclerViewCom.setAdapter(commentAdapter);
//                    recyclerViewCom.smoothScrollToPosition(0);

                    judul_sharing.setText(ks.getTitle());
                    //namanya.setText(ks.getUser().getFullName());
                    deskripsi.setText(ks.getDescription());
                    nama.setText(ks.getUser().getFullName());
                    prodi.setText(ks.getUser().getStudyProgram().getName());
                    angkatan.setText("Angkatan "+ks.getUser().getBatch());

                    String url = "http://api.ipbconnect.cs.ipb.ac.id/uploads/profile/"+ks.getUser().getUserProfile().getPhoto();
                    Picasso.get()
                            .load(url)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.placeholdergambar)
                            .into(fotonya);


                    like.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(stateLike==false) {
                                like.setColorFilter(getContext().getResources().getColor(R.color.merah));
                                stateLike=true;
                            }
                            else if(stateLike==true){
                                like.setColorFilter(getContext().getResources().getColor(R.color.abu));
                                stateLike=false;
                            }
                        }
                    });

                    bookmark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(stateBookmark==false) {
                                bookmark.setColorFilter(getContext().getResources().getColor(R.color.colorPrimary));
                                stateBookmark=true;
                            }
                            else if(stateBookmark==true){
                                bookmark.setColorFilter(getContext().getResources().getColor(R.color.abu));
                                stateBookmark=false;
                            }
                        }
                    });
//                    for(int i=0;i<commentsList.size();i++) {
//                        comment.setText("Comment: " + commentsList.get(i).getCreated());
//                    }
                    //comment.setText("Comment: " + like.size());

                    final String filenya = "http://api.ipbconnect.cs.ipb.ac.id/uploads/knowledgesharing/"+ks.getFile();
                    pd = new ProgressDialog(getActivity());
                    pd.setMessage("Load document, please wait...");
                    pd.setCancelable(false);
                    wv.getSettings().setJavaScriptEnabled(true);
                    wv.setWebViewClient(new WebViewClient(){
                        @Override
                        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                            super.onReceivedError(view, request, error);
                            Toast.makeText(getActivity(), "Maaf, terjadi kesalahan pada saat load document", Toast.LENGTH_SHORT).show();
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
                    wv.loadUrl("https://docs.google.com/gview?url="+filenya+"&embedded=true");

                    download.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View view) {

                            String packageName = "cn.wps.moffice_eng";
                            //boolean isPackageInstalled;
//                            PackageManager pm = getActivity().getPackageManager();
//                            int flags = 0;
//                            try
//                            {
//                                pm.getPackageUid(packageName,flags);
//                            File x = new File(file);
//                                Uri pptUri = Uri.parse(file);
//                                Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
//                                        .setText("open ppt")
//                                        .setType("application/vnd.ms-powerpoint")
//                                        .setStream(pptUri)
//                                        .getIntent()
//                                        //.setPackage("com.google.android.finsky.permission.BIND_GET_INSTALL_REFERRER_SERVICE");
//                                        .setPackage(packageName);
                                        //.setPackage("com.google.android.apps.docs");
                                //startActivity(shareIntent);

//                            Intent intent = new Intent("android.intent.action.VIEW");
//                            intent.addCategory("android.intent.category.DEFAULT");
//                            intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
//                            Uri uri = Uri.parse(file);
//                            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
//                            intent.setPackage(packageName);

                            File myFile = new File(filenya);
//                            try {
//                                FileOpen.openFile(getActivity(), myFile);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            //install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            //install.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                            Uri apkURI = FileProvider.getUriForFile(
                                    getActivity(),
                                    getActivity().getApplicationContext()
                                            .getPackageName() + ".provider", myFile);
                            getActivity().grantUriPermission(getActivity().getPackageName(), apkURI, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Intent install = new Intent(Intent.ACTION_VIEW);
                            install.setDataAndType(apkURI, "application/vnd.ms-powerpoint");
                            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            getActivity().startActivity(install);
//                                if (intent.resolveActivity(getActivity().getPackageManager()) != null){
//                                    startActivity(intent);
//                                    Toast.makeText(getActivity(), uri.toString(), Toast.LENGTH_SHORT).show();
//                                }
//                                else Toast.makeText(getActivity(), "Anda tidak memiliki aplikasinya", Toast.LENGTH_SHORT).show();
                                //isPackageInstalled = true;
//                            }
//                            catch (final PackageManager.NameNotFoundException nnfe)
//                            {
//                                //isPackageInstalled = false;
//                                Toast.makeText(getActivity(), "Anda tidak memiliki aplikasinya", Toast.LENGTH_SHORT).show();
//                            }
                            //return isPackageInstalled;

                            //isPackageExisted("cn.wps.moffice_eng");

                        }
                    });

//                    String url = "http://api.ipbconnect.cs.ipb.ac.id/uploads/knowledgesharing/"+ks.getCover();
//                    Picasso.get()
//                            .load(url)
//                            .placeholder(R.drawable.placegam)
//                            .error(R.drawable.logoipb)
//                            .into(foto);
                }
            }

            @Override
            public void onFailure(Call<KnowledgeSharing> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public static class FileOpen {

        public static void openFile(Context context, File url) throws IOException {
            // Create URI
            File file=url;
            Uri uri = Uri.fromFile(file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            // Check what kind of file you are trying to open, by comparing the url with extensions.
            // When the if condition is matched, plugin sets the correct intent (mime) type,
            // so Android knew what application to use to open the file
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if(url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if(url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if(url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if(url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if(url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if(url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if(url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if(url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if(url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if(url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    private void loadComment(){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<KnowledgeSharing> call = apiService.getCommentKnowledge("JWT "+ sessionManager.getKeyToken(),sharing_id);
        call.enqueue(new Callback<KnowledgeSharing>() {
            @Override
            public void onResponse(Call<KnowledgeSharing> call, final Response<KnowledgeSharing> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    KnowledgeSharing ks = response.body();
                    if(ks.isSuccess()==false && ks.getMessage() == null){
                        commentsList = ks.getComment();
                        judul_komen.setText("Comments (" + commentsList.size() + ") :");
                        commentAdapter = new CommentKnowledgeList(commentsList);
                        recyclerViewCom.setAdapter(commentAdapter);
                        //Toast.makeText(getActivity(),"1 "+ks.getMessage(),Toast.LENGTH_SHORT).show();
                        //recyclerViewCom.smoothScrollToPosition(0);
                    }
                    else {
                        judul_komen.setText("Comments (0) :");
                        //Toast.makeText(getActivity(),"0 "+ks.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<KnowledgeSharing> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void postComment() {
        apiService = ApiClient.getClient().create(ApiInterface.class);

        final String value = tulis.getText().toString();
        final String createdBy = sessionManager.getKeyId();


        Call<PostCommentResponse> ucall = apiService.postCommentKnowledge("JWT "+ sessionManager.getKeyToken(),value,createdBy,sharing_id);
        ucall.enqueue(new Callback<PostCommentResponse>() {
            @Override
            public void onResponse(Call<PostCommentResponse> call, Response<PostCommentResponse> response) {

                if (response.isSuccessful()) {

                    PostCommentResponse cr = response.body();

                    if(cr.isSuccess()==false ){
                        Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        msg = cr.getMessage();

                        Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    loadComment();
                    tulis.getText().clear();
                }
            }

            @Override
            public void onFailure(Call<PostCommentResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
