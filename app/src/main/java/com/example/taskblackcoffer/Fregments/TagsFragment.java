package com.example.taskblackcoffer.Fregments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.taskblackcoffer.DashBoardActivity;
import com.example.taskblackcoffer.LoginActivity;
import com.example.taskblackcoffer.R;
import com.preference.PowerPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagsFragment extends Fragment {
    Button logout;
    View view;
    public TagsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_tags, container, false);
        logout=view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PowerPreference.clearAllData();

                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;

    }
}
