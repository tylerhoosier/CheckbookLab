package com.mis4800.group4.interestcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	EditText txtPrincipal, txtRate, txtPeriod;
	Button btnCalculate, btnAmortization;
	TextView tvResult;
	Spinner calcOptions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Grab the text fields and button in variables
		txtPrincipal = (EditText) findViewById(R.id.txtPrincipal);
		txtRate = (EditText) findViewById(R.id.txtRate);
		txtPeriod = (EditText) findViewById(R.id.txtPeriod);
		
		tvResult = (TextView) findViewById(R.id.tvResult);
		
		btnCalculate = (Button) findViewById(R.id.btnCalculate);
		
		calcOptions = (Spinner) findViewById(R.id.calcOptions);
	
		
		// Set up something to happen when the button is clicked
		btnCalculate.setOnClickListener(this);
		
		btnAmortization = (Button) findViewById(R.id.btn_amortization);
		btnAmortization.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		// Declares Variables to be used later
		double principal = Double.parseDouble(txtPrincipal.getText().toString());
		double rate = Double.parseDouble(txtRate.getText().toString());
		int period = Integer.parseInt(txtPeriod.getText().toString());
		
	
		
		if (v.getId() == R.id.btnCalculate) {
		
		double result = 0;
		// Select the appropriate calculation based on what was in spinner
		
		switch(calcOptions.getSelectedItemPosition()) {
		case 0:
				result = calculateSimpleInterst(principal, rate, period);
				break;
		case 1:
			result = calculateCompoundInterest(principal, rate, period);
			break;
		case 2:
			result = calculateMortgagePI(principal, rate, period);
			break;
		}
		
		// Displays Results
		tvResult.setText(String.format(getResources().getString(R.string.result), result));
		} else if (v.getId() == R.id.btn_amortization) {
		// Display Amortization Schedule 	
			Intent amortIntent = new Intent();
			amortIntent.setClass(this, AmortizationActivity.class);
			amortIntent.putExtra("principal", principal);
			amortIntent.putExtra("rate", rate);
			amortIntent.putExtra("period", period);
			startActivity(amortIntent);
		
		}
	}
	public static double calculateMortgagePI(double principal, double rate, double period) {
		// Calculates Monthly Mortgage Payment
		return (float)((principal * ((rate / 100) / 12)) / (1 - Math.pow(1 + ((rate / 100) / 12), (period * 12) * -1))); 
	}	
	public static double calculateCompoundInterest(double principal, double rate, int period) {
		// Calculates Compound Interest
		return (float)(principal * Math.pow((1 + rate/100 ), period));
	}	
	private double calculateSimpleInterst(double principal, double rate, int period) {
		// Calculates Simple Interest
		return (float)(principal * (1 + rate / 100 * period));
	}
}
