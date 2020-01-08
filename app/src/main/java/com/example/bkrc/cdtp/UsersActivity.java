package com.example.bkrc.cdtp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import static com.example.bkrc.cdtp.MainActivity.messageList;
import static com.example.bkrc.cdtp.MainActivity.userList;
import static com.example.bkrc.cdtp.SignActivity.ip;
import static com.example.bkrc.cdtp.SignActivity.user;

public class UsersActivity extends Activity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private UserAdapter mAdapter;
    RelativeLayout relativeLayout;
    Button ref;
    String[] message_info=null;
    String[] user_info=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        ref=(Button)findViewById(R.id.user_refresh);
        recyclerView=(RecyclerView)findViewById(R.id.user_Activity_recyclerview);
        relativeLayout=(RelativeLayout)findViewById(R.id.user_rellay);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter= new UserAdapter(this, MainActivity.userList);
        recyclerView.setAdapter(mAdapter);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageList.clear();
                getMessages();
                userList.clear();
                getUsers();
            }
        });
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList.clear();
                getUsers();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        messageList.clear();
        getMessages();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(this,MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

    public void getMessages() {

        final String userName=user.getName();
        String URL = ip+"/cdtp/get_message2.php";
        final RequestQueue queue;
        queue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                message_info=response.split(",");
                int len=message_info.length/4;
                String[] time=null;
                for(int i=0;i<len;i++){
                    time=message_info[i*4+2].split(":");
                    messageList.add(new Message(message_info[i*4+1],message_info[i*4+3],message_info[i*4],time[0],time[1]));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Database Connection",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    public void getUsers(){
        String userName=user.getName();
        String URL = ip+"/cdtp/get_users.php?u="+userName;
        final RequestQueue queue;
        queue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                user_info=response.split(" ");
                int len=user_info.length/6;
                for(int i=0;i<len;i++){
                    userList.add(new Users(user_info[i*6+1],user_info[i*6+2],user_info[i*6+4],user_info[i*6+5],Integer.valueOf(user_info[i*6+3]),Integer.valueOf(user_info[i*6])));
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Database Connection",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

}