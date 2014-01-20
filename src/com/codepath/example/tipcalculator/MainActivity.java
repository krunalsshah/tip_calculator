package com.codepath.example.tipcalculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

			}
		});

		mBtnTen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTipAmount(computeTipAmount(10));
			}
		});

		mBtnFifteen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTipAmount(computeTipAmount(15));
			}
		});
		mBtnTwenty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTipAmount(computeTipAmount(20));
			}
		});

		mBtnCustom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

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
		if(!(tipAmt == null) && !tipAmt.isEmpty()){
			findViewById(R.id.llTotalTipAmt).setVisibility(View.VISIBLE);
			mTvTotalTipAmtLbl.setText(getText(R.string.dollar) + " " + tipAmt);
		}else{
			Toast.makeText(this, getText(R.string.user_enter_bill_amt), Toast.LENGTH_SHORT).show();
		}
	}

	private String computeTipAmount(final double tipPercent) {
		String billAmountStr = mEtTotalBillAmount.getText().toString();
		if (!(billAmountStr == null) && !(billAmountStr.isEmpty())) {
			return tipAmountFormatter(Double.parseDouble(billAmountStr) * (tipPercent / 100));
		}
		return "";
	}

	private String tipAmountFormatter(double unrounded) {
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(rounded.doubleValue());
	}
}
