package com.refresh.pos.ui.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.refresh.pos.R;
import com.refresh.pos.techicalservices.inventory.InventoryDaoAndroid;
import com.refresh.pos.ui.inventory.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText serialText;
    private Button serialBtn;

    /**
     * Id to identity READ_CONTACTS permission request.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);


        serialText =(EditText) findViewById(R.id.serial_edit_text);
        serialBtn =(Button) findViewById(R.id.send_serial_btn);

        btnSerialClick();




    }

    private void btnSerialClick(){


        serialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        SendSerialToServer ();



            }
        });
    }

    private void SendSerialToServer (){
        String serialValue= serialText.getText().toString();
        String urlToken ="http://139.180.142.52:3000/api/register?serial=";
//        String serial ="tyriv0t6v8de5qweqnyv";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlToken+serialValue, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String Response = response.getString("message");
                            String data = response.getString("data");
//                            JSONArray jsonArray = response.getJSONArray("serial");
                            if (Response.equals("success")){


                                Toast.makeText(getApplicationContext(), data,
                                        Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(getApplicationContext()).addtoRequestQue(request);


    }
}

