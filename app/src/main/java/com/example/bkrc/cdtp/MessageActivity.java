package com.example.bkrc.cdtp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.example.bkrc.cdtp.MainActivity.messageList;
import static com.example.bkrc.cdtp.SignActivity.ip;
import static com.example.bkrc.cdtp.SignActivity.user;

public class MessageActivity extends Activity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private MessageAdapter mesAdapter;
    private Handler mHandler = new Handler();
    String[] message_info=null;
    RelativeLayout rellay2;
    TextView textsender;
    static Users  sender;
    Button send_button,miktar_button;
    EditText send_text,miktar_text;
    int listSize=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        textsender = (TextView)findViewById(R.id.text_sender);
        send_button = (Button) findViewById(R.id.send_button);
        miktar_button = (Button) findViewById(R.id.gonderbutton);
        send_text = (EditText) findViewById(R.id.send_text);
        miktar_text = (EditText) findViewById(R.id.gondertext);
        recyclerView=(RecyclerView)findViewById(R.id.message_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        rellay2=(RelativeLayout)findViewById(R.id.message_rellay2);


        rellay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageList.clear();
                getMessages();

            }


        });
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        miktar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bakiyeGonder();
            }


        });
        Intent i = getIntent();
        if (i.hasExtra("userInfo")) {
            sender = (Users) i.getSerializableExtra("userInfo");

        }
        textsender.setText(sender.getId()+"-"+ sender.getRealname()+" "+sender.getSurname());

        final List<Message> a=new ArrayList<Message>();
        for(int q=0;q<MainActivity.messageList.size();q++){
            if(MainActivity.messageList.get(q).getSender().equals(sender.getName()) &&MainActivity.messageList.get(q).getReceiver().equals(user.getName())    ) {
                a.add(new Message(MainActivity.messageList.get(q).getSender(), MainActivity.messageList.get(q).getReceiver(), MainActivity.messageList.get(q).getMes(), MainActivity.messageList.get(q).getHour(), MainActivity.messageList.get(q).getMin()));
            }
            else if(MainActivity.messageList.get(q).getSender().equals(user.getName()) && MainActivity.messageList.get(q).getReceiver().equals(sender.getName())) {
                a.add(new Message(MainActivity.messageList.get(q).getSender(), MainActivity.messageList.get(q).getReceiver(), MainActivity.messageList.get(q).getMes(), MainActivity.messageList.get(q).getHour(), MainActivity.messageList.get(q).getMin()));
            }
        }
        listSize=a.size();
        mesAdapter= new MessageAdapter(this, a);
        recyclerView.setAdapter(mesAdapter);
        layoutManager.scrollToPosition(a.size()-1);







    }

    private void bakiyeGonder() {
        int userwatt=user.getWatt();
        int receiverwatt=sender.getWatt();
        String x=miktar_text.getText().toString();
        int y=Integer.valueOf(x);
        if (y>userwatt){
            Toast.makeText(getApplicationContext(),"Bakiye Yetersiz...",Toast.LENGTH_SHORT).show();
        }else{
            user.setWatt(userwatt-y);
            sender.setWatt(receiverwatt+y);
            int a=user.getWatt();
            int b=sender.getWatt();
            System.out.println("Sender=a="+a+"&b="+b+"&u="+user.getName()+"&s="+sender.getName());
            String URL = ip+"/cdtp/send_watt.php?a="+a+"&b="+b+"&u="+user.getName()+"&s="+sender.getName();
            final RequestQueue queue;
            queue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()  {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(),"Miktar Gönderildi",Toast.LENGTH_SHORT).show();
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

    private void sendMessage() {
        final String s;
        s=send_text.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = ip+"/cdtp/send_message.php";
        Calendar rightNow = Calendar.getInstance();
        final int h = rightNow.get(Calendar.HOUR_OF_DAY);
        final int min=rightNow.get(Calendar.MINUTE);
        final String time=Integer.toString(h)+":"+Integer.toString(min)+":00";
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("s", sender.getName());
                params.put("r", user.getName());
                params.put("m",s);
                params.put("h",time);
                return params;
            }
        };
        queue.add(postRequest);
        send_text.setText("");

        MainActivity.messageList.add(new Message(user.getName(),sender.getName(),s,Integer.toString(h),Integer.toString(min)));
        final List<Message> a=new ArrayList<Message>();
        for(int q=0;q<MainActivity.messageList.size();q++){
            if(MainActivity.messageList.get(q).getSender().equals(sender.getName()) &&MainActivity.messageList.get(q).getReceiver().equals(user.getName())    ) {
                a.add(new Message(MainActivity.messageList.get(q).getSender(), MainActivity.messageList.get(q).getReceiver(), MainActivity.messageList.get(q).getMes(), MainActivity.messageList.get(q).getHour(), MainActivity.messageList.get(q).getMin()));
            }
            else if(MainActivity.messageList.get(q).getSender().equals(user.getName()) && MainActivity.messageList.get(q).getReceiver().equals(sender.getName())) {
                a.add(new Message(MainActivity.messageList.get(q).getSender(), MainActivity.messageList.get(q).getReceiver(), MainActivity.messageList.get(q).getMes(), MainActivity.messageList.get(q).getHour(), MainActivity.messageList.get(q).getMin()));
            }
        }

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
        mesAdapter= new MessageAdapter(this, a);
        recyclerView.swapAdapter(mesAdapter,false);
        if(listSize!=a.size()) {
            layoutManager.scrollToPosition(a.size() - 1);
            listSize=a.size();
        }


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
                    messageList.add(new Message(message_info[i*4+1],message_info[i*4+3],message_info[i*4+0],time[0],time[1]));

                }
                final List<Message> b=new ArrayList<Message>();
                for(int q=0;q<MainActivity.messageList.size();q++){
                    if(MainActivity.messageList.get(q).getSender().equals(sender.getName()) &&MainActivity.messageList.get(q).getReceiver().equals(user.getName())    ) {
                        b.add(new Message(MainActivity.messageList.get(q).getSender(), MainActivity.messageList.get(q).getReceiver(), MainActivity.messageList.get(q).getMes(), MainActivity.messageList.get(q).getHour(), MainActivity.messageList.get(q).getMin()));
                    }
                    else if(MainActivity.messageList.get(q).getSender().equals(user.getName()) && MainActivity.messageList.get(q).getReceiver().equals(sender.getName())) {
                        b.add(new Message(MainActivity.messageList.get(q).getSender(), MainActivity.messageList.get(q).getReceiver(), MainActivity.messageList.get(q).getMes(), MainActivity.messageList.get(q).getHour(), MainActivity.messageList.get(q).getMin()));
                    }
                }
                mesAdapter= new MessageAdapter(getApplicationContext(),b);
                recyclerView.swapAdapter(mesAdapter,false);
                if(listSize!=b.size()) {
                    layoutManager.scrollToPosition(b.size() - 1);
                    listSize=b.size();
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
    protected void onPostResume() {
        super.onPostResume();
        runnable.run();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mHandler.removeCallbacks(runnable);
        }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
        mHandler.removeCallbacks(runnable);
    }

    private Runnable runnable = new Runnable(){
        public void run(){
            messageList.clear();
            getMessages();
            mHandler.postDelayed(runnable,4000);
        }

    };

}
