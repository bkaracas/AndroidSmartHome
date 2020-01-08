package com.example.bkrc.cdtp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.List;
import static com.example.bkrc.cdtp.SignActivity.ip;
import static com.example.bkrc.cdtp.SignActivity.user;

public class MainActivity extends AppCompatActivity {

    static public List<Message> messageList;
    static public List<Users> userList;
    TextView userInfo;
    Button payment,message,component;
    String[] user_info=null;
    String[] user_info2=null;
    String[] message_info=null;
    static public String[] components=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInfo = (TextView) findViewById(R.id.asdqwe);
        payment  = (Button)findViewById(R.id.b1) ;
        message  = (Button)findViewById(R.id.b2) ;
        component  = (Button)findViewById(R.id.b3) ;
        userList = new ArrayList<Users>();
        messageList = new ArrayList<Message>();
        getUserInfo();
        getUsers();
        getMessages();
        getComponents();
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToPayment();
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToMessage();
            }
        });
        component.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToComponents();
            }
        });
        if(user.getName().equals("a")){
            component.setVisibility(View.VISIBLE);
        }else{
            component.setVisibility(View.INVISIBLE);
        }

    }

    private void sendToComponents() {
        Intent componentIntent = new Intent(this,ComponentActivity.class);
        //messageIntent.putExtra("userInfo",user);
        startActivity(componentIntent);
        finish();
    }

    private void getUserInfo() {
        String URL = ip+"/cdtp/get_user_info.php?u="+user.getName();
        final RequestQueue queue;
        queue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                user_info2=response.split(" ");
                user.setId(Integer.parseInt(user_info2[0]));
                user.setRealname(user_info2[4]);
                user.setSurname(user_info2[5]);
                userInfo.setText("\nKullanıcı Bilgileri:\n\n" + user.getRealname() +" "+  user.getSurname()+"\nDaire: "+user.getId() + "\n\nKalan Watt: " + user.getWatt());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Database Connection",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);

    }

    private void sendToMessage() {

        Intent messageIntent = new Intent(this,UsersActivity.class);
        messageIntent.putExtra("userInfo",user);
        startActivity(messageIntent);
        finish();
    }

    private void sendToPayment() {

        Intent mainIntent = new Intent(this,PaymentActivity.class);
        mainIntent.putExtra("userInfo",user);
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



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Database Connection",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent signIntent = new Intent(this,SignActivity.class);
        startActivity(signIntent);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void getComponents(){
        String userName=user.getName();
        String URL = ip+"/cdtp/get_comps.php?u="+userName;
        final RequestQueue queue;

        queue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                components=response.split(" ");

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
