package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;


public class ChatRoomActivity1 extends AppCompatActivity {

    public static final String _ID = "_Id";
    public static final String ISSEND = "issend";
    public static final String MESSAGE = "Message";
    public String ACTIVITY_NAME = "ProfileActivity";
    Button send;
    Button receive;
    EditText chattext;
    TextView sendtext;
    TextView receivetext;
    Messages message;
    ArrayList<Messages> messageList = new ArrayList<Messages>();
    SQLiteDatabase db;
    Cursor results;
    MyOpener opener = new MyOpener(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        boolean isTablet = (findViewById(R.id.framelayout) != null);

        send = findViewById(R.id.send);
        receive = findViewById(R.id.receive);
        chattext = findViewById(R.id.chattext);
        sendtext = findViewById(R.id.sendtext);
        receivetext = findViewById(R.id.receivetext);

        //Set Adapter
        ListView listview = findViewById(R.id.listview);
        ChatAdapter adapter = new ChatAdapter();
        listview.setAdapter(adapter);

        MyOpener dbOpener = new MyOpener(this);
        db = new MyOpener(this).getWritableDatabase();

        String[] columns = {MyOpener.COL_ID, MyOpener.COL_TYPE, MyOpener.COL_MESSAGE};
        //query all the results from the database:
        results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //find the column indices:
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);
        int messageColIndex = results.getColumnIndex(dbOpener.COL_MESSAGE);
        int typeColIndex = results.getColumnIndex(dbOpener.COL_TYPE);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String type = results.getString(typeColIndex);
            String message = results.getString(messageColIndex);
            long id = results.getLong(idColIndex);

            //add the new newMessage to the list:
            if (type.equals("false")) {
                messageList.add(new Messages(message, false, id));
            } else if (type.equals("true")) {
                messageList.add(new Messages(message, true, id));
            }

        }

        send.setOnClickListener(click -> {

            //add to the database and get the new ID
            ContentValues newValues = new ContentValues();
            //put string message in the MESSAGE column:
            newValues.put(MyOpener.COL_MESSAGE, chattext.getText().toString());
            //put boolean type in the TYPE column:
            newValues.put(MyOpener.COL_TYPE, "true");
            //insert in the database:
            long newId = db.insert(MyOpener.TABLE_NAME, null, newValues);
            //add the new contact to the list:
            message = new Messages(chattext.getText().toString(), true, newId);

            //add the new newMessage to the list:
            messageList.add(message);

            adapter.notifyDataSetChanged();
            chattext.setText("");

        });

        receive.setOnClickListener(click -> {
            //add to the database and get the new ID
            ContentValues newValues = new ContentValues();
            //put string message in the MESSAGE column:
            newValues.put(MyOpener.COL_MESSAGE, chattext.getText().toString());
            //put boolean type in the TYPE column:
            newValues.put(MyOpener.COL_TYPE, "false");
            //insert in the database:
            long newId = db.insert(MyOpener.TABLE_NAME, null, newValues);

            //add the new contact to the list:
            message = new Messages(chattext.getText().toString(), false, newId);
            //add the new newMessage to the list:
            messageList.add(message);

            adapter.notifyDataSetChanged();
            chattext.setText("");

        });
        listview.setAdapter(adapter);


        listview.setOnItemClickListener((list, item, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putString(MESSAGE, messageList.get(position).getMessage());
            bundle.putBoolean(ISSEND, messageList.get(position).isSent());
            bundle.putLong(_ID, messageList.get(position).getId());

            if (isTablet) {
                DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
                dFragment.setArguments(bundle); //pass it a bundle for information
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, dFragment).commit(); //Add the fragment in FrameLayout.commit(); //actually load the fragment.
                Log.d("Row", "Type:");
            } else //isPhone
            {
                Intent nextActivity = new Intent(ChatRoomActivity1.this, EmptyActivity.class);
                nextActivity.putExtras(bundle); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
        });

            listview.setOnItemLongClickListener((list, item, position, id) -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity1.this);
                builder.setTitle("Do you want to delete this?")

                        .setMessage("The selected row is: " + position + "The database id is: " + id)
                        .setPositiveButton("Yes", (click, arg) -> {
                            delete(messageList.get(position));
                            messageList.remove(position);
                            Log.d("Row", "Type: ");
                            adapter.notifyDataSetChanged();

                        })
                        .setNegativeButton("No", (click, arg) -> {
                        })
                        .create().show();
                Toast.makeText(ChatRoomActivity1.this, "Message Deleted", Toast.LENGTH_LONG).show();

                return true;
            });

            printCursor(results, db.getVersion());

    }

    public void printCursor(Cursor c, int version){
        Log.d("PrintCursor", "Version No.: " + MyOpener.VERSION_NUM);
        Log.d("PrintCursor", "Number of Columns: " + results.getColumnCount());
        Log.d("PrintCursor", "Column Names: "+ Arrays.toString(results.getColumnNames()));
        Log.d("PrintCursor", "Number of Results: " + results.getCount());

        int typeColIndex = c.getColumnIndex(MyOpener.COL_TYPE);
        int messageColIndex = c.getColumnIndex(MyOpener.COL_MESSAGE);
        int idColIndex = c.getColumnIndex(MyOpener.COL_ID);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            int type = c.getInt(typeColIndex);
            String message = c.getString(messageColIndex);
            long id = c.getLong(idColIndex);
            Log.d("ROW", "type: " + type + ", message: " + message + ", id:" + id);
            c.moveToNext();
        }

    }
    protected class ChatAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public Messages getItem(int position) {
            return messageList.get(position);
        }

        @Override
        public long getItemId(int position) {
            int position1 = position;
            return position1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View view;
            TextView row;

            message = getItem(position);
            if (message.isSent()) {

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
    }
    public void delete(Messages message) {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[]{Long.toString(message.getId())});
    }
}