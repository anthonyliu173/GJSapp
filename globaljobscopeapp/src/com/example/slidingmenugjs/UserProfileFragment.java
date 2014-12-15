package com.example.slidingmenugjs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 * This class is dedicated to User Profile management.
 * Server side API has not been established at this moment.
 * */
public class UserProfileFragment extends Fragment {
	
	public UserProfileFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
         
        return rootView;
    }
}
