package com.example.slidingmenugjs;


import java.util.ArrayList;
import java.util.HashMap;

import com.example.databasegjs.DatabaseHandler;
import com.example.databasegjs.StoreData;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This class displays all jobs that have been saved by user.
 */

public class JobListFragment extends ListFragment {

	public JobListFragment() {
	}

	private ArrayList<HashMap<String, String>> dataLists;
	private DatabaseHandler db;
	private ImageView img_No_Job_Saved;
	private TextView txt_No_Job_Saved;

	private static final String TAG_ID = "id";
	private static final String TAG_TITLE = "title";
	private static final String TAG_LOCATION = "location";
	private static final String TAG_SALARY = "salary";
	private static final String TAG_COMPANY = "company";
	private static final String TAG_DETAIL = "detail";

	private ListView lv;
	private ListAdapter adapter;
	
	//parameters for mysql
	ModifyDataBase MDB;
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_job_list, container,
				false);
		img_No_Job_Saved = (ImageView) rootView
				.findViewById(R.id.img_No_Job_Saved);
		txt_No_Job_Saved = (TextView) rootView
				.findViewById(R.id.txt_No_Job_Save);
		lv = (ListView) rootView.findViewById(android.R.id.list);

		// Create a ListView-specific touch listener. ListViews are given
		// special treatment because
		// by default they handle touches for their list items... i.e. they're
		// in charge of drawing
		// the pressed state (the list selector), handling list item clicks,
		// etc.
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
				lv, new SwipeDismissListViewTouchListener.DismissCallbacks() {
					@Override
					public boolean canDismiss(int position) {
						// Toast.makeText(getActivity(),
						// String.valueOf(position), 1000).show();
						return true;
					}

					@Override
					public void onDismiss(ListView listView,
							int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							dataLists.remove(adapter.getItem(position));
							RemoveData(position);
						}
						SetAdapter(dataLists);
					}
				});
		lv.setOnTouchListener(touchListener);
		// Setting this scroll listener is required to ensure that during
		// ListView scrolling,
		// we don't look for swipes.
		lv.setOnScrollListener(touchListener.makeScrollListener());

		return rootView;

	}
	
	//if user remove an job, corresponding database will be modified.
	public void RemoveData(int position){
		db = new DatabaseHandler(context);
		ArrayList<StoreData> Data_from_db = db.Get_Datas();
		db.close();
		MDB = new ModifyDataBase();
		MDB.RemoveData(context, Data_from_db.get(position).getJobId(), 
				Data_from_db.get(position).getTitle(), 
				Data_from_db.get(position).getCompany(), 
				Data_from_db.get(position).getSalary(),
				Data_from_db.get(position).getLocation(),
				Data_from_db.get(position).getDetail(),
				"save");
		Toast.makeText(context, "remove from Job List", Toast.LENGTH_SHORT).show();

	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

	/*
	 * Set Adapter / Update Adapter and also check adapter length If no job
	 * saved, show corresponding image and text
	 */
	private void SetAdapter(ArrayList<HashMap<String, String>> DataList) {

		adapter = new SimpleAdapter(getActivity(), dataLists,
				R.layout.list_item_data, new String[] { TAG_TITLE, TAG_SALARY,
						TAG_COMPANY, TAG_LOCATION, TAG_DETAIL }, new int[] {
						R.id.data_title, R.id.data_salary, R.id.data_company,
						R.id.data_location, R.id.data_detail });

		// updating listview
		setListAdapter(adapter);

		if (!DataList.isEmpty()) {
			img_No_Job_Saved.setVisibility(View.GONE);
			txt_No_Job_Saved.setVisibility(View.GONE);
		} else {
			img_No_Job_Saved.setVisibility(View.VISIBLE);
			txt_No_Job_Saved.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		TextView a = (TextView) v.findViewById(R.id.data_ID);
		String TAG_ID = a.getText().toString();
		TextView b = (TextView) v.findViewById(R.id.data_company);
		String TAG_COMPANY = b.getText().toString();
		TextView c = (TextView) v.findViewById(R.id.data_title);
		String TAG_TITLE = c.getText().toString();
		TextView d = (TextView) v.findViewById(R.id.data_salary);
		String TAG_SALARY = d.getText().toString();
		TextView e = (TextView) v.findViewById(R.id.data_detail);
		String TAG_DETAIL = e.getText().toString();
		TextView f = (TextView) v.findViewById(R.id.data_location);
		String TAG_LOCATION = f.getText().toString();

		// start individual job activity
		Intent IndividualJob = new Intent(getActivity(), IndividualJobs.class);

		Bundle bd = new Bundle();

		bd.putString("position", TAG_TITLE);
		bd.putString("id", TAG_ID);
		bd.putString("salary", TAG_SALARY);
		bd.putString("detail", TAG_DETAIL);
		bd.putString("company", TAG_COMPANY);
		bd.putString("location", TAG_LOCATION);
		bd.putString("status", "joblist");
		IndividualJob.putExtras(bd);

		startActivity(IndividualJob);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Hashmap for ListView
		dataLists = new ArrayList<HashMap<String, String>>();

		// Load JSON in Background Thread
		new LoadDatas().execute();

	}

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
			
		}

		/**
		 * getting Albums JSON
		 * */
		protected String doInBackground(String... args) {

			db = new DatabaseHandler(getActivity());
			ArrayList<StoreData> Data_from_db = db.Get_Datas();

			if (Data_from_db.size() != 0) {
				// looping through All albums
				for (int i = 0; i < Data_from_db.size(); i++) {

					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_ID, Data_from_db.get(i).getJobId());
					map.put(TAG_TITLE, Data_from_db.get(i).getTitle());
					map.put(TAG_LOCATION, Data_from_db.get(i).getLocation());
					map.put(TAG_SALARY, Data_from_db.get(i).getSalary());
					map.put(TAG_COMPANY, Data_from_db.get(i).getCompany());
					map.put(TAG_DETAIL, Data_from_db.get(i).getDetail());

					// adding HashList to ArrayList
					dataLists.add(map);
				}
			} else {
				Log.d("Datas: ", "null");
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			SetAdapter(dataLists);
		}

	}

}
