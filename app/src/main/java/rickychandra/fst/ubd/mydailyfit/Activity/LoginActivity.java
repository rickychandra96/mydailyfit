package rickychandra.fst.ubd.mydailyfit.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import rickychandra.fst.ubd.mydailyfit.Network.RequestHandler;
import rickychandra.fst.ubd.mydailyfit.R;
import rickychandra.fst.ubd.mydailyfit.Util.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText editUsername, editPassword;
    private ImageView btnLogin, btnRegister;

    SessionManager sessionManager;
    RequestHandler requestHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setupListener();
    }
    private void setupListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                new AsyncLogin().execute(username, password);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
        editUsername = (EditText) findViewById(R.id.login_activity_editUsername);
        editPassword = (EditText) findViewById(R.id.login_activity_editPassword);
        btnLogin = (ImageView) findViewById(R.id.login_activity_btnLogin);
        btnRegister = (ImageView) findViewById(R.id.login_activity_btnRegister);
        sessionManager = new SessionManager(this);
    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        URL url = null;
        HttpURLConnection conn;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading........");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("http://172.20.10.4/mydailyfit/login.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.getMessage();

            }

            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(15000);
                conn.setRequestMethod("POST");

                conn.setDoOutput(true);
                conn.setDoInput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(query);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }

            try {
                int responseCode = conn.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while((line=bufferedReader.readLine())!=null){
                        result.append(line);
                    }
                    return result.toString();
                }else{
                    return (responseCode + "");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            if(s.equalsIgnoreCase("true")){
                sessionManager.createLoginSession(editUsername.getText().toString());

                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("username", editUsername.getText().toString());
                startActivity(intent);
                finish();
            }else if(s.equalsIgnoreCase("false")){
                Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
