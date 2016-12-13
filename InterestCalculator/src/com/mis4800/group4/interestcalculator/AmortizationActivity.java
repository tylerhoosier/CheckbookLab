package com.mis4800.group4.interestcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AmortizationActivity extends Activity {
	
	TextView tvAmortization;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_amortization);
		Intent sourceIntent = getIntent();
		tvAmortization = (TextView) findViewById(R.id.tvamortization);
		
		double principal = sourceIntent.getDoubleExtra("principal", 0);
		double rate = sourceIntent.getDoubleExtra("rate", 0);
		int period = sourceIntent.getIntExtra("period", 0);
		
		displayAmortizationSchedule(principal, rate, period);
	}

	private void displayAmortizationSchedule(double principal, double rate, int period) {
		
		
		// Step 1. Find the monthly payment
		double monthlyPayment = MainActivity.calculateMortgagePI(principal, rate, period);
		double monthlyRate = rate / 12 / 100;
		double currentPrincipal = principal;
		
		// loop through each month to show that month's data
		for(int month = 1; month <= period * 12; month++) {
			// calculate current month interest
			double thisMonthInterest = currentPrincipal * monthlyRate;
			// calculate current principal
			double thisMonthPrincipal = monthlyPayment - thisMonthInterest;
			currentPrincipal = currentPrincipal - thisMonthPrincipal;
			
			// Display data each month and rounds the principal and interest 
			tvAmortization.append("\nMonth " + month + ": Principal: " + (double)Math.round(thisMonthPrincipal * 100) / 100 + "   Int: " + (double)Math.round(thisMonthInterest * 100) / 100);
		}
	}
}
