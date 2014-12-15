package com.example.slidingmenugjs;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.json.JSONParser;
import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This class displays search results
 * */
public class SearchResult extends ListActivity{
	
	private String Keyword_Searched;
	private ProgressDialog pDialog;
	
	JSONParser jsonParser = new JSONParser();
	ArrayList<HashMap<String, String>> dataLists ;
	JSONArray data = null;
	
	Button imgbtnSearch;
	EditText edtSearch;
	
	private static final String URL_DATA = "http://globaljobscope.com/mobileappdata/fakedata.php";
	
	// ALL JSON node names
	private static final String TAG_ID = "id";
	private static final String TAG_TITLE = "title";
	private static final String TAG_LOCATION = "location";
	private static final String TAG_SALARY = "salary";
	private static final String TAG_COMPANY = "company";
	private static final String TAG_DETAIL = "detail";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);
		
		// set action bar style
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F26E1A")));
		
		//unpack bundled info
		Bundle bd = this.getIntent().getExtras();
		Keyword_Searched = bd.getString("keyword_searched");
				
		//register Search Button & EditText
		imgbtnSearch = (Button)findViewById(R.id.imgbtnSearch);
		edtSearch = (EditText)findViewById(R.id.edtSearch);
		edtSearch.setText(Keyword_Searched);
		imgbtnSearch.setOnClickListener(Search);
				
		dataLists = new ArrayList<HashMap<String, String>>();

	    // Loading Albums JSON in Background Thread
		new LoadDatas().execute();
		

	}
	
	//
	private void setListView(){
		//register ListView Click
		ListView lv = getListView();

		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {

				TextView a = (TextView) view.findViewById(R.id.data_ID);
			    String data_id = a.getText().toString();
			    TextView b = (TextView) view.findViewById(R.id.data_company);
			    String data_company = b.getText().toString();
			    TextView c = (TextView) view.findViewById(R.id.data_title);
			    String data_position = c.getText().toString();
			    TextView d = (TextView) view.findViewById(R.id.data_salary);
			    String data_salary = d.getText().toString();
			    TextView e = (TextView) view.findViewById(R.id.data_detail);
			    String data_detail = e.getText().toString();
			    TextView f = (TextView) view.findViewById(R.id.data_location);
			    String data_location = f.getText().toString();
			    
				Intent IndividualJob = new Intent(SearchResult.this, IndividualJobs.class);
				
				Bundle bd = new Bundle();

				bd.putString("id", data_id);
				bd.putString("company", data_company);
				bd.putString("position", data_position);
				bd.putString("salary", data_salary);
				bd.putString("detail", data_detail);
				bd.putString("location", data_location);
				bd.putString("status", "searchresult");
				IndividualJob.putExtras(bd);
				
				startActivity(IndividualJob);
			}
		});		
		
	}
	
	private OnClickListener Search = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			//reset keyword_searched
			Keyword_Searched =  edtSearch.getText().toString();
			Toast.makeText(SearchResult.this, Keyword_Searched , Toast.LENGTH_SHORT).show();
			new LoadDatas().execute();
		}
	};
	
 	/**
 	 * Background Async Task to Load all Albums by making http request
 	 * */
 	class LoadDatas extends AsyncTask<String, String, String> {

 		/**
 		 * Before starting background thread Show Progress Dialog
 		 * */
 		@Override
 		protected void onPreExecute() {
 			super.onPreExecute();
 			pDialog = new ProgressDialog(SearchResult.this);
			pDialog.setMessage("loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
 		}

 		/**
 		 * getting Albums JSON
 		 * */
 		protected String doInBackground(String... args) {
 			// Building Parameters
 			List<NameValuePair> params = new ArrayList<NameValuePair>();

 			// getting JSON string from URL
 			String json = jsonParser.makeHttpRequest(URL_DATA, "GET",
 					params);

 			// Check your log cat for JSON reponse
 			Log.d("Albums JSON: ", "> " + json);

 			try {				
 				data = new JSONArray(json);
 				if (data != null) {
 					dataLists.clear();
 					// looping through All albums
 					for (int i = 0; i < data.length(); i++) {
 						JSONObject c = data.getJSONObject(i);
 						// Storing each json item values in variable
 						String id = c.getString(TAG_ID);
 						String title = c.getString(TAG_TITLE);
 						String location = c.getString(TAG_LOCATION);
 						String salary = c.getString(TAG_SALARY)+" usd/year";
 						String company = c.getString(TAG_COMPANY);
 						String detail = c.getString(TAG_DETAIL);

 						// creating new HashMap
 						HashMap<String, String> map = new HashMap<String, String>();

 						// adding each child node to HashMap key => value
 						map.put(TAG_ID, id);
 						map.put(TAG_TITLE, title);
 						map.put(TAG_LOCATION, location);
 						map.put(TAG_SALARY, salary);
 						map.put(TAG_COMPANY, company);
 						map.put(TAG_DETAIL, detail);

 						// adding HashList to ArrayList
 						dataLists.add(map);
 					}
 				}else{
 					Log.d("Datas: ", "null");
 				}

 			} catch (JSONException e) {
 				e.printStackTrace();
 			}

 			return null;
 		}

 		/**
 		 * After completing background task Dismiss the progress dialog
 		 * **/
 		protected void onPostExecute(String file_url) {
 			pDialog.dismiss();
 		// updating UI from Background Thread
 			runOnUiThread(new Runnable() {
 				public void run() {
 					ListAdapter adapter = new SimpleAdapter(
						SearchResult.this, dataLists,
						R.layout.list_item_data, new String[] {TAG_ID,
								TAG_TITLE, TAG_SALARY, TAG_COMPANY, TAG_LOCATION, TAG_DETAIL }, new int[] {
								 R.id.data_ID, R.id.data_title, R.id.data_salary, R.id.data_company, R.id.data_location
								 , R.id.data_detail});
				
					// updating listview
					setListAdapter(adapter);
	 			}
			});
 			setListView();
 		}

 	}
	
}
