package com.example.slidingmenugjs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * This class is the main page of the app.
 * Type in desired job title / keyword in search box(edtEnter)
 * Press enter(btnSearch)
 * SearchResult activity will be brought up.
 * */
public class HomeFragment extends Fragment {
	
	public HomeFragment(){}
	EditText edtEnter;
	Button btnSearch;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        edtEnter = (EditText)rootView.findViewById(R.id.edtEnter);
        btnSearch = (Button)rootView.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new OnClickListener(){
        	
        	public void onClick(View view){
        		
        		String Keyword = "";
        		//Check if user enters keyword
        		if(!edtEnter.getText().toString().matches("")){
        			Toast.makeText(getActivity(), edtEnter.getText().toString(), Toast.LENGTH_SHORT).show();
        			Keyword = edtEnter.getText().toString();
        		}else{
        			Toast.makeText(getActivity(), "search all jobs", Toast.LENGTH_SHORT).show();
        			Keyword = "search all jobs";
        		}
        		
        		//call search result activity when user presses "search" button
        		Intent SearchResultIntent = new Intent(getActivity(),
        				SearchResult.class);
        		Bundle bd = new Bundle();
        		//bundle info
        		bd.putString("keyword_searched", Keyword);
        		SearchResultIntent.putExtras(bd);
        		startActivity(SearchResultIntent);
        	}
        });
        
        return rootView;
    }


}
