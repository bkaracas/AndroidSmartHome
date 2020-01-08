package com.example.bkrc.cdtp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import static com.example.bkrc.cdtp.SignActivity.ip;
import static com.example.bkrc.cdtp.SignActivity.user;

public class Admin_Activity extends Activity {

    TextView saat, d1, d2, d3, d4;
    String timeString;
    int timeInt;
    String[] user_info = null;
    int dd1,dd2,dd3,dd4;
    private Handler mHandler = new Handler();
    public int getTimeInt() {
        return timeInt;
    }

    public void setTimeInt(int timeInt) {
        this.timeInt = timeInt;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);
        saat = (TextView) findViewById(R.id.saat);
        d1 = (TextView) findViewById(R.id.daire1);
        d2 = (TextView) findViewById(R.id.daire2);
        d3 = (TextView) findViewById(R.id.daire3);
        d4 = (TextView) findViewById(R.id.daire4);
        getTime();
        getUsers();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        runnable.run();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mHandler.removeCallbacks(runnable);
        Intent SignIntent = new Intent(this,SignActivity.class);
        startActivity(SignIntent);
        finish();
    }

    private void getTime() {
        String URL = ip + "/cdtp/get_time2";
        final RequestQueue queue;
        queue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                timeString = response;
                System.out.println(timeString);
                setTimeInt(Integer.valueOf(timeString));
                saat.setText(timeString);
                saat.append(":00");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Database Connection", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }


    public void getUsers() {

        String userName = "f";
        String URL = ip + "/cdtp/get_users.php?u=" + userName;
        final RequestQueue queue;
        queue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                user_info = response.split(" ");
                dd1=Integer.valueOf(user_info[3]);
                dd2=Integer.valueOf(user_info[6 + 3]);
                dd3=Integer.valueOf(user_info[2 * 6 + 3]);
                dd4=Integer.valueOf(user_info[3 * 6 + 3]);

                d1.setText("1.Daire: ");
                d1.append(String.valueOf(dd1));
                d2.setText("2.Daire: ");
                d2.append(String.valueOf(dd2));
                d3.setText("3.Daire: ");
                d3.append(String.valueOf(dd3));
                d4.setText("4.Daire: ");
                d4.append(String.valueOf(dd4));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Database Connection", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    private Runnable runnable = new Runnable(){
        public void run(){
            getTime();
            getUsers();
            mHandler.postDelayed(runnable,2000);

        }

    };


}