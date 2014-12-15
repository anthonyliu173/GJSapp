package com.example.databasegjs;

import android.content.Context;

/*
 * This class is used to communicate with DatabaseHandler.java
 * */
public class StoreData {

	// private variables
	public int _id;
	public String JobId;
	public String Title;
	public String Company;
	public String Salary;
	public String Location;
	public String Detail;
	public String Save;

	DatabaseHandler db;
	Context _context;

	public StoreData() {

	}

	public StoreData(int _id, String JobId, String Title, String Company, String Salary,
			String Location, String Detail, String Save) {
		this._id = _id;
		this.JobId = JobId;
		this.Title = Title;
		this.Company = Company;
		this.Salary = Salary;
		this.Location = Location;
		this.Detail = Detail;
		this.Save = Save;

	}
	
	// getting ID
	public int getId() {
		return this._id;
	}

	// getting Job ID
	public String getJobId() {
		return this.JobId;
	}

	// setting Title
	public String getTitle() {
		return this.Title;
	}

	// getting Company
	public String getCompany() {
		return this.Company;
	}

	// getting Salary
	public String getSalary() {
		return this.Salary;
	}
	
	//getting Location
	public String getLocation() {
		return this.Location;
	}

	// getting Detail
	public String getDetail() {
		return this.Detail;
	}
	
	// getting Save status
	public String getSave() {
		return this.Save;
	}

	// setting ID
	public void setId(int _id) {
		this._id = _id;
	}
	
	// setting JobId
	public void setJobId(String JobId) {
		this.JobId = JobId;
	}

	// setting Title
	public void setTitle(String Title) {
		this.Title = Title;
	}

	// setting Company
	public void setCompany(String Company) {
		this.Company = Company;
	}

	// setting Salary
	public void setSalary(String Salary) {
		this.Salary = Salary;
	}
	
	// setting Location
	public void setLocation(String Location) {
		this.Location = Location;
	}

	// setting Detail
	public void setDetail(String Detail) {
		this.Detail = Detail;
	}
	
	// setting Save status
	public void setSave(String Save) {
		this.Save = Save;
	}

}
