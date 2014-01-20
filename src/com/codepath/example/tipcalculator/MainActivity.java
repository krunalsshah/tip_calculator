package com.codepath.example.tipcalculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText mEtTotalBillAmount;
	private Button mBtnTen;
	private Button mBtnFifteen;
	private Button mBtnTwenty;
	private Button mBtnCustom;
	private TextView mTvTotalTipAmtLbl;
	private double mCurrentTipPercent = 0;
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeView();
		addListeners();
	}

	public void initializeView() {
		mEtTotalBillAmount = (EditText) findViewById(R.id.etTotalBillAmount);
		mEtTotalBillAmount.requestFocus();
		showSoftKeyboard(mEtTotalBillAmount);

		mBtnTen = (Button) findViewById(R.id.btnTen);
		mBtnFifteen = (Button) findViewById(R.id.btnFifteen);
		mBtnTwenty = (Button) findViewById(R.id.btnTwenty);
		mBtnCustom = (Button) findViewById(R.id.btnCustom);
		mTvTotalTipAmtLbl = (TextView) findViewById(R.id.tvTotalTipAmtLbl);
		//set every button unselected
		mBtnTen.setBackgroundColor(Color.GRAY);
		mBtnFifteen.setBackgroundColor(Color.GRAY);
		mBtnTwenty.setBackgroundColor(Color.GRAY);
		mBtnCustom.setBackgroundColor(Color.GRAY);
	}

	public void addListeners() {
		mEtTotalBillAmount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (mCurrentTipPercent > 0) {
					showTipAmount(computeTipAmount(mCurrentTipPercent));
				}
			}
		});

		mBtnTen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBtnTen.setBackgroundColor(Color.DKGRAY);
				mBtnFifteen.setBackgroundColor(Color.GRAY);
				mBtnTwenty.setBackgroundColor(Color.GRAY);
				mBtnCustom.setBackgroundColor(Color.GRAY);
				showTipAmount(computeTipAmount(10));
			}
		});

		mBtnFifteen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBtnTen.setBackgroundColor(Color.GRAY);
				mBtnFifteen.setBackgroundColor(Color.DKGRAY);
				mBtnTwenty.setBackgroundColor(Color.GRAY);
				mBtnCustom.setBackgroundColor(Color.GRAY);
				showTipAmount(computeTipAmount(15));
			}
		});

		mBtnTwenty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBtnTen.setBackgroundColor(Color.GRAY);
				mBtnFifteen.setBackgroundColor(Color.GRAY);
				mBtnTwenty.setBackgroundColor(Color.DKGRAY);
				mBtnCustom.setBackgroundColor(Color.GRAY);
				showTipAmount(computeTipAmount(20));
			}
		});

		mBtnCustom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBtnTen.setBackgroundColor(Color.GRAY);
				mBtnFifteen.setBackgroundColor(Color.GRAY);
				mBtnTwenty.setBackgroundColor(Color.GRAY);
				mBtnCustom.setBackgroundColor(Color.DKGRAY);
				hideTipAmount();
				//Inflate custom tip layout
				LayoutInflater inflater = LayoutInflater.from(context);
				View view = inflater.inflate(R.layout.custom_tip_layout, null);
				//Construct Dialog to accept custom tip amount
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				//Set Dialog view & title
				alertDialogBuilder.setView(view);
				alertDialogBuilder.setTitle(R.string.cutom_tip_dialog);
				final EditText mEtTipPercent = (EditText) view
						.findViewById(R.id.etCustomTipPercent);
				alertDialogBuilder
						.setPositiveButton(getText(R.string.ok),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// change state only if total bill amount & custom tip amount are provided by the user
										if(!isNullEmpty(mEtTotalBillAmount.getText().toString()) && !isNullEmpty(mEtTipPercent
															.getText().toString())){
											mCurrentTipPercent = Double
													.valueOf(mEtTipPercent
															.getText().toString());
											showTipAmount(computeTipAmount(mCurrentTipPercent));
										}else{
											showTipAmount(computeTipAmount(mCurrentTipPercent));
										}
										
									}
								})
						.setNegativeButton(getText(R.string.cancel),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										if(!isNullEmpty(mEtTotalBillAmount.getText().toString())){
											showTipAmount(computeTipAmount(mCurrentTipPercent));
										}
									}
								});
				AlertDialog alertDialog = alertDialogBuilder.create();			 
				alertDialog.show();

			}
		});
	}

	private void showSoftKeyboard(View view) {
		if (view.requestFocus()) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		}
	}

	private void showTipAmount(final String tipAmt) {
		if (!isNullEmpty(tipAmt)) {
			findViewById(R.id.llTotalTipAmt).setVisibility(View.VISIBLE);
			mTvTotalTipAmtLbl.setText(getText(R.string.dollar) + " " + tipAmt);
		} else {
			hideTipAmount();
			Toast.makeText(this, getText(R.string.user_enter_bill_amt),
					Toast.LENGTH_SHORT).show();
		}
	}

	private void hideTipAmount() {
		findViewById(R.id.llTotalTipAmt).setVisibility(View.GONE);
	}

	private String computeTipAmount(final double tipPercent) {
		String billAmountStr = mEtTotalBillAmount.getText().toString();
		if (!isNullEmpty(billAmountStr)) {
			mCurrentTipPercent = tipPercent;
			return tipAmountFormatter(Double.parseDouble(billAmountStr)
					* (tipPercent / 100));
		}
		return "";
	}

	private String tipAmountFormatter(double unrounded) {
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(rounded.doubleValue());
	}
	
	private boolean isNullEmpty(String value){
		return (value == null || value.isEmpty());
	}

}
