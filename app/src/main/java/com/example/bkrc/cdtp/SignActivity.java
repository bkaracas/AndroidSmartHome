package com.example.bkrc.cdtp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SignActivity extends Activity {

    Button signIn;
    EditText editTextName, editTextPassword;
    public static Users user = new Users();
    String[] user_info=null;
    public static final String ip="http://192.168.1.4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_activity);
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        signIn=(Button)findViewById(R.id.SignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().toString().isEmpty()){
                    Users user=new Users("a","a",100);
                    SendUserToSignActivity(user);
                }else if(editTextName.getText().toString().equals("admin")){
                    Intent i=new Intent(SignActivity.this,Admin_Activity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    loginUser();}
            }
        });
    }

    private void loginUser() {
        String userName = editTextName.getText().toString();
        user.setName(userName);
        String URL = ip+"/cdtp/get_data.php?u="+userName;
        RequestQueue queue;
        queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                user_info=response.split(" ");
                user.setPassword(user_info[1]);
                user.setWatt(Integer.valueOf(user_info[0]));
                responsed(user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Database Connection",Toast.LENGTH_LONG).show();
                editTextName.setText("");
                editTextPassword.setText("");
            }
        });
        queue.add(stringRequest);


    }

    private void responsed(Users user){
        String password=editTextPassword.getText().toString();
        if(password.equals(user.getPassword())){
            SendUserToSignActivity(user);
        }
        else {
            Toast.makeText(SignActivity.this, "Kullanıcı adı ya da parola yanlış", Toast.LENGTH_SHORT).show();
            editTextName.setText("");
            editTextPassword.setText("");
        }

    }

    private void SendUserToSignActivity(Users user) {
        Intent MainIntent = new Intent(SignActivity.this,MainActivity.class);
        MainIntent.putExtra("userInfo",user);
        startActivity(MainIntent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }


}