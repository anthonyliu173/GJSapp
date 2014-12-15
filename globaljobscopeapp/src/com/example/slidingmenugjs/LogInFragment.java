package com.example.slidingmenugjs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 * This class is dedicated to log in / sign up.
 * Server side API has not been established at this moment.
 * */
public class LogInFragment extends Fragment {
	
	public LogInFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_log_in, container, false);
         
        return rootView;
    }
}
