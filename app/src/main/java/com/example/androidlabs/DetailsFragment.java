package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private AppCompatActivity parentActivity;
    private Bundle dataFromActivity;
    public static final String _ID = "_Id";
    public static final String ISSEND = "issend";
    public static final String MESSAGE = "Message";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();

        // Inflating the fragment_details view but storing result as a variable
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        String message = dataFromActivity.getString(MESSAGE);
        int type = dataFromActivity.getInt(ISSEND);
        long id = dataFromActivity.getLong(_ID);

        TextView messageHere = view.findViewById(R.id.messagehere);
        messageHere.setText(message);
        TextView messageID = view.findViewById(R.id.id);
        messageID.setText(String.valueOf(id));

        Button hideButton = view.findViewById(R.id.hideButton);
        hideButton.setOnClickListener(click -> {

            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        return view;
    }

   @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity) context;
    }
}