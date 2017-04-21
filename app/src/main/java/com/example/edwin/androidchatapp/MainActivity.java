package com.example.edwin.androidchatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Socket socket;
    {
        try{
            socket = IO.socket("http://192.168.1.172:3000");
        }catch(URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    private EditText mInputMessageView;
    private ArrayList<String> mMessages = new ArrayList<String>();
    private ListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Messenger 2.0");
        socket.connect();

        ImageButton sendBtn = (ImageButton)findViewById(R.id.imageButton2);
        mInputMessageView = (EditText) findViewById(R.id.editText);

         adapter = new ListAdapter(MainActivity.this, mMessages);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });




       socket.on("message", handleIncomingMessages);
    }

    private Emitter.Listener handleIncomingMessages = new Emitter.Listener(){
        @Override
        public void call(final Object... args){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    //String imageText;
                    try {
                        message = data.getString("text");
                        addMessage(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    /*
                    try {
                        imageText = data.getString("image");
                        addImage(decodeImage(imageText));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    */

                }
            });
        }
    };


    private void sendMessage(){
        String message = mInputMessageView.getText().toString().trim();
        mInputMessageView.setText("");
        addMessage(message);
        JSONObject sendText = new JSONObject();
        try{
            sendText.put("text",message);
            socket.emit("message", sendText);
        }catch(JSONException e){

        }

    }

    private void addMessage(String message) {

        mMessages.add(message);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
