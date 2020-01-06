package com.refresh.pos.ui.sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.DateTimeStrategy;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.sale.Register;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.MainActivity;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.InventoryFragment;
import com.refresh.pos.ui.inventory.RecyclerViewAdapterTable;
import com.refresh.pos.ui.main.MainFragment;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * UI for Sale operation.
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")
public class SaleFragment extends UpdatableFragment {

	private Register register;
	private ArrayList<Map<String, String>> saleList;
	private ListView saleListView;
	private Button clearButton;
	private TextView tvTable;
    public TextView textNoTable;
	private TextView tvName;
	private TextView totalPrice;
	private Button printButton;
	private Button quicklyButton;
	private Button endButton;
	private Button saveButtton;
	private MainFragment reportFragment;
	private Resources res;
	private Button table_button;
    public static final int OPEN_TABLE_DIALOG = 1; // adding this line
	private int tableNo;



    private ProductCatalog productCatalog;
	/**
	 * Construct a new SaleFragment.
	 * @param
	 */
	public SaleFragment(MainFragment reportFragment) {
		super();
		this.reportFragment = reportFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		try {
			register = Register.getInstance();
			productCatalog = Inventory.getInstance().getProductCatalog();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}

		View view = inflater.inflate(R.layout.layout_sale, container, false);
		
		res = getResources();
		saleListView = (ListView) view.findViewById(R.id.sale_List);
		totalPrice = (TextView) view.findViewById(R.id.totalPrice);
		clearButton = (Button) view.findViewById(R.id.clearButton);
		endButton = (Button) view.findViewById(R.id.endButton);
		saveButtton = (Button) view.findViewById(R.id.saveButton);
		printButton = (Button) view.findViewById(R.id.printButton);
		quicklyButton = (Button) view.findViewById(R.id.btn_bil_quickly);
		table_button = (Button) view.findViewById(R.id.table_button);
		tvTable = (TextView) view.findViewById(R.id.tvTable);
		tvName = (TextView) view.findViewById(R.id.tvName);
        textNoTable = (TextView) view.findViewById(R.id.textNoTable);




		initUI();
		return view;
	}

	/**
	 * Initiate this UI.
	 */

	private void checkArgument(){

		String strtext = getArguments().getString("message");

		if (strtext!=null){

			tableNo = Integer.parseInt(strtext);


			textNoTable.setText(tableNo+"");

		}else {



			tableNo = 1;
			textNoTable.setText(tableNo+"");
		}


	}

	private void initUI() {

		checkArgument();


//		saleListView.setOnItemClickListener(new OnItemClickListener(){
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				showEditPopup(arg1,arg2);
//			}
//		});

		endButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String textNo = textNoTable.getText().toString();

				if(register.hasSale()){

					if (textNo.equals("")){

						Toast.makeText(getActivity().getBaseContext() , "TableNumber is empty", Toast.LENGTH_SHORT).show();

					}else {

						showPopup(v);

					}

				} else {

					Toast.makeText(getActivity().getBaseContext() , res.getString(R.string.hint_empty_sale), Toast.LENGTH_SHORT).show();
				}
			}
		});


		saveButtton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String textNo = textNoTable.getText().toString();

				if(register.hasSale()){

					if (textNo.equals("")){

						Toast.makeText(getActivity().getBaseContext() , "TableNumber is empty", Toast.LENGTH_SHORT).show();

					}else {


						int tableNumber = Integer.parseInt(textNo);
//						register.endSale(DateTimeStrategy.getCurrentTime(),tableNumber);
						textNoTable.setText("");
						update();



					}

				} else {

					Toast.makeText(getActivity().getBaseContext() , res.getString(R.string.hint_empty_sale), Toast.LENGTH_SHORT).show();
				}
			}
		});



		
		clearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!register.hasSale() || register.getCurrentSale().getAllLineItem().isEmpty()) {
					Toast.makeText(getActivity().getBaseContext() , res.getString(R.string.hint_empty_sale), Toast.LENGTH_SHORT).show();
				} else {

					showConfirmClearDialog();
				}
			} 
		});

		printButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				String textNo = textNoTable.getText().toString();

				if(register.hasSale()){

					if (textNo.equals("")){

						Toast.makeText(getActivity().getBaseContext() , "TableNumber is empty", Toast.LENGTH_SHORT).show();

					}else {


						int tableNumber = Integer.parseInt(textNo);
						register.holdOrderSale(DateTimeStrategy.getCurrentTime(),tableNumber,"busy");
						textNoTable.setText("");
						update();



					}

				} else {

					Toast.makeText(getActivity().getBaseContext() , res.getString(R.string.hint_empty_sale), Toast.LENGTH_SHORT).show();
				}


            }
		});


		quicklyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

//				saveButtton.setVisibility(View.GONE);
//				endButton.setVisibility(View.VISIBLE);
				tvTable.setBackgroundColor(Color.RED);
				tvTable.setText(R.string.table_takeaway);
				tvName.setBackgroundColor(Color.RED);
                textNoTable.setText("TA001");

			}
		});

		table_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

//				saveButtton.setVisibility(View.VISIBLE);
//				endButton.setVisibility(View.GONE);
				tvTable.setBackgroundColor(Color.parseColor("#33B5E5"));
				tvTable.setText(R.string.table_number);
				tvName.setBackgroundColor(Color.parseColor("#33B5E5"));

				openTablePopup(v);


			}
		});
	}




	/**
	 * Show list
	 * @param list
	 */
	private void showList(List<LineItem> list) {

		saleList = new ArrayList<Map<String, String>>();
		for(LineItem line : list) {
			saleList.add(line.toMap());

			saleListView.invalidate();
		}

		SimpleAdapter sAdap;
		sAdap = new SimpleAdapter(getActivity().getBaseContext(), saleList,
				R.layout.listview_lineitem, new String[]{"name","quantity","price"}, new int[] {R.id.name,R.id.quantity,R.id.price});
		saleListView.setAdapter(sAdap);





	}




	/**
	 * Try parsing String to double.
	 * @param value
	 * @return true if can parse to double.
	 */
	public boolean tryParseDouble(String value)  
	{  
		try  {  
			Double.parseDouble(value);  
			return true;  
		} catch(NumberFormatException e) {  
			return false;  
		}  
	}
	
	/**
	 * Show edit popup.
	 * @param anchorView
	 * @param position
	 */
//	public void showEditPopup(View anchorView,int position){
//
//		Bundle bundle = new Bundle();
//		bundle.putString("position",position+"");
//		bundle.putString("sale_id",register.getCurrentSale().getId()+"");
//		bundle.putString("product_id",register.getCurrentSale().getLineItemAt(position).getProduct().getId()+"");
////		bundle.putString("name_product",register.getCurrentSale().getLineItemAt(position).getProduct().getName()+"");
//
//		EditFragmentDialog newFragment = new EditFragmentDialog(SaleFragment.this, reportFragment);
//		newFragment.setArguments(bundle);
//		newFragment.show(getFragmentManager(), "");
//
//	}

	/**
	 * Show popup
	 * @param anchorView
	 */
	public void showPopup(View anchorView) {

		int tbNumber = 0;

		String tbNumber_txt = String.valueOf(tbNumber);

//		Bundle bundle = new Bundle();
//		bundle.putString("edttext", totalPrice.getText().toString());
//		bundle.putString("table_id",tbNumber_txt);
//		bundle.putString("sale_id",register.getCurrentSale().getId()+"");
//		PaymentFragmentDialog newFragment = new PaymentFragmentDialog(SaleFragment.this, reportFragment);
//		newFragment.setArguments(bundle);
//		newFragment.show(getFragmentManager(), "");

	}


	public void openTablePopup(View anchorView) {


		OpenTableFragmentDialog newFragment = new OpenTableFragmentDialog(SaleFragment.this, reportFragment);
        newFragment.setTargetFragment(this, OPEN_TABLE_DIALOG);
        newFragment.show(getFragmentManager(), "OpenTable");


	}

	// This is the interface declared inside your adapter.


	@Override
	public void update() {

		if(register.hasSale()){
			showList(register.getCurrentSale().getAllLineItem());
			totalPrice.setText(register.getTotal() + "");
		}

		else{

			showList(new ArrayList<LineItem>());
			totalPrice.setText("0.00");
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		update();
	}
	
	/**
	 * Show confirm or clear dialog.
	 */
	private void showConfirmClearDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle(res.getString(R.string.dialog_clear_sale));
		dialog.setPositiveButton(res.getString(R.string.no), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		dialog.setNegativeButton(res.getString(R.string.yes), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

                String textNo = textNoTable.getText().toString();
                int tableNumber = Integer.parseInt(textNo);

                register.holdOrderSale(DateTimeStrategy.getCurrentTime(),tableNo,"empty");

                register.cancleSale();
				textNoTable.setText("");
				update();
			}
		});

		dialog.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
			case OPEN_TABLE_DIALOG:

				if (resultCode == Activity.RESULT_OK) {
					// here the part where I get my selected date from the saved variable in the intent and the displaying it.
					Bundle bundle = data.getExtras();
					String resultData = bundle.getString("data", "error");


				}
				break;
		}
	}



}
