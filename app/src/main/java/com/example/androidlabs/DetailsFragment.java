package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private AppCompatActivity parentActivity;
    private Bundle dataFromActivity;
    private TextView messageHere, ID;
    private CheckBox isSend;
    private Button hideButton;
    private float long_id;
    private String string_message;
    private boolean boolean_isSend = false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflating the fragment_details view but storing result as a variable
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Getting data from Bundle created in ChatRoomActivity
        dataFromActivity = getArguments();
        long_id = dataFromActivity.getLong(ChatRoomActivity1.ITEM_ID);
        string_message = dataFromActivity.getString(ChatRoomActivity1.ITEM_SELECTED);
        boolean_isSend = dataFromActivity.getBoolean(ChatRoomActivity1.ITEM_POSITION);

        // variables for widgets from fragment_details view
        messageHere = view.findViewById(R.id.messagehere);
        ID = view.findViewById(R.id.id);
        isSend = view.findViewById(R.id.issend);
        hideButton = view.findViewById(R.id.hideButton);

        //setting new values for view
        messageHere.setText(string_message);
        ID.setText(String.valueOf(long_id));
        isSend.setChecked(boolean_isSend);

        hideButton.setOnClickListener( click -> {
            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        return view;
    }

    @Override //need this method for hideBtn to work
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity) context;
    }
}