package com.example.bkrc.cdtp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.Nullable;
import static com.example.bkrc.cdtp.SignActivity.ip;
import static com.example.bkrc.cdtp.SignActivity.user;

public class PaymentActivity extends Activity {

    TextView payText,payText1,payText2,payAy,payYıl;
    Button payButton;
    EditText cardName, cardNumber,cardNumber2;
    SeekBar seekBar;
    Spinner spinner,spinner2;
    String[] user_info=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
        payText= (TextView)findViewById(R.id.payText);
        payText1=(TextView)findViewById(R.id.payText1);
        payButton = (Button)findViewById(R.id.payButton);
        cardName = (EditText)findViewById(R.id.cardName);
        cardNumber=(EditText)findViewById(R.id.cardNumber);
        cardNumber2=(EditText)findViewById(R.id.cardNumber2);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        payText2=(TextView)findViewById(R.id.payText2);
        payAy=(TextView)findViewById(R.id.textAy);
        payYıl=(TextView)findViewById(R.id.textYıl);

        ArrayList<String> years = new ArrayList<String>();
        for (int i = 19; i <= 27; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        spinner2.setAdapter(adapter);

        ArrayList<String> months = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            months.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        spinner.setAdapter(adapter2);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                payText1.setText("" + progress);
                payText1.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                payText2.setText("" + progress*0.31 + " tl");
                payText2.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                //textView.setY(100); just added a value set this properly using screen with height aspect ratio , if you do not set it by default it will be there below seek bar

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = cardName.getText().toString();
                String cardnumber = cardNumber.getText().toString();
                String cardnumber2 = cardNumber2.getText().toString();
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(getApplicationContext(), "Kartın Üzerindeki Bilgileri \n Eksiksiz Giriniz.", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(cardnumber)){
                    Toast.makeText(getApplicationContext(), "Kart Numarasını Giriniz.", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(cardnumber2)){
                    Toast.makeText(getApplicationContext(), "CSV Kodunu Giriniz", Toast.LENGTH_SHORT).show();
                }
                else if(cardnumber2.length()!=3){
                    Toast.makeText(getApplicationContext(), "CSV Kodunu Eksik \n Girdiniz Tekrar Giriniz.", Toast.LENGTH_SHORT).show();

                }else if(cardnumber.length()!=16){
                    Toast.makeText(getApplicationContext(), "Kart Numarasını Eksik \n Girdiniz Tekrar Giriniz.", Toast.LENGTH_SHORT).show();

                }else {
                confirmPayment();}
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(this,MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

    private void confirmPayment() {

        final int deger=Integer.valueOf(payText1.getText().toString())*1000;
        String userName=user.getName();
        final String get_data = ip+"/cdtp/get_data.php?u="+userName;
        RequestQueue queue;
        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, get_data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                user_info=response.split(" ");
                int x=Integer.valueOf(user_info[0]);
                x=x+deger;
                user.setWatt(x);
                addPaymentToSQL();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Ödeme ile ilgili bir sorun oluştu...",Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);


    }

    private void addPaymentToSQL() {
        RequestQueue Reqqueue;
        Reqqueue = Volley.newRequestQueue(this);
        String insert_data = ip+"/cdtp/insert_data.php";
        StringRequest request = new StringRequest(Request.Method.POST, insert_data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Yükleme İşlemi Tamamlandı",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("u",user.getName());
                parameters.put("p",user.getPassword());
                parameters.put("w",Integer.toString(user.getWatt()));

                return parameters;
            }
        };
        Reqqueue.add(request);
        SendUserToSecondActivity(user);
    }

    private void SendUserToSecondActivity(Users user) {
        Intent secondIntent = new Intent(PaymentActivity.this,MainActivity.class);
        secondIntent.putExtra("userInfo",user);
        startActivity(secondIntent);
        finish();
    }

}






