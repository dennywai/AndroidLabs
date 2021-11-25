package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


public class ChatRoomActivity extends AppCompatActivity {


    private static final String ACTIVITY_NAME = "ChatRoomActivity";
    Button send;
    Button receive;
    EditText chattext;
    TextView sendtext;
    TextView receivetext;

    ChatAdapter adapter;
    ArrayList<Messages> messages = new ArrayList<>();
    Messages message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        adapter = new ChatAdapter();
        ListView list = findViewById(R.id.listview);

        send = findViewById(R.id.send);
        receive = findViewById(R.id.receive);
        chattext = findViewById(R.id.chattext);
        sendtext = findViewById(R.id.sendtext);
        receivetext = findViewById(R.id.receivetext);

        send.setOnClickListener(click -> {
            message = new Messages(chattext.getText().toString(),1);
            messages.add(message);
            adapter.notifyDataSetChanged();
            chattext.setText("");
            });

        receive.setOnClickListener(click -> {
            message = new Messages(chattext.getText().toString(),2);
            Log.e(ACTIVITY_NAME, message.content);
            messages.add(message);
            adapter.notifyDataSetChanged();
            chattext.setText("");
        });
        list.setAdapter(adapter);
    }

        protected class ChatAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return messages.size();
            }

            @Override
            public Object getItem(int position) {
                return messages.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                View view = null;
                TextView row;

                message = (Messages) getItem(position);

                if(message.texts == Messages.senttext) {
                    view = inflater.inflate(R.layout.activity_send, parent, false);
                    row = view.findViewById(R.id.sendtext);
                    String show = getItem(position).toString();
                    row.setText(show);
                }
                else if(message.texts == Messages.receivedtext){
                    view = inflater.inflate(R.layout.activity_receieve, parent, false);
                    row = view.findViewById(R.id.receivetext);
                    String show = getItem(position).toString();
                    row.setText(show);
                }
                    return view;
                }
            }
        }

