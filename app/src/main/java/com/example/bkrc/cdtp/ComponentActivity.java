package com.example.bkrc.cdtp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.bkrc.cdtp.MainActivity.components;
import static com.example.bkrc.cdtp.MainActivity.messageList;
import static com.example.bkrc.cdtp.SignActivity.ip;
import static com.example.bkrc.cdtp.SignActivity.user;
import static java.lang.Math.pow;

public class ComponentActivity extends Activity {

    Button b1,b2,b3,b4,b5,b6;
    Users user;
    int b1color,b2color,b3color,b4color,b5color,b6color;
    String timeString=null;
    int timeInt;
    private Handler mHandler = new Handler();

    public int getTimeInt() {
        return timeInt;
    }

    public void setTimeInt(int time) {
        this.timeInt = time;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_activity);

        b1=(Button)findViewById(R.id.buzdolabi);
        b2=(Button)findViewById(R.id.firin);
        b3=(Button)findViewById(R.id.camasir);
        b4=(Button)findViewById(R.id.bulasik);
        b5=(Button)findViewById(R.id.televizyon);
        b6=(Button)findViewById(R.id.pc);
        b1.setBackgroundResource(R.drawable.button_components_off);
        b2.setBackgroundResource(R.drawable.button_components_off);
        b3.setBackgroundResource(R.drawable.button_components_off);
        b4.setBackgroundResource(R.drawable.button_components_off);
        b5.setBackgroundResource(R.drawable.button_components_off);
        b6.setBackgroundResource(R.drawable.button_components_off);
        final Context c=this;
        b6color=Integer.valueOf(components[5]);
        b5color=Integer.valueOf(components[4]);
        b4color=Integer.valueOf(components[3]);
        b3color=Integer.valueOf(components[2]);
        b2color=Integer.valueOf(components[1]);
        b1color=Integer.valueOf(components[0]);

        getTime();
        if (b1color==1){
            b1.setBackgroundResource(R.drawable.button_components_on);
        }
        if (b2color==1){
            b2.setBackgroundResource(R.drawable.button_components_on);
        }
        if (b3color==1){
            b3.setBackgroundResource(R.drawable.button_components_on);
        }
        if (b4color==1){
            b4.setBackgroundResource(R.drawable.button_components_on);
        }
        if (b5color==1){
            b5.setBackgroundResource(R.drawable.button_components_on);
        }
        if (b6color==1){
            b6.setBackgroundResource(R.drawable.button_components_on);
        }
        /*AlertDialog alertDialog = new AlertDialog.Builder(ComponentActivity.this).create();
                    alertDialog.setTitle("Dikkat!!!");
                    alertDialog.setMessage("Buzdolabı Her Zaman Çalışan Bir Cihazdır\n Kapatmak İstediğinizen Emin Misiniz?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Hayır",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    alertDialog.show();*/
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
                if(b1color==1){
                    AlertDialog alertDialog = new AlertDialog.Builder(ComponentActivity.this).create();
                    alertDialog.setTitle("Dikkat!!!");
                    alertDialog.setMessage("Buzdolabı Her Zaman Çalışan Bir Cihazdır\n Kapatmak İstediğinizden Emin Misiniz?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Hayır",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            components[0]="2";
                            b1.setBackgroundResource(R.drawable.button_components_off);
                            b1color=2;
                        }
                    });
                    alertDialog.show();

                }else{
                    components[0]="1";
                    b1.setBackgroundResource(R.drawable.button_components_on);
                    b1color=1;
                }
                updateComponents();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
                if(b2color==1){
                    components[1]="2";
                    b2.setBackgroundResource(R.drawable.button_components_off);
                    b2color=2;
                }else{

                    if(timeInt<18 && timeInt>7) {
                        AlertDialog alertDialog = new AlertDialog.Builder(ComponentActivity.this).create();
                        alertDialog.setTitle("Enerji Tüketimi Önerisi");
                        alertDialog.setMessage("Batarya Elektriği Kullanım Saatinde Fırın Çalıştırarak Enerjinizden %10 Tasarruf Etmek İstemez Misiniz? ");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Kullanmaya Devam Et",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        components[1] = "1";
                                        b2.setBackgroundResource(R.drawable.button_components_on);
                                        b2color = 1;
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Tasarruf Et", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                        alertDialog.show();
                    }else {
                        components[1] = "1";
                        b2.setBackgroundResource(R.drawable.button_components_on);
                        b2color = 1;
                    }

                }
                updateComponents();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b3color==1){
                    components[2]="2";
                    b3.setBackgroundResource(R.drawable.button_components_off);
                    b3color=2;
                }else{
                    if(timeInt<18 && timeInt>7) {
                        AlertDialog alertDialog = new AlertDialog.Builder(ComponentActivity.this).create();
                        alertDialog.setTitle("Enerji Tüketimi Önerisi");
                        alertDialog.setMessage("Batarya Elektriği Kullanım Saatinde Çamaşır Makinesini Çalıştırarak Enerjinizden %10 Tasarruf Etmek İstemez Misiniz? ");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Kullanmaya Devam Et",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        components[2]="1";
                                        b3.setBackgroundResource(R.drawable.button_components_on);
                                        b3color=1;
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Tasarruf Et", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                        alertDialog.show();
                    }else {
                        components[2]="1";
                        b3.setBackgroundResource(R.drawable.button_components_on);
                        b3color=1;
                    }





                }
                updateComponents();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b4color==1){
                    components[3]="2";
                    b4.setBackgroundResource(R.drawable.button_components_off);
                    b4color=2;
                }else{
                    if(timeInt<18 && timeInt>7) {
                        AlertDialog alertDialog = new AlertDialog.Builder(ComponentActivity.this).create();
                        alertDialog.setTitle("Enerji Tüketimi Önerisi");
                        alertDialog.setMessage("Batarya Elektriği Kullanım Saatinde Bulaşık Makinesini Çalıştırarak Enerjinizden %10 Tasarruf Etmek İstemez Misiniz? ");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Kullanmaya Devam Et",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        components[3]="1";
                                        b4.setBackgroundResource(R.drawable.button_components_on);
                                        b4color=1;
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Tasarruf Et", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                        alertDialog.show();
                    }else {
                        components[3]="1";
                        b4.setBackgroundResource(R.drawable.button_components_on);
                        b4color=1;
                    }

                }
                updateComponents();
            }


        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b5color==1){
                    components[4]="2";
                    b5.setBackgroundResource(R.drawable.button_components_off);
                    b5color=2;
                }else{
                    components[4]="1";
                    b5.setBackgroundResource(R.drawable.button_components_on);
                    b5color=1;
                }
                updateComponents();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b6color==1){
                    AlertDialog alertDialog = new AlertDialog.Builder(ComponentActivity.this).create();
                    alertDialog.setTitle("Dikkat!!!");
                    alertDialog.setMessage("Mühendisin Bilgisayarı Her Zaman Çalışmalıdır\n Kapatmak İstediğinizden Emin Misiniz?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Hayır",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            components[5]="2";
                            b6.setBackgroundResource(R.drawable.button_components_off);
                            b6color=2;
                            dialog.dismiss();

                        }
                    });
                    alertDialog.show();

                }else{
                    components[5]="1";
                    b6.setBackgroundResource(R.drawable.button_components_on);
                    b6color=1;
                }
                updateComponents();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mHandler.removeCallbacks(runnable);
        Intent mainIntent = new Intent(this,MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        runnable.run();
    }

    private void updateComponents() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = ip+"/cdtp/update_comps.php";
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
                params.put("b", components[0]);
                params.put("f", components[1]);
                params.put("c",components[2]);
                params.put("m",components[3]);
                params.put("t",components[4]);
                params.put("p",components[5]);
                return params;
            }
        };
        queue.add(postRequest);

    }

    private void getTime(){
        String URL = ip+"/cdtp/get_time2";
        final RequestQueue queue;
        queue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                timeString=response;
                System.out.println(timeString);
                setTimeInt(Integer.valueOf(timeString));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Database Connection",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    private Runnable runnable = new Runnable(){
        public void run(){
            getTime();
            updateComponents();
            mHandler.postDelayed(runnable,2000);
        }

    };

}
