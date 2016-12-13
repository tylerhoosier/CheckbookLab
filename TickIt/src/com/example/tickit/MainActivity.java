package com.example.tickit;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.database.tickit.TaskListDatabase;
// import com.database.tickit.TaskListDatabase;
import com.model.tickit.mis.Task;
import com.model.tickit.mis.TaskList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	
	String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
	
	ListView lstTasks;
	Button btnCreate, btnViewTasks, btnDelete, btnCreateNewTask, btnDeleteSelectedTasks, btnCalendar;
	
	EditText textViewEnterTask;
	EditText editDate;
	
	
	TaskList myTaskList;
	
	String task;

	// Create adapter for Transactions
		ArrayAdapter<Task> adapter;
		// Create transactions array
		TaskList mytasklist;
		
		// create the database
		TaskListDatabase mydatabase;
		
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.new_tasks);
			// connect controller to the model by initializing a checkbook
			mytasklist = new TaskList("Task");
			
			// create the database
			mydatabase = new TaskListDatabase(this);
			
			// hook up with the rest of the controls
			btnCreateNewTask = (Button) findViewById (R.id.btnCreateNewTask);
			btnDelete =  (Button) findViewById (R.id.btnDelete);
			btnCalendar = (Button) findViewById (R.id.btnCalendar);
			
			textViewEnterTask = (EditText) findViewById (R.id.textViewEnterTask);
			editDate = (EditText) findViewById (R.id.editDate);
	
			btnCreateNewTask.setOnClickListener(this);
			btnDelete.setOnClickListener((OnClickListener) this);
			btnCalendar.setOnClickListener(this);
			
			lstTasks = (ListView) findViewById(R.id.lstTasks);
			
			// Update adapter to an adapter of tasks
			adapter = new ArrayAdapter<Task>(this, 
					android.R.layout.simple_list_item_1, 
					mytasklist.getTask());
			//Connect the adapter to checkbook transactions
			lstTasks.setAdapter(adapter);
			lstTasks.setOnItemClickListener(this);
		}
		
		protected void onPause() {
			super.onPause();
			mydatabase.save(mytasklist);
		}
		
		protected void onResume() {
			super.onResume();
			mydatabase.retrieve(mytasklist);
		}
		
		private int selectedPosition = -1;
		
		// Create an ArrayList of transactions
		List <String> tasks = new ArrayList<String>();
		private Task current;
		
		
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				return true;
			} else if (id == R.id.sortDate) {
				mytasklist.sort(0);
				adapter.notifyDataSetChanged();
				return true;
			} else if (id == R.id.sortTask) {
				mytasklist.sort(1);
				adapter.notifyDataSetChanged();
				return true;
			}
			
			return super.onContextItemSelected(item);
		}
		
		public void onClick(View v) {
			// use switch statement to control actions based on the specific button pushed
			switch(v.getId()) {
			
			case R.id.btnCreateNewTask:	
				
				String task = textViewEnterTask.getText().toString();
				String date = editDate.getText().toString();
				

					Task newtask = new Task(task, date);
				
					// add the task
					mytasklist.addTask(newtask);
					// reset text fields to 0
					textViewEnterTask.setText(null);
					editDate.setText(null);
					
					// notify the adapter
					adapter.notifyDataSetChanged();
					
			case R.id.btnDelete:

				// get task
				task = textViewEnterTask.getText().toString();
	
				
				
				// allow user to hit delete button multiple times without crashing the app
				if(selectedPosition == -1) return;
				// delete the amount at selected position
				mytasklist.deleteTask(selectedPosition);
				// reset selectedPosition
				selectedPosition = -1;
				
			
				// switch back text field to 0
				textViewEnterTask.setText(null);
				editDate.setText(null);
				
				//notify the adapter
				adapter.notifyDataSetChanged();
				
				break;
				
			case R.id.btnCalendar:
				
				Intent CalIntent = new Intent();
				CalIntent.setClass(this, CalendarActivity.class);
				CalIntent.putExtra("mydate", new Date(current.getDate()).getTime());
				startActivity(CalIntent);
				break;
				
			}
				// notify the adapter to let it know data changed
				adapter.notifyDataSetChanged();
		}
		//@Override
		public void onItemClick(AdapterView<?> listview, View itemview, int itemposition, long itemId) {
			current = mytasklist.getTask().get(itemposition);
			
			textViewEnterTask.setText(current.getTask().toString());
			editDate.setText(current.getDate().toString());
		
			// Save item position to make update work
			selectedPosition = itemposition;
			
			adapter.notifyDataSetChanged();
			
		
		}
}
