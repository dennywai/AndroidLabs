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
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private AppCompatActivity parentActivity;
    private Bundle dataFromActivity;
    public static final String MESSEGE = "MESSEGE";
    public static final String _ID = "_ID";
    public static final String ISSEND = "ISSEND";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();

        // Inflating the fragment_details view but storing result as a variable
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        Bundle bundle =getArguments();
        ((TextView) view.findViewById(R.id.messagehere)).setText(bundle.getString(ChatRoomActivity1.MESSEGE));
        ((TextView) view.findViewById(R.id.id)).setText(bundle.getString(ChatRoomActivity1._ID));
        ((CheckBox) view.findViewById(R.id.issend)).setText(bundle.getString(ChatRoomActivity1.ISSEND));

        Button hideButton = view.findViewById(R.id.hideButton);
        hideButton.setOnClickListener(click -> {
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