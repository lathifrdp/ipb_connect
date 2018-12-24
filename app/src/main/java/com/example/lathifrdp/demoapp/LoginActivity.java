package com.example.lathifrdp.demoapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.lathifrdp.demoapp.response.LoginResponse;

import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.api.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.helper.SessionManager;

/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends AppCompatActivity {
    ApiInterface apiService;
    ProgressDialog progressDialog;
    private SessionManager sessionManager;
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    Button mEmailSignInButton;
    //private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        //coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        //populateAutoComplete();

        /* Session */
        sessionManager = new SessionManager(LoginActivity.this);
        if (sessionManager.isLoggedIn() == true) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            startActivity(intent);
            finish();
        }


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    //attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();
                //login();
                cek();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        TextView regis = (TextView) findViewById(R.id.register_form);
        regis.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

    }

    public void cek() {
        //cek apakah ada kesalahan inputan
        if (validate() == true) {
            //onLoginFailed();
            return;
        }
        //validasi login
        login();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Gagal Masuk", Toast.LENGTH_LONG).show();
        mEmailSignInButton.setEnabled(true);
    }

    /*private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }*/

   /* private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }*/

    /**
     * Callback received when a permissions request has been completed.
     */
    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }*/


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private void login() {
        //progressDialog = ProgressDialog.show(context, null, "Validasi User...",true,false);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        //apiService = ApiUtils.getUserService();

        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();


        Call<LoginResponse> ucall = apiService.postLogin(email, password);
        ucall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {

                    LoginResponse lr = response.body();

                    if(lr.isSuccess()==false ){
                        Toast.makeText(LoginActivity.this, lr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {

                        String token = lr.getToken();
                        String _id = lr.getUser().getId();
                        String photo = lr.getUser().getUserProfile().getPhoto();
                        String email = lr.getUser().getEmail();
                        String fullname = lr.getUser().getFullName();
                        String userType = lr.getUser().getUserType();
                        String name = lr.getUser().getStudyProgram().getName();
                        String crowdfunding = lr.getUser().getCrowdfunding();
                        //getIntent().getByteArrayExtra(photo);

                        //cekLogin();

                        //attemptLogin();
//                    if(lr.isSuccess()==false){
//                        Toast.makeText(LoginActivity.this,lr.getMessage(),Toast.LENGTH_SHORT).show();
//                    }


//                  if (lr.getUser() != null && lr.getUser().getUserProfile() != null && lr.getUser().getUserProfile().getAddress() != null && TextUtils.isEmpty(lr.getUser().getUserProfile().getAddress()))
//                  Toast.makeText(LoginActivity.this, "alamat : "+lr.getUser().getUserProfile().getAddress(), Toast.LENGTH_SHORT).show();

                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        sessionManager.createLoginSession(token, _id, photo, email, fullname, userType, name, crowdfunding);
                        mainIntent.putExtra("tipe",userType);
                        mainIntent.putExtra("prodi",name);
                        LoginActivity.this.startActivity(mainIntent);
                        //mainIntent.putExtra("photoS",photo);

//                        Snackbar snackbar = Snackbar
//                                .make( coordinatorLayout, lr.getMessage(), Snackbar.LENGTH_LONG);
//                        snackbar.show();
                        Toast.makeText(LoginActivity.this, lr.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, userType, Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, name, Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, crowdfunding, Toast.LENGTH_SHORT).show();

//                        Toast.makeText(LoginActivity.this, lr.getUser().getId(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(LoginActivity.this, lr.getUser().getUserType(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(LoginActivity.this, lr.getUser().getStudyProgram().getName(), Toast.LENGTH_SHORT).show();
                        //Snackbar.make(LoginActivity.this,lr.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        //Toast.makeText(LoginActivity.this, "alamat : "+lr.getUser().getUserProfile().getAddress(), Toast.LENGTH_SHORT).show();

                    }
                }
//                if (response.isSuccessful() == false) {
//                    LoginResponse lr = response.body();
//                    Toast.makeText(LoginActivity.this, lr.getMessage(), Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                //Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
                //Log.d("OnFailure", t.toString());
            }
        });

    }

    public boolean validate() {
        boolean valid = false;
        View focusView = null;

        int cekError = 0;

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String uemail = mEmailView.getText().toString();
        String upassword = mPasswordView.getText().toString();


        if (uemail.isEmpty()) {
            mEmailView.setError("Email tidak boleh kosong");
            cekError=1;
            //requestFocus(mEmailView);
            focusView = mEmailView;
            valid = true;
        } else if (!isEmailValid(uemail)) {
            mEmailView.setError("Email tidak valid");
            //requestFocus(mEmailView);
            cekError=1;
            focusView = mEmailView;
            valid = true;
        } else {
            mEmailView.setError(null);
            cekError=0;
        }
        if(cekError==0) {
            if (upassword.isEmpty()) {
                mPasswordView.setError("Password tidak boleh kosong");
                //requestFocus(mPasswordView);

                focusView = mPasswordView;
                valid = true;
            } else {
                mPasswordView.setError(null);
            }
        }
        if (valid) {
            focusView.requestFocus();
        }
        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String uemail = mEmailView.getText().toString();
        String upassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(upassword) && !isPasswordValid(upassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(uemail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(uemail)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(uemail, upassword);
//            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //finish();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}


    /**
     * Shows the progress UI and hides the login form.
     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return new CursorLoader(this,
//                // Retrieve data rows for the device user's 'profile' contact.
//                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
//                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
//
//                // Select only email addresses.
//                ContactsContract.Contacts.Data.MIMETYPE +
//                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
//                .CONTENT_ITEM_TYPE},
//
//                // Show primary email addresses first. Note that there won't be
//                // a primary email address if the user hasn't specified one.
//                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//        List<String> emails = new ArrayList<>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            emails.add(cursor.getString(ProfileQuery.ADDRESS));
//            cursor.moveToNext();
//        }
//
//        addEmailsToAutoComplete(emails);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//    }
//
//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(LoginActivity.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
//
//        mEmailView.setAdapter(adapter);
//    }
//
//
//    private interface ProfileQuery {
//        String[] PROJECTION = {
//                ContactsContract.CommonDataKinds.Email.ADDRESS,
//                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
//        };
//
//        int ADDRESS = 0;
//        int IS_PRIMARY = 1;
//    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mEmail;
//        private final String mPassword;
//
//        UserLoginTask(String email, String password) {
//            mEmail = email;
//            mPassword = password;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//
//            // TODO: register the new account here.
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//
//            if (success) {
//                finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }
//}

