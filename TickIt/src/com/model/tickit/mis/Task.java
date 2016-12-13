package com.model.tickit.mis;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

	private String task;
	private String taskDate;
	

	
	public Task(String task, String date) {
		super();
		this.task = task;
		this.taskDate = date;
	}

	public String getTask() {
		
		return task;
	}

	/**
	 * @param payee the payee to set
	 */
	public void setTask(String task) {
		this.task = task;
	}
	
	public String getDate() {
		return taskDate;
	}
	
	public void setDate(String date) {
		this.taskDate = date;
	}
	
//	public String getFormattedDate() {
//		return dateformat.format(getDate());
//	}
	
	public String toString() {
		return task + "\nDate: " + getDate();
	}
	
	public int compareByDate(Task another) {
		return taskDate.compareTo(another.getDate());
	}
	
	public int compareByName(Task another) {
		return task.compareTo(another.getTask());
	}

}

