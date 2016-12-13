package com.mis4800.group4;

import android.app.Activity;
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
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {

	Button btnAdd, btnDelete;
	TextView tvBalance;
	ListView lstTransactions;
	EditText txtAmount;
	
	ArrayAdapter<Double> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvBalance = (TextView) findViewById(R.id.tvBalance);
		txtAmount = (EditText) findViewById(R.id.txtAmount);
		
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnAdd.setOnClickListener((OnClickListener) this);
		btnDelete.setOnClickListener((OnClickListener) this);
		// make delete button invisible at the start
		btnDelete.setVisibility(View.INVISIBLE);
		
		for(int i=0; i < MAX; i++) {
			transactions[i] = 0.0;
		}
		lstTransactions = (ListView) findViewById(R.id.lstTransactions);
		
		adapter = new ArrayAdapter<Double>(this, 
				android.R.layout.simple_list_item_1, 
				transactions);
		lstTransactions.setAdapter(adapter);
		lstTransactions.setOnItemClickListener(this);
	}

	public static int MAX = 25;
	
	private int selectedPosition = -1;
	
	
	// Create an array of transactions
	Double[] transactions = new Double[MAX];
	// Keep track of how many transactions we have - we start at 0
	int numTransactions = 0;
	
	/**
	 * Delete a transaction at position "position"
	 * @param positionToDelete the position of the array (0 based) where I am deleting
	 */
	private void deleteTransaction(int selectedPosition) {
		
		// We start at the selectedPosition, we don't have to go to the very last one because it
		// would move over when we delete the last but 1 item - so we go to numTransactions -1
		for(int emptyPosition = selectedPosition; emptyPosition < MAX -1; emptyPosition++) {
			// replace the item at the current emptyPosition with the one next to it
			transactions[emptyPosition] = transactions[emptyPosition + 1];
		}
		// Now we have one less transaction, so lets decrement number of transactions
		numTransactions = numTransactions -1;
		
		
		
	}

	/**
	 * Update the transaction at a given position (0 based) with a new amount
	 * @param positionToUpdate
	 * @param newAmount
	 */
	private void updateTransaction(int selectedPosition, double newAmount) {
		// Change transaction amount in position to new amount
		transactions[selectedPosition] = newAmount;
	}

	/**
	 * calculate the current balance
	 * @return the current balance
	 */
	private double calculateBalance() {
		double balance = 0.0; // Variable to store balance in
		// Go through all the transactions in a loop
		for(int i=0; i < numTransactions; i++) {
			// Add the transaction I am at to the balance
			balance = balance + transactions[i];
		}
		System.out.println("Current Balance is " + balance);
		// return the calculated balance
		return balance;
	}

	/**
	 * Add a new transaction with an amount to the checkbook
	 * @param amount
	 */
	private void addTransaction(double amount) {
		// Error checking - we can only add a new transactions if we have not maxed out.
		if (numTransactions < MAX) {
			// PUt the amount in the right position
			transactions[numTransactions] = amount;
			
			// Increment the number of transactions
			numTransactions = numTransactions +1;
			
			
			
		} else {
			System.out.println("Checkbook is full!"); // checkbook is full - print an error message
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View v) {
		// use switch statement to control actions based on the specific button pushed
		switch(v.getId()) {
		case R.id.btnAdd:
			// Add the amount to the array
			double amount = Double.parseDouble(txtAmount.getText().toString());
			if (btnAdd.getText().equals("Add")) {
				// add the transaction
				addTransaction(amount);
				// reset text field to 0
				txtAmount.setText(null);
			} else {
				// make delete button visible
				btnDelete.setVisibility(View.VISIBLE);
				// otherwise update transaction
				updateTransaction(selectedPosition, amount);
				// reset positionToUpdate
				selectedPosition = -1;
				// switch back the text of the button
				btnAdd.setText("Add");
				// make delete button invisible
				btnDelete.setVisibility(View.INVISIBLE);
				// reset text field
				txtAmount.setText(null);
				// notify adapter
				adapter.notifyDataSetChanged();
			}
				// update the balance
			tvBalance.setText("The current balance is: $" + calculateBalance());
			break;
		case R.id.btnDelete:
			// delete the amount at selected position
			deleteTransaction(selectedPosition);
			// reset selectedPosition
			selectedPosition = -1;
			// switch back the text of the button
			btnAdd.setText("Add");
			// switch back text field to 0
			txtAmount.setText(null);
			// update the balance
			tvBalance.setText("The current balance is: $" + calculateBalance());
			// make delete button invisible
			btnDelete.setVisibility(View.INVISIBLE);
			break;
		}
			// notify the adapter to let it know data changed
			adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> listview, View itemview, int itemposition, long itemId) {
		// TODO Auto-generated method stub
		txtAmount.setText(transactions[itemposition].toString());
		// Change the button so the user knows the balance can be updated
		btnAdd.setText("Update");
		// make delete button appear
		btnDelete.setVisibility(View.VISIBLE);
		// Save item position to make update work
		selectedPosition = itemposition;
	}
}
