package com.refresh.pos.ui.sale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.refresh.pos.R;
import com.refresh.pos.domain.DateTimeStrategy;
import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.inventory.LineItemSale;
import com.refresh.pos.domain.inventory.ToppingProduct;
import com.refresh.pos.domain.sale.Register;
import com.refresh.pos.domain.sale.Sale;
import com.refresh.pos.domain.sale.SaleLastRow;
import com.refresh.pos.domain.sale.SaleLedger;
import com.refresh.pos.domain.sale.SaleReport;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDaoAndroid;
import com.refresh.pos.techicalservices.sale.SaleDao;
import com.refresh.pos.techicalservices.sale.SaleDaoAndroid;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.MySingleton;
import com.refresh.pos.ui.main.MainFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.android.volley.VolleyLog.TAG;

/**
 * A dialog shows the total change and confirmation for Sale.
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")
public class EndPaymentFragmentDialog extends DialogFragment  {

	private Button doneButton;
	private TextView chg;
	private Register regis;
	private UpdatableFragment saleFragment;
	private MainFragment mainFragment;
    private Sale sale;
    private String saleId;
    private String tableNumber;
    private String price;

    private int sale_id;
    private int numberTable;
    private SaleLedger saleLedger;
	private ListView lineitemListView;
	private ArrayList<Map<String, String>> lineitemList;
	private ArrayList<Map<String, String>> lineitemListSale;
	private ArrayList<Map<String, String>> testList;

	private SaleDao saleDao;
	private double totalPrice;
	private double moneyInput;
	private double moneyChange;
	private ArrayList<Map<String, String>> salelist;
	private String testId;
	private SaleReport saleReport;
	private List<LineItemSale> lineItem;




	/**
	 * End this UI.
	 * @param saleFragment
	 */
	public EndPaymentFragmentDialog(UpdatableFragment saleFragment, MainFragment mainFragment) {
		super();
		this.saleFragment = saleFragment;
		this.mainFragment = mainFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			regis = Register.getInstance();
            saleLedger = SaleLedger.getInstance();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		
		View v = inflater.inflate(R.layout.dialog_paymentsuccession, container,false);


		saleId = getArguments().getString("sale_id");
		sale_id = Integer.parseInt(saleId);

		price = getArguments().getString("total");
		totalPrice = Double.parseDouble(price);

		String money_input = getArguments().getString("money_input");
		moneyInput = Double.parseDouble(money_input);

		String money_change = getArguments().getString("money_change");
		moneyChange = Double.parseDouble(money_change);


		tableNumber = getArguments().getString("table_id");
		numberTable = Integer.parseInt(tableNumber);

		sale = saleLedger.getByTableId(numberTable);
		getLineitem((ArrayList<LineItem>) sale.getAllLineItem());

		savetoSaleAndSaleLine();








        chg = (TextView) v.findViewById(R.id.changeTxt);
		chg.setText(money_change);
		doneButton = (Button) v.findViewById(R.id.doneButton);

		doneButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				end();

			}
		});


//

		return v;
	}

    private void getLineitem(ArrayList<LineItem> list) {

        lineitemList = new ArrayList<Map<String, String>>();
        for (LineItem lineitem : list) {
            lineitemList.add(lineitem.toMap());

        }

//        Toast.makeText(getActivity().getBaseContext(),lineitemList.toString(),
//                Toast.LENGTH_SHORT).show();

    }

	private void getSale(ArrayList<SaleLastRow> list) {

		salelist = new ArrayList<Map<String, String>>();
		for (SaleLastRow saleLastRow : list) {
			salelist.add(saleLastRow.toMap());

		}


//		Toast.makeText(getActivity().getBaseContext()," salelist = " + salelist,
//				Toast.LENGTH_SHORT).show();


	}

	private void savetoSaleAndSaleLine(){

		ArrayList myArrlis = new ArrayList();

		for (int ii = 0; ii < sale.getAllLineItem().size(); ii++) {

			myArrlis.add(sale.getAllLineItem().get(ii).getProduct().getName());

//			String gpk = sale.getAllLineItem().get(ii).getGpk();

		}





	}


	private void showList(List<LineItemSale> list) {

		lineitemListSale = new ArrayList<Map<String, String>>();
		for (LineItemSale lineitem : list) {
			lineitemListSale.add(lineitem.toLineMap());

		}


//
//				Toast.makeText(getActivity().getBaseContext(),lineitemListSale.toString(),
//				Toast.LENGTH_SHORT).show();

	}

    private void saveSale(){

        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();

        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));

        }

        String gpk = salt.toString();
        String code = "INV-0-0";
        String date = DateTimeStrategy.getCurrentTime();
        int tableId = numberTable;
        int user_id = 1;
        int member_id = 1;
        int payment_id = 1;
        String vat_type = "none";
        int vat_percent = 5;
        double total = totalPrice;
        double discount = 0;
        double money_input = moneyInput;
        double money_change = moneyChange;
        String created = DateTimeStrategy.getCurrentTime();
        int sync = 0;
        String status = "ENDED";

        regis.endSale(gpk,code,date,tableId,user_id,member_id,payment_id,vat_type,vat_percent,total,discount,money_input,money_change,created,sync,status);
    }

	/**
	 * End
	 */
	private void end(){

		final String token = "9bdtrkt9w7apuatehl6o";

		saveSale();

        getSale((ArrayList<SaleLastRow>) saleLedger.getLastRow());


        for (int ii = 0; ii < salelist.size(); ii++) {

            testId = salelist.get(ii).get("id");

        }


//		Toast.makeText(getActivity().getBaseContext(),salelist.toString(),
//				Toast.LENGTH_SHORT).show();

        for (int ii = 0; ii < sale.getAllLineItem().size(); ii++) {

           int sale_id = Integer.parseInt(testId);
           String gpk = sale.getAllLineItem().get(ii).getGpk();
           int product_id = sale.getAllLineItem().get(ii).getProduct().getId();
           int qty = sale.getAllLineItem().get(ii).getQuantity();
           double price = sale.getAllLineItem().get(ii).getPriceAtSale();
           double cost = sale.getAllLineItem().get(ii).getProduct().getCost();
           double total = sale.getAllLineItem().get(ii).getTotalPriceAtSale();
           String topping = sale.getAllLineItem().get(ii).getTopping();
           String topping_name = sale.getAllLineItem().get(ii).getTopping_name();
           double topping_price = sale.getAllLineItem().get(ii).getTopping_price();
           int sync = InventoryDaoAndroid.SYNC_STATUS_FAILED;
           String status = "use";



			regis.saveSaleDetail(sale_id,gpk,product_id,qty,price,cost,total,topping,topping_name,topping_price,sync,status);

        }


		regis.clearTemp(numberTable);






		int saleIdtest = Integer.parseInt(testId);
		saleReport = saleLedger.getSaleReportById(saleIdtest);
		showList(saleReport.getAllLineItem());



		if (checkNetworkConnection()){

			final JSONObject saleData = new JSONObject();

			for(int ii = 0; ii < salelist.size(); ii++){

				int id = Integer.parseInt(salelist.get(ii).get("id"));
				String gpk = salelist.get(ii).get("gpk");
				String code = salelist.get(ii).get("code");
				String dated = salelist.get(ii).get("dated");
				int table_id = Integer.parseInt(salelist.get(ii).get("table_id"));
				int user_id = Integer.parseInt(salelist.get(ii).get("user_id"));
				int member_id = Integer.parseInt(salelist.get(ii).get("member_id"));
				int payment_id = Integer.parseInt(salelist.get(ii).get("payment_id"));
				String vat_type = salelist.get(ii).get("vat_type");
				int vat_percent = Integer.parseInt(salelist.get(ii).get("vat_percent"));
				int vat_value = Integer.parseInt(salelist.get(ii).get("vat_value"));
				double total = Double.parseDouble(salelist.get(ii).get("total"));
				double discount = Double.parseDouble(salelist.get(ii).get("discount"));
				double money_input = Double.parseDouble(salelist.get(ii).get("money_input"));
				double money_change = Double.parseDouble(salelist.get(ii).get("money_change"));
				String created = salelist.get(ii).get("created");
				int sync = Integer.parseInt(salelist.get(ii).get("sync"));
				String status = salelist.get(ii).get("status");



				try {

					saleData.put("id",id);
					saleData.put("gpk",gpk);
					saleData.put("code",code);
					saleData.put("dated",dated);
					saleData.put("table_id",table_id);
					saleData.put("user_id",user_id);
					saleData.put("member_id",member_id);
					saleData.put("payment_id",payment_id);
					saleData.put("vat_type",vat_type);
					saleData.put("vat_percent",vat_percent);
					saleData.put("vat_value",vat_value);
					saleData.put("total",total);
					saleData.put("discount",discount);
					saleData.put("money_input",money_input);
					saleData.put("money_change",money_change);
					saleData.put("created",created);
					saleData.put("sync",sync);
					saleData.put("status","use");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}



			JSONArray jArray = new JSONArray();
            for(int ii = 0; ii < lineitemListSale.size(); ii++){

                int testIdeiei = Integer.parseInt(lineitemListSale.get(ii).get("id"));
                int product_id = Integer.parseInt(lineitemListSale.get(ii).get("product_id"));

                try {

                    JSONObject cust = new JSONObject();

                    cust.put("id", testIdeiei);
                    cust.put("product_id",product_id);

                    jArray.put(cust);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }


            final JSONObject saleObject = new JSONObject();

			try {

				saleObject.put("token",token);
				saleObject.put("sale",saleData);
				saleObject.put("saledetails",jArray);


			} catch (JSONException e) {
				e.printStackTrace();

			}


//




			String testUrl = "http://139.180.142.52:3000/api/sales";

//			RequestQueue queue = Volley.getRequestQueue();


			JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
					testUrl, saleObject,

					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {

							Log.d(TAG, response.toString());
						}

					}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {

					Log.d(TAG, "Error: " + error.getMessage());
				}

			})

			{
				/**
				 * Passing some request headers
				 */
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					HashMap<String, String> headers = new HashMap<String, String>();
					//headers.put("Content-Type", "application/json");
					headers.put("Content-Type", "application/json");
					return headers;
				}
			};

			MySingleton.getInstance(getContext()).addtoRequestQue(jsonObjReq);



		}else {

			Toast.makeText(getActivity().getBaseContext(),
					" Fail Connect",
					Toast.LENGTH_SHORT).show();


		}




//		reportFragment.update();
		mainFragment.Endprocess();
		this.dismiss();
	}

	public boolean checkNetworkConnection(){

		ConnectivityManager connectivityManager =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo!=null && networkInfo.isConnected());
	}
}
