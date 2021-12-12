package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;
import java.util.ArrayList;


public class ChatRoomActivity extends AppCompatActivity {

    public String ACTIVITY_NAME = "ProfileActivity";
    Button send;
    Button receive;
    TextView sendtext;
    TextView receivetext;
    EditText chattext;
    ArrayList<Messages> messageList = new ArrayList<>();
    Messages message;
    SQLiteDatabase db;
    Cursor cursor;
    MyOpener opener = new MyOpener(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //listen for items being clicked in the ListView
        send = findViewById(R.id.send);
        receive = findViewById(R.id.receive);
        chattext = findViewById(R.id.chattext);
        sendtext = findViewById(R.id.sendtext);
        receivetext = findViewById(R.id.receivetext);

        ListView list = findViewById(R.id.listview);
        ChatAdapter adapter = new ChatAdapter();
        list.setAdapter(adapter);

        MyOpener dbOpener = new MyOpener(this);
        db = new MyOpener(this).getWritableDatabase();
        String [] Columns = {MyOpener.COL_ID, MyOpener.COL_TYPE, MyOpener.COL_MESSAGE};
        cursor = db.query(false, MyOpener.TABLE_NAME, Columns, null, null, null, null, null, null);

        //find the column indices:
        int textIndex = cursor.getColumnIndex(dbOpener.COL_TYPE);
        int messageIndex = cursor.getColumnIndex(dbOpener.COL_MESSAGE);
        int idIndex = cursor.getColumnIndex(dbOpener.COL_ID);

        //iterate over the results, return true if there is a next item:

        while (cursor.moveToNext()){
                //!cursor.isAfterLast()) {
            String text = cursor.getString(textIndex);
            String message = cursor.getString(messageIndex);
            long id = cursor.getLong(idIndex);

            //add the new message to the array list:
            if (text.equals("false")) {
                messageList.add(new Messages(message, false, id));
            } else if (text.equals("true")) {
                messageList.add(new Messages(message, true, id));
            }
        }

        send.setOnClickListener(click -> {
            chattext.getText().toString();

            ContentValues newValues = new ContentValues();
            newValues.put(MyOpener.COL_TYPE, "TRUE");
            newValues.put(MyOpener.COL_MESSAGE, chattext.getText().toString());

            //insert into db and get id value
            long newId = db.insert(MyOpener.TABLE_NAME, null, newValues);
            messageList.add(new Messages(chattext.getText().toString(), true, newId));
            adapter.notifyDataSetChanged();
            chattext.setText("");
        });

        receive.setOnClickListener(click -> {
            chattext.getText().toString();

            ContentValues newValues = new ContentValues();
            newValues.put(MyOpener.COL_TYPE, "FALSE");
            newValues.put(MyOpener.COL_MESSAGE, chattext.getText().toString());

            //insert into db and get id value
            long newId = db.insert(MyOpener.TABLE_NAME, null, newValues);
            messageList.add(new Messages(chattext.getText().toString(), false, newId));
            adapter.notifyDataSetChanged();
            chattext.setText("");
        });

        list.setOnItemLongClickListener((p, b, pos, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);

            builder.setTitle("Do you want to delete this?");
            builder.setMessage("The selected row is: " + pos + " The database id is: " + id)
                    .setPositiveButton("YES", (click, arg) -> {
                        MyOpener opener = null;
                        messageList.remove(pos);
                        Log.e(ACTIVITY_NAME, "Message Deleted: " + pos + "Database ID: " + id);
                        adapter.notifyDataSetChanged();
                        //messageList.remove(db);
                        Toast.makeText(ChatRoomActivity.this, "Message Deleted", Toast.LENGTH_LONG).show();
                    })
                    .setNegativeButton("NO", (click, arg) -> {
                    })
                    .create().show();
            return true;
        });
        printCursor(cursor, db.getVersion());
    }
    private void printCursor(Cursor c, int version) {
        Log.d("PrintCursor", "Version No.: " + MyOpener.VERSION_NUM);
        Log.d("PrintCursor", "Number of Columns: " + c.getColumnCount());
        Log.d("PrintCursor", "Names of Columns: " + c.getColumnNames());
        Log.d("PrintCursor", "Number of Results: " + c.getCount());

        int textIndex = c.getColumnIndex(MyOpener.COL_TYPE);
        int messageIndex = c.getColumnIndex(MyOpener.COL_MESSAGE);
        int idIndex = c.getColumnIndex(MyOpener.COL_ID);

        while (c.moveToFirst()) {
            String text = c.getString(textIndex);
            String message = c.getString(messageIndex);
            long id = c.getLong(idIndex);
            Log.d("ROW", "text: " + text + ", message: " + message + ", id:" + id);
            c.moveToNext();
        }
    }
        protected class ChatAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return messageList.size();
            }

            @Override
            public Object getItem(int position) {
                return messageList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return messageList.get(position).getId();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                View view;
                TextView row;
                message = (Messages) getItem(position);

                if (message.isSent) {
                    view = inflater.inflate(R.layout.activity_send, parent, false);
                    row = view.findViewById(R.id.sendtext);
                    String show = message.getMessage();
                    row.setText(show);
                } else {
                    view = inflater.inflate(R.layout.activity_receieve, parent, false);
                    row = view.findViewById(R.id.receivetext);
                    String show = message.getMessage();
                    row.setText(show);
                }
                return view;
            }
            public void deleteFromDatabase(SQLiteDatabase db){
                db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "=?", new String[]{Long.toString(message.getId())});
            }
        }
}

