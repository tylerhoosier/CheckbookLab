package com.model.tickit.mis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskList {

	private String task;
	

	private List<Task> tasks;

	/**
	 * @param bank
	 * @param owner
	 */
	public TaskList(String task) {
		super();
		this.task = task;
		this.tasks = new ArrayList<Task>();
	}
	
	public void addTask(Task newt) {
		tasks.add(newt);
	}
	
	public void deleteTask(int position) {
		tasks.remove(position);
	}
	
	/**
	 * @return the transactions
	 */
	public List<Task> getTask() {
		return tasks;
	}
	
	
	/**
	 * @return the bank
	 */
	public String getTask1() {
		return task;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setTask(String task) {
		this.task = task;
	}
	
	public void sort(int sortType) {
		switch(sortType) {
		case 0: Collections.sort(tasks, new Comparator<Task>() {

			@Override
			public int compare(Task lhs, Task rhs) {
				// TODO Auto-generated method stub
				return lhs.compareByDate(rhs);
			}
		});
		break;
		
		case 1:
			Collections.sort(tasks, new Comparator<Task>() {

				@Override
				public int compare(Task lhs, Task rhs) {
					// TODO Auto-generated method stub
					return lhs.compareByName(rhs);
				}
				
			});
		break;
}
}
}