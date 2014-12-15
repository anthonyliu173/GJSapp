package com.example.slidingmenugjs;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This class displays an individual job detail.
 * User can press save(imgSave and txtSave) to save job to local database.
 * */

public class IndividualJobs extends Activity{
	
	private String position;
	private String id;
	private String salary;
	private String detail;
	private String company;
	private String location;
	private String status;
	private boolean save;

	TextView txtTitleInd;
	TextView txtCompanyInd;
	TextView txtSalaryInd;
	TextView txtLocationInd;
	TextView txtDetailInd; 
	TextView txtSave;
	ImageView imgSave;
	
	//parameters for MySQL
	ModifyDataBase MDB;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.individual_job);
		
		context = IndividualJobs.this;
	
		// set action bar style
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F26E1A")));
		
		//get bundle information from previous class(SearchResult.java)
		Bundle bd = this.getIntent().getExtras();
		position = bd.getString("position");
		id = bd.getString("id");
		salary = bd.getString("salary");
		detail = bd.getString("detail");
		company = bd.getString("company");
		location = bd.getString("location");
		status = bd.getString("status");
		save = false;
		
		//check if this job has been saved before
		check_if_job_saved(id);
		
		txtTitleInd = (TextView)findViewById(R.id.txtTitleInd);
		txtCompanyInd = (TextView)findViewById(R.id.txtCompanyInd);
		txtSalaryInd = (TextView)findViewById(R.id.txtSalaryInd);
		txtLocationInd = (TextView)findViewById(R.id.txtLocationInd);
		txtDetailInd = (TextView)findViewById(R.id.txtDetailInd);
		txtSave = (TextView)findViewById(R.id.txtSave);
		imgSave = (ImageView)findViewById(R.id.imgSave);
		
		txtTitleInd.setText(position);
		txtCompanyInd.setText(company);
		txtSalaryInd.setText(salary);
		txtLocationInd.setText(location);
		txtDetailInd.setText(detail);
		
		//if status is "joblist", remove "save" button
		if(status.matches("joblist")){
			txtSave.setVisibility(View.GONE);
			imgSave.setVisibility(View.GONE);
		}
		
		if(save){
			imgSave.setImageResource(R.drawable.save);
		}else{
			imgSave.setImageResource(R.drawable.unsave);
		}
	
	}
	
	/*
	 * Check if the job has been saved or not by examining the ID, and show corresponding icon.
	 * */
	public void check_if_job_saved(String ID){
		MDB = new ModifyDataBase();
		if(MDB.check_if_saved(context, ID).matches("true")){
			save = true;
		}
	}
	
	/*
	 * This is a "on click" function for saving/unsaving jobs
	 * */
	public void save_unsave(View v){
		
		MDB = new ModifyDataBase();
		
		if(!save){
			imgSave.setImageResource(R.drawable.save);
			MDB.SaveData(context, id, position, company, salary, location, detail, "save");
			Toast.makeText(IndividualJobs.this, "save to Job List", Toast.LENGTH_SHORT).show();
			save = true;
		}else{
			imgSave.setImageResource(R.drawable.unsave);
			MDB.RemoveData(context, id, position, company, salary, location, detail, "save");
			Toast.makeText(IndividualJobs.this, "remove from Job List", Toast.LENGTH_SHORT).show();
			save = false;
		}
	}
	
}
