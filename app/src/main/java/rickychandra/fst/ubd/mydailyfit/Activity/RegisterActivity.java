package rickychandra.fst.ubd.mydailyfit.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import rickychandra.fst.ubd.mydailyfit.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText editName, editUsername, editPassword, editConfirmPassword;
    private Button btnRegister;
    private TextView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        setupListener();
    }

    private void setupListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                String confirmPassword = editConfirmPassword.getText().toString();

                if(password.equals(confirmPassword)){
                    new AsyncRegister().execute(name, username, password);
                }else{
                    Toast.makeText(RegisterActivity.this,"Password mixmatch", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void init() {
        editName = (EditText) findViewById(R.id.register_activity_name);
        editUsername = (EditText) findViewById(R.id.register_activity_username);
        editPassword = (EditText) findViewById(R.id.register_activity_password);
        editConfirmPassword = (EditText) findViewById(R.id.register_activity_confirm_password);
        btnRegister = (Button) findViewById(R.id.register_activity_register);
        btnLogin = (TextView) findViewById(R.id.register_activity_login);
    }

    private class AsyncRegister extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
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
                url = new URL("http://172.20.10.4/mydailyfit/register.php");
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
                        .appendQueryParameter("name", params[0])
                        .appendQueryParameter("username", params[1])
                        .appendQueryParameter("password", params[2]);
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
                Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else if(s.equalsIgnoreCase("false")){
                Toast.makeText(RegisterActivity.this, "Invalid.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
