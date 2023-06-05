package com.thanh.blindhelper;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FIREBASE", "Fetching FCM registration token failed");
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    // Log and toast
                    Log.d("FIREBASE", token);
                    editText.setText(token);
                });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest request = new StringRequest(
//                Request.Method.GET,
//                "http://192.168.1.11:5000/",
//                response -> {
//                    Toast.makeText(this, "Connect to localhost successfully", Toast.LENGTH_SHORT).show();
//                }, error -> Toast.makeText(this, "Connect failed", Toast.LENGTH_SHORT).show()
//        );
//        requestQueue.add(request);
    }
}