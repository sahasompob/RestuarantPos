package com.refresh.pos.ui.sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.inventory.LineItemSale;
import com.refresh.pos.domain.sale.Sale;
import com.refresh.pos.domain.sale.SaleLedger;
import com.refresh.pos.domain.sale.SaleReport;
import com.refresh.pos.techicalservices.NoDaoSetException;

/**
 * UI for showing the detail of Sale in the record.
 * @author Refresh Team
 *
 */
public class SaleDetailActivity extends Activity{

	private TextView totalBox;
	private TextView dateBox;
	private ListView lineitemListView;
	private List<Map<String, String>> lineitemList;
	private Sale sale;
	private SaleReport saleReport;
	private int saleId;
	private SaleLedger saleLedger;

	@SuppressLint("MissingSuperCall")
    @Override
	public void onCreate(Bundle savedInstanceState) {

		try {
			saleLedger = SaleLedger.getInstance();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}

		saleId = Integer.parseInt(getIntent().getStringExtra("id"));
		saleReport = saleLedger.getSaleReportById(saleId);

	Toast.makeText(getBaseContext() ,saleId+"", Toast.LENGTH_SHORT).show();


		initUI(savedInstanceState);
	}


	/**
	 * Initiate actionbar.
	 */
	@SuppressLint("NewApi")
//	private void initiateActionBar() {
//		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//			ActionBar actionBar = getActionBar();
//			actionBar.setDisplayHomeAsUpEnabled(true);
//			actionBar.setTitle(getResources().getString(R.string.sale));
//			actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));
//		}
//	}
	

	/**
	 * Initiate this UI.
	 * @param savedInstanceState
	 */
	private void initUI(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_saledetail);
		
//		initiateActionBar();
		
		totalBox = (TextView) findViewById(R.id.totalBox);
		dateBox = (TextView) findViewById(R.id.dateBox);
		lineitemListView = (ListView) findViewById(R.id.lineitemList);
	}

	/**
	 * Show list.
	 */
	private void showList(List<LineItemSale> list) {
		lineitemList = new ArrayList<Map<String, String>>();
		for(LineItemSale line : list) {
			lineitemList.add(line.toMap());
		}

		SimpleAdapter sAdap = new SimpleAdapter(SaleDetailActivity.this, lineitemList,
				R.layout.listview_lineitem, new String[]{"name","quantity","price"}, new int[] {R.id.name,R.id.quantity,R.id.price});
		lineitemListView.setAdapter(sAdap);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * Update UI.
	 */
	public void update() {
		totalBox.setText(saleReport.getTotal() + "");
		dateBox.setText(saleReport.getEndTime() + "");
		showList(saleReport.getAllLineItem());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		update();
	}
}
