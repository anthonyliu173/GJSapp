package com.example.slidingmenugjs;

import java.util.ArrayList;

import android.content.Context;

import com.example.databasegjs.DatabaseHandler;
import com.example.databasegjs.StoreData;

public class ModifyDataBase {
	
	private int USER_ID = 0;
	private String JobId;
	private String Title;
	private String Company;
	private String Salary;
	private String Location;
	private String Detail;
	private String Save;
	DatabaseHandler db;
	
	//check if the given ID is in database
	public String check_if_saved(Context context, String ID){
		String saved = "false";
		db = new DatabaseHandler(context);	
		ArrayList<StoreData> Data_from_db = db.Get_Datas();
		for(int i =0;i<Data_from_db.size();i++){
			if(ID.matches(Data_from_db.get(i).getJobId().toString())){
				saved = "true";
			}
		}
		db.close();
		return saved;
	}
	
	//add data to MYSQL database
	public void SaveData(Context context, String jobid, String title, String company, String salary, 
			String location, String detail, String save){
		
		JobId = jobid;
		Title = title;
		Company = company;
		Salary = salary;
		Location = location;
		Detail = detail;
		Save = save;
		
		db = new DatabaseHandler(context);		
		ArrayList<StoreData> Data_from_db = db.Get_Datas();
		
		//check user_id first before assigning
		if (Data_from_db.size() == 0) {
			USER_ID = 0;
		} else {
			USER_ID = Data_from_db.get(Data_from_db.size() - 1)
					.getId() + 1;
		}
		db.Add_Data(new StoreData(USER_ID, JobId, Title,
				Company, Salary, Location, Detail, Save));
		//context.deleteDatabase("GJSdata");
		db.close();
	}
	
	//remove data from MYSQL database
	public void RemoveData(Context context, String jobid, String title, String company, String salary, 
			String location, String detail, String save){
		db = new DatabaseHandler(context);		
		ArrayList<StoreData> Data_from_db = db.Get_Datas();
		for(int i =0;i<Data_from_db.size();i++){
			if(jobid.matches(Data_from_db.get(i).getJobId().toString())){
				db.Delete_Job(Data_from_db.get(i).getId());
			}
		}
		db.close();
	}
}
	
